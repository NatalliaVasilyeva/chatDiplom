package com.bsu.nvasilyeva.listener;

import com.bsu.nvasilyeva.entity.Roles;
import com.bsu.nvasilyeva.entity.User;
import com.bsu.nvasilyeva.service.RolesService;
import com.bsu.nvasilyeva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInserterListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserService userService;

    @Autowired
    RolesService roleService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        if (userService.findByEmail("natali1111@tut.by") == null) {

            // Admin Insert

            User user = new User();
            user.setName("Natallia");
            user.setEmail("natali1111@tut.by");
            user.setPassword("natali1111");
            user.setEnabled(true);

            Roles role1 = new Roles();
            role1.setRole("USER");
            role1.setUser(user);

            Roles role2 = new Roles();
            role2.setRole("ADMIN");
            role2.setUser(user);

            userService.add(user);

            roleService.add(role1);
            roleService.add(role2);

        }

        if (userService.findByEmail("vasia@tut.by") == null) {

            User userV = new User();
            userV.setName("Vasia");
            userV.setEmail("vasia@tut.by");
            userV.setPassword("vasia");
            userV.setEnabled(true);

            Roles role3 = new Roles();
            role3.setRole("USER");
            role3.setUser(userV);

            userService.add(userV);

            roleService.add(role3);

        }

    }

}
