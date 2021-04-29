package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task expected;

    @BeforeEach
    public void setUp() {
        expected = new Task();
        expected.setName("test task");
        expected.setPriority(Priority.MEDIUM);
        expected.setState(new State());
        expected.setTodo(new ToDo());
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    public void testCorrectCreate() {
        when(taskRepository.save(expected)).thenReturn(expected);
        Task actual = taskService.create(expected);

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> taskService.create(null)
        );

        assertEquals("Task cannot be 'null'", exception.getMessage());
        verify(taskRepository, never()).save(new Task());
    }

    @Test
    public void testCorrectReadById() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Task actual = taskService.readById(anyLong());

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> taskService.readById(anyLong())
        );

        assertEquals("Task with id 0 not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCorrectUpdate() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(taskRepository.save(expected)).thenReturn(expected);
        Task actual = taskService.update(expected);

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionUpdate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> taskService.update(null)
        );

        assertEquals("Task cannot be 'null'", exception.getMessage());
        verify(taskRepository, never()).save(new Task());
    }

    @Test
    public void testDelete() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        doNothing().when(taskRepository).delete(any(Task.class));
        taskService.delete(anyLong());

        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).delete(any(Task.class));
    }

    @Test
    public void testGetAll() {
        List<Task> expected = List.of(new Task(), new Task(), new Task());

        when(taskRepository.findAll()).thenReturn(expected);
        List<Task> actual = taskService.getAll();

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetByTodoId() {
        List<Task> expected = List.of(new Task(), new Task(), new Task());

        when(taskRepository.getByTodoId(anyLong())).thenReturn(expected);
        List<Task> actual = taskService.getByTodoId(anyLong());

        assertEquals(expected, actual);
        verify(taskRepository, times(1)).getByTodoId(anyLong());
    }
}
