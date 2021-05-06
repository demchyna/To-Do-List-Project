package com.softserve.itacademy.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 0;
    String firstName() default  "";
    String lastName() default "";
    String email() default "";
    String password() default "";
    String role() default "USER";
}
