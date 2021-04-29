package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.security.WebAuthenticationToken;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    public void testCreateGetMethod() throws Exception {
        mvc.perform(get("/users/create")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("create-user"))
                .andExpect(forwardedUrl(null))
                .andExpect(model().size(1))
                .andExpect(model().attribute("user", new User()));
    }

    @Test
    public void testCreatePostMethod() throws Exception {

        when(roleService.readById(2L)).thenReturn(new Role());

        mvc.perform(post("/users/create")
                .content("firstName=Nick&lastName=Brown&email=brown@mail.com&password=2222")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

    }
}
