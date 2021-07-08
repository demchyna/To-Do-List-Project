package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testGetByEmail_1() {
        User user1 = new User();
        user1.setFirstName("Mike");
        user1.setLastName("Green");
        user1.setEmail("mike@mail.com");
        user1.setPassword("1111");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Nick");
        user2.setLastName("Brown");
        user2.setEmail("nick@mail.com");
        user2.setPassword("2222");

        User expected = userRepository.save(user2);
        User actual = userRepository.findByEmail("nick@mail.com");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetByEmail_2() {
        User user = new User();
        user.setFirstName("Mike");
        user.setLastName("Green");
        user.setEmail("mike@mail.com");
        user.setPassword("1111");
        userRepository.save(user);

        User actual = userRepository.findByEmail("nick@mail.com");

        assertNull(actual);
    }
}
