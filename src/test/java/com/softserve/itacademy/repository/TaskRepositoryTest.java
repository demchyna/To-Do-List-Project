package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@DataJpaTest
public class TaskRepositoryTest {

    private final ToDoRepository todoRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskRepositoryTest(ToDoRepository todoRepository, TaskRepository taskRepository) {
        this.todoRepository = todoRepository;
        this.taskRepository = taskRepository;
    }

    @Test
    @DisplayName("Get tasks by the todo ID when two tasks belong to the same todo")
    public void testGetByTodoId_1() {
        ToDo todo = new ToDo();
        todo.setTitle("test todo");
        todo.setCreatedAt(LocalDateTime.now());
        todo = todoRepository.save(todo);

        Task task1 = new Task();
        task1.setName("test task #1");
        task1.setTodo(todo);
        task1 = taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("test task #2");
        task2.setTodo(todo);
        task2 = taskRepository.save(task2);

        List<Task> expected = List.of(task1, task2);
        List<Task> actual = taskRepository.getByTodoId(todo.getId());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("Get tasks by the todo ID when two tasks belong to the different todo")
    public void testGetByTodoId_2() {
        ToDo todo1 = new ToDo();
        todo1.setTitle("test todo #1");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1 = todoRepository.save(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("test todo #2");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2 = todoRepository.save(todo2);

        Task task1 = new Task();
        task1.setName("test task #1");
        task1.setTodo(todo1);
        task1 = taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("test task #2");
        task2.setTodo(todo2);
        taskRepository.save(task2);

        List<Task> expected = List.of(task1);
        List<Task> actual = taskRepository.getByTodoId(todo1.getId());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("Get tasks by the todo ID when two tasks don't belong todo")
    public void testGetByTodoId_3() {
        ToDo todo1 = new ToDo();
        todo1.setTitle("test todo #1");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1 = todoRepository.save(todo1);

        ToDo todo2 = new ToDo();
        todo2.setTitle("test todo #2");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2 = todoRepository.save(todo2);

        Task task1 = new Task();
        task1.setName("test task #1");
        task1.setTodo(todo1);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("test task #2");
        task2.setTodo(todo1);
        taskRepository.save(task2);

        List<Task> expected = List.of();
        List<Task> actual = taskRepository.getByTodoId(todo2.getId());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
