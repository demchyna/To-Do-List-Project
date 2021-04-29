package com.softserve.itacademy;

import com.softserve.itacademy.repository.StateRepositoryTest;
import com.softserve.itacademy.repository.TaskRepositoryTest;
import com.softserve.itacademy.repository.UserRepositoryTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SelectClasses({
        StateRepositoryTest.class,
        TaskRepositoryTest.class,
        UserRepositoryTest.class
})
class ToDoListApplicationTests {  }
