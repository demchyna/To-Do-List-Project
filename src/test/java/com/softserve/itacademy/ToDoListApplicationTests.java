package com.softserve.itacademy;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({
        "com.softserve.itacademy.controller",
        "com.softserve.itacademy.repository",
        "com.softserve.itacademy.service"
})
public class ToDoListApplicationTests {  }
