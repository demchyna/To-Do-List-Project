package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@DataJpaTest
public class ToDoRepositoryTest {

    private final UserRepository userRepository;
    private final ToDoRepository todoRepository;

    @Autowired
    public ToDoRepositoryTest(UserRepository userRepository, ToDoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @Test
    public void testGetByUserId_1() {
        User user1 = new User();
        user1.setFirstName("Mike");
        user1.setLastName("Green");
        user1.setEmail("mike@mail.com");
        user1.setPassword("1111");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Nick");
        user2.setLastName("Brown");
        user2.setEmail("nick@mail.com");
        user2.setPassword("2222");
        user2 = userRepository.save(user2);

        ToDo todo1 = new ToDo();
        todo1.setTitle("test todo #1");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setOwner(user1);
        todo1 = todoRepository.save(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("test todo #2");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setCollaborators(List.of(user1, user2));
        todo2 = todoRepository.save(todo2);

        ToDo todo3 = new ToDo();
        todo3.setTitle("test todo #3");
        todo3.setCreatedAt(LocalDateTime.now());
        todo3.setOwner(user2);
        todoRepository.save(todo3);

        List<ToDo> expected = List.of(todo1, todo2);
        List<ToDo> actual = todoRepository.getByUserId(user1.getId());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testGetByUserId_2() {
        User user1 = new User();
        user1.setFirstName("Mike");
        user1.setLastName("Green");
        user1.setEmail("mike@mail.com");
        user1.setPassword("1111");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Nick");
        user2.setLastName("Brown");
        user2.setEmail("nick@mail.com");
        user2.setPassword("2222");
        user2 = userRepository.save(user2);

        ToDo todo1 = new ToDo();
        todo1.setTitle("test todo #1");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setOwner(user1);
        todoRepository.save(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("test todo #2");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setCollaborators(List.of(user1));
        todoRepository.save(todo2);

        List<ToDo> expected = List.of();
        List<ToDo> actual = todoRepository.getByUserId(user2.getId());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
