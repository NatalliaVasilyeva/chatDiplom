package com.bsu.nvasilyeva.controller;

import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.entity.Roles;
import com.bsu.nvasilyeva.entity.User;
import com.bsu.nvasilyeva.entity.VerificationToken;
import com.bsu.nvasilyeva.event.EmailConfirmationEvent;
import com.bsu.nvasilyeva.listener.LoggedUserListener;
import com.bsu.nvasilyeva.service.ChatService;
import com.bsu.nvasilyeva.service.RolesService;
import com.bsu.nvasilyeva.service.UserService;
import com.bsu.nvasilyeva.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    VerificationTokenService tokenService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    RolesService rolesService;

    @Autowired
    ChatService chatService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @RequestMapping(value = {"/home", "/"})
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/registration")
    public ModelAndView showRegistration() {
        return new ModelAndView("registration", "user", new User());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView handleRegistration(@ModelAttribute("user") @Valid User user, BindingResult result,
                                           HttpServletRequest request, RedirectAttributes flashModel, Model model) {

        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", user);
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Your email is already registered...");
            return new ModelAndView("registration", "user", user);
        }

        userService.add(user);
        userService.approveUser(user);
        Roles roles = new Roles();
        roles.setRole("USER");
        roles.setUser(user);
        rolesService.add(roles);

        eventPublisher.publishEvent(
                new EmailConfirmationEvent(this, user, VerificationToken.EMAILVERIFICATION, request.getContextPath()));

        flashModel.addFlashAttribute("message", "For email verification confirm link on your email account...");
        return new ModelAndView("redirect:/registration");
    }

    @RequestMapping("/confirmRegistration")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes model,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = tokenService.isValid(token, VerificationToken.EMAILVERIFICATION);
        if (user == null) {
            model.addFlashAttribute("error", "Your token is invalid please try again");
            return "redirect:/login";
        }
        userService.approveUser(user);
        tokenService.deleteToken(token);
        autoLogin(request, user);
        return "redirect:/home";
    }

    @RequestMapping("/showAddEmail")
    public String showAddEmai() {

        return "addEmail";
    }


    @RequestMapping(value = "/sendforgotPasswordEmailToken", method = RequestMethod.POST)
    public String sendforgotPasswordEmailToken(@RequestParam("email") String email, RedirectAttributes model,
                                               HttpServletRequest request) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addFlashAttribute("error", "Email address is not valid");
            return "redirect:/showAddEmail";
        }
        eventPublisher.publishEvent(
                new EmailConfirmationEvent(this, user, VerificationToken.FORGOTPASSWORD, request.getContextPath()));
        model.addFlashAttribute("message", "please check and verify your email  ..");
        return "redirect:/showAddEmail";
    }

    @RequestMapping("/showForgotPassword")
    public String showForgotPassword(@RequestParam("token") String token, RedirectAttributes flashModel, Model model) {

        User user = tokenService.isValid(token, VerificationToken.FORGOTPASSWORD);
        if (user == null) {
            flashModel.addFlashAttribute("error", "Your token is invalid please try again");
            return "redirect:/login";
        }
        tokenService.deleteToken(token);
        model.addAttribute("email", user.getEmail());
        return "forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgotPassword(HttpServletRequest request, @RequestParam("email") String email,
                                 @RequestParam("password") String password, RedirectAttributes model) {
        User user = userService.forgotPassword(email, password);

        autoLogin(request, user);
        return "redirect:/home";
    }

    private void autoLogin(HttpServletRequest request, User user) {
        try {
            LoggedUserListener loggedUser;
            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(), userDetails.getAuthorities());
            if (auth.isAuthenticated() && user.getEnabled()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                HttpSession session = request.getSession(false);

                if (session != null) {
                    loggedUser = new LoggedUserListener(user.getEmail(), activeUserStore);
                    session.setAttribute("loggedUser", loggedUser);
                    session.setAttribute("userSession", user);
                }
                try {
                    chatService.sendToAllFriendOnlineMessage(user.getEmail());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Problem authenticating user" + user.getEmail() + e);
        }
    }
}
