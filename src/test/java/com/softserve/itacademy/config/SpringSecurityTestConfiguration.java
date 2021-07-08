package com.softserve.itacademy.config;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class SpringSecurityTestConfiguration {


    @Bean("withRoleAdmin")
    @Primary
    public User testUserWithRoleAdmin() {
        Role role = new Role();
        role.setName("ADMIN");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Mike");
        user.setLastName("Green");
        user.setEmail("mike@mail.com");
        user.setPassword("1111");
        user.setRole(role);

        return user;
    }

    @Bean("withRoleUser")
    @Primary
    public User testUserWithRoleUser() {
        Role role = new Role();
        role.setName("USER");

        User user = new User();
        user.setId(2L);
        user.setFirstName("Nick");
        user.setLastName("Brown");
        user.setEmail("nick@mail.com");
        user.setPassword("2222");
        user.setRole(role);

        return user;
    }
}
