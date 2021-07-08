package com.softserve.itacademy.controller;

import com.softserve.itacademy.config.SpringSecurityTestConfiguration;
import com.softserve.itacademy.config.WithMockCustomUser;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { UserController.class, SpringSecurityTestConfiguration.class })
public class UserControllerTest {

    @MockBean private UserService userService;
    @MockBean private RoleService roleService;
    @MockBean private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;

    @Autowired
    @Qualifier("withRoleAdmin")
    private User userWithRoleAdmin;

    @Autowired
    @Qualifier("withRoleUser")
    private User userWithRoleUser;

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testCreateGetMethod() throws Exception {
        mvc.perform(get("/users/create")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("create-user"))
                .andExpect(model().size(1))
                .andExpect(model().attribute("user", new User()))
                .andDo(print());

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testCorrectCreatePostMethod() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("");
        when(roleService.readById(anyLong())).thenReturn(new Role());
        when(userService.create(any(User.class))).thenReturn(new User());

        mvc.perform(post("/users/create")
                .param("firstName", userWithRoleAdmin.getFirstName())
                .param("lastName", userWithRoleAdmin.getLastName())
                .param("email", userWithRoleAdmin.getEmail())
                .param("password", userWithRoleAdmin.getPassword())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todos/all/users/0"))
                .andDo(print());

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleService, times(1)).readById(anyLong());
        verify(userService, times(1)).create(any(User.class));

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testErrorCreatePostMethod() throws Exception {
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPassword("");

        mvc.perform(post("/users/create")
                .param("firstName", user.getFirstName())
                .param("lastName", user.getLastName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("create-user"))
                .andExpect(model().size(1))
                .andExpect(model().attribute("user", user))
                .andDo(print());

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testReadGetMethod() throws Exception {
        when(userService.readById(anyLong())).thenReturn(userWithRoleAdmin);

        mvc.perform(get("/users/1/read")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info"))
                .andExpect(model().size(1))
                .andExpect(model().attribute("user", userWithRoleAdmin))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testUpdateGetMethod() throws Exception {
        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");

        when(userService.readById(anyLong())).thenReturn(userWithRoleUser);
        when(roleService.getAll()).thenReturn(List.of(role1, role2));

        mvc.perform(get("/users/1/update")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("update-user"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("user", userWithRoleUser))
                .andExpect(model().attribute("roles", List.of(role1, role2)))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());
        verify(roleService, times(1)).getAll();

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "nick@mail.com")
    public void testCorrectUpdatePostMethodWithRoleUSERAndCorrectPassword() throws Exception {
        when(userService.readById(anyLong())).thenReturn(userWithRoleUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("");

        mvc.perform(post("/users/2/update")
                .param("firstName", userWithRoleUser.getFirstName())
                .param("lastName", userWithRoleUser.getLastName())
                .param("email", userWithRoleUser.getEmail())
                .param("oldPassword", "2222")
                .param("password", userWithRoleUser.getPassword())
                .param("roleId", "2")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/2/read"))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleService, never()).readById(anyLong());
        verify(userService, times(1)).update(any(User.class));

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testCorrectUpdatePostMethodWithRoleADMINAndCorrectPassword() throws Exception {
        when(userService.readById(anyLong())).thenReturn(userWithRoleAdmin);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("");
        when(roleService.readById(anyLong())).thenReturn(any(Role.class));

        mvc.perform(post("/users/1/update")
                .param("firstName", userWithRoleAdmin.getFirstName())
                .param("lastName", userWithRoleAdmin.getLastName())
                .param("email", userWithRoleAdmin.getEmail())
                .param("oldPassword", "1111")
                .param("password", userWithRoleAdmin.getPassword())
                .param("roleId", "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/1/read"))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleService, times(1)).readById(anyLong());
        verify(userService, times(1)).update(any(User.class));

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testErrorUpdatePostMethodWithRoleADMINAndInvalidPassword() throws Exception {
        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");

        when(userService.readById(anyLong())).thenReturn(userWithRoleAdmin);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(roleService.getAll()).thenReturn(List.of(role1, role2));

        mvc.perform(post("/users/1/update")
                .param("firstName", userWithRoleAdmin.getFirstName())
                .param("lastName", userWithRoleAdmin.getLastName())
                .param("email", userWithRoleAdmin.getEmail())
                .param("oldPassword", "1111")
                .param("password", userWithRoleAdmin.getPassword())
                .param("roleId", "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-user"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("user", userWithRoleAdmin))
                .andExpect(model().attribute("roles", List.of(role1, role2)))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(roleService, times(1)).getAll();

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testErrorUpdatePostMethod() throws Exception {
        Role role1 = new Role();
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setName("USER");

        User user = new User();
        user.setId(1L);
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPassword("");
        user.setRole(role1);

        when(userService.readById(anyLong())).thenReturn(user);
        when(roleService.getAll()).thenReturn(List.of(role1, role2));

        mvc.perform(post("/users/1/update")
                .param("firstName", user.getFirstName())
                .param("lastName", user.getLastName())
                .param("email", user.getEmail())
                .param("oldPassword", "")
                .param("password", user.getPassword())
                .param("roleId", "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("update-user"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("roles", List.of(role1, role2)))
                .andDo(print());

        verify(userService, times(1)).readById(anyLong());
        verify(roleService, times(1)).getAll();

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(id = 1, email = "mike@mail.com", role = "ADMIN")
    public void testDeleteGetMethodOneself() throws Exception {
        mvc.perform(get("/users/1/delete")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-form"))
                .andDo(print());

        verify(userService, times(1)).delete(anyLong());

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(id = 1, email = "mike@mail.com", role = "ADMIN")
    public void testDeleteGetMethodAnotherUser() throws Exception {
        mvc.perform(get("/users/2/delete")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"))
                .andDo(print());

        verify(userService, times(1)).delete(anyLong());

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }

    @Test
    @WithMockCustomUser(email = "mike@mail.com", role = "ADMIN")
    public void testGetAllGetMethod() throws Exception {
        when(userService.getAll()).thenReturn(List.of(new User(), new User(), new User()));

        mvc.perform(get("/users/all")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("users-list"))
                .andExpect(model().size(1))
                .andExpect(model().attribute("users",
                        List.of(new User(), new User(), new User())))
                .andDo(print());

        verify(userService, times(1)).getAll();

        verifyNoMoreInteractions(passwordEncoder, roleService, userService);
    }
}
