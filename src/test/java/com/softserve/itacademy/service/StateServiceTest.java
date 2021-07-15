package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
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
public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    private State expected;

    @BeforeEach
    public void setUp() {
        expected = new State();
        expected.setName("test state");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    public void testCorrectCreate() {
        when(stateRepository.save(expected)).thenReturn(expected);
        State actual = stateService.create(expected);

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> stateService.create(null)
        );

        assertEquals("State cannot be 'null'", exception.getMessage());
        verify(stateRepository, never()).save(new State());
    }

    @Test
    public void testCorrectReadById() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        State actual = stateService.readById(anyLong());

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> stateService.readById(anyLong())
        );

        assertEquals("State with id 0 not found", exception.getMessage());
        verify(stateRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCorrectUpdate() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(stateRepository.save(expected)).thenReturn(expected);
        State actual = stateService.update(expected);

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).findById(anyLong());
        verify(stateRepository, times(1)).save(expected);
        verifyNoMoreInteractions(stateRepository);
    }

    @Test
    public void testExceptionUpdate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> stateService.update(null)
        );

        assertEquals("State cannot be 'null'", exception.getMessage());
        verify(stateRepository, never()).save(new State());
    }

    @Test
    public void testDelete() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(new State()));
        doNothing().when(stateRepository).delete(any(State.class));
        stateService.delete(anyLong());

        verify(stateRepository, times(1)).findById(anyLong());
        verify(stateRepository, times(1)).delete(any(State.class));
    }

    @Test
    public void testGetAll() {
        List<State> expected = List.of(new State(), new State(), new State());

        when(stateRepository.getAll()).thenReturn(expected);
        List<State> actual = stateService.getAll();

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).getAll();
    }

    @Test
    public void testCorrectGetByName() {
        when(stateRepository.findByName(anyString())).thenReturn(expected);
        State actual = stateService.getByName(anyString());

        assertEquals(expected, actual);
        verify(stateRepository, times(1)).findByName(anyString());
    }

    @Test
    public void testExceptionGetByName() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> stateService.getByName(anyString())
        );

        assertEquals("State with name '' not found", exception.getMessage());
        verify(stateRepository, times(1)).findByName(anyString());
    }
}
