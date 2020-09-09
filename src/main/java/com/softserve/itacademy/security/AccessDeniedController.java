package com.softserve.itacademy.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccessDeniedController {
    @GetMapping(path = "/forbidden")
    public String accessPage() {
        return "access-denied";
    }
}
