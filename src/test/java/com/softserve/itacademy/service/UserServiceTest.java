package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.*;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User expected;

    @BeforeEach
    public void setUp() {
        expected = new User();
        expected.setFirstName("Mike");
        expected.setLastName("Green");
        expected.setEmail("green@mail.com");
        expected.setPassword("1111");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    public void testCorrectCreate() {
        when(userRepository.save(expected)).thenReturn(expected);
        User actual = userService.create(expected);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> userService.create(null)
        );

        assertEquals("User cannot be 'null'", exception.getMessage());
        verify(userRepository, never()).save(new User());
    }

    @Test
    public void testCorrectReadById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        User actual = userService.readById(anyLong());

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> userService.readById(anyLong())
        );

        assertEquals("User with id 0 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCorrectUpdate() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(userRepository.save(expected)).thenReturn(expected);
        User actual = userService.update(expected);

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionUpdate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> userService.update(null)
        );

        assertEquals("User cannot be 'null'", exception.getMessage());
        verify(userRepository, never()).save(new User());
    }

    @Test
    public void testDelete() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).delete(any(User.class));
        userService.delete(anyLong());

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    public void testGetAll() {
        List<User> expected = List.of(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.getAll();

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCorrectLoadUserByUsername() {
        when(userRepository.findByEmail(anyString())).thenReturn(expected);
        UserDetails actual = userService.loadUserByUsername(anyString());

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testExceptionLoadUserByUsername() {
        Exception exception = assertThrows(UsernameNotFoundException.class, ()
                -> userService.loadUserByUsername(anyString())
        );

        assertEquals("User not Found!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}
