package com.softserve.itacademy.controller;

import com.softserve.itacademy.ToDoListApplication;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.security.WebAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        Role role = new Role();
        role.setName("ADMIN");

        User user = new User();
        user.setFirstName("Mike");
        user.setLastName("Green");
        user.setEmail("green@mail.com");
        user.setPassword("1111");
        user.setRole(role);

        WebAuthenticationToken token = new WebAuthenticationToken(user);
        token.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Test
    public void testHomeGetMethod() throws Exception {
        mvc.perform(get("/home")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(forwardedUrl(null));
    }


}
