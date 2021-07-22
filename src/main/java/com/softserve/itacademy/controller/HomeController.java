package com.softserve.itacademy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/", "home"})
    public String home() {

        logger.info("Home page was loaded!");
        return "home";
    }
}
