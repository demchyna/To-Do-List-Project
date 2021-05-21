package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ToDoServiceTest {

    @Mock
    private ToDoRepository todoRepository;

    @InjectMocks
    private ToDoServiceImpl todoService;

    private ToDo expected;

    @BeforeEach
    public void setUp() {
        expected = new ToDo();
        expected.setTitle("test todo #1");
        expected.setCreatedAt(LocalDateTime.now());
        expected.setOwner(new User());
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    public void testCorrectCreate() {
        when(todoRepository.save(expected)).thenReturn(expected);
        ToDo actual = todoService.create(expected);

        assertEquals(expected, actual);
        verify(todoRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> todoService.create(null)
        );

        assertEquals("ToDo cannot be 'null'", exception.getMessage());
        verify(todoRepository, never()).save(new ToDo());
    }

    @Test
    public void testCorrectReadById() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        ToDo actual = todoService.readById(anyLong());

        assertEquals(expected, actual);
        verify(todoRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> todoService.readById(anyLong())
        );

        assertEquals("ToDo with id 0 not found", exception.getMessage());
        verify(todoRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCorrectUpdate() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(todoRepository.save(expected)).thenReturn(expected);
        ToDo actual = todoService.update(expected);

        assertEquals(expected, actual);
        verify(todoRepository, times(1)).findById(anyLong());
        verify(todoRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionUpdate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> todoService.update(null)
        );

        assertEquals("ToDo cannot be 'null'", exception.getMessage());
        verify(todoRepository, never()).save(new ToDo());
    }

    @Test
    public void testDelete() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(new ToDo()));
        doNothing().when(todoRepository).delete(any(ToDo.class));
        todoService.delete(anyLong());

        verify(todoRepository, times(1)).findById(anyLong());
        verify(todoRepository, times(1)).delete(any(ToDo.class));
    }

    @Test
    public void testGetAll() {
        List<ToDo> expected = List.of(new ToDo(), new ToDo(), new ToDo());

        when(todoRepository.findAll()).thenReturn(expected);
        List<ToDo> actual = todoService.getAll();

        assertEquals(expected, actual);
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    public void testGetByUserId() {
        List<ToDo> expected = List.of(new ToDo(), new ToDo(), new ToDo());

        when(todoRepository.getByUserId(anyLong())).thenReturn(expected);
        List<ToDo> actual = todoService.getByUserId(anyLong());

        assertEquals(expected, actual);
        verify(todoRepository, times(1)).getByUserId(anyLong());
    }
}
