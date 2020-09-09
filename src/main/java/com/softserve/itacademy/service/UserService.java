package com.softserve.itacademy.service;

import com.softserve.itacademy.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();

    UserDetails loadUserByUsername(String username);
}
