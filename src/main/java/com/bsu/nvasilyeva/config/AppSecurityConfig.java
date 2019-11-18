package com.bsu.nvasilyeva.config;

import com.bsu.nvasilyeva.dto.ActiveUserStore;
import com.bsu.nvasilyeva.handler.FailedLoginHandler;
import com.bsu.nvasilyeva.handler.SuccessLoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Set access to the pages in dependence on roles and authentication of person
 */


@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailService;

    @Autowired
    SuccessLoginHandler loginHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().accessDeniedPage("/home")
                .and().authorizeRequests()
                .antMatchers("/home", "/registration", "/logout", "/login", "/", "/**", "/resources/**")
                .permitAll()
                .antMatchers("/showProfile", "/updateProfileImage", "/getUserProfile", "/handleEditProfile",
                        "/discoverUser", "/friendPendingRequest", "/friendRequest", "friendList").hasRole("USER").anyRequest().authenticated()
                .and().formLogin().usernameParameter("email")
                .passwordParameter("password").loginPage("/login")
                .permitAll()
                .failureHandler(new FailedLoginHandler())
                .successHandler(loginHandler).permitAll();
        http.sessionManagement().maximumSessions(1).expiredUrl("/home");
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

}
