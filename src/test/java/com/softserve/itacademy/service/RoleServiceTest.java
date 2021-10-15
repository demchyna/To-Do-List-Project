package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.impl.RoleServiceImpl;
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
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role expected;

    @BeforeEach
    public void setUp() {
        expected = new Role();
        expected.setName("test role");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    public void testCorrectCreate() {
        when(roleRepository.save(expected)).thenReturn(expected);
        Role actual = roleService.create(expected);

        assertEquals(expected, actual);
        verify(roleRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionCreate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> roleService.create(null)
        );

        assertEquals("Role cannot be 'null'", exception.getMessage());
        verify(roleRepository, never()).save(new Role());
    }

    @Test
    public void testCorrectReadById() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Role actual = roleService.readById(anyLong());

        assertEquals(expected, actual);
        verify(roleRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testExceptionReadById() {
        Exception exception = assertThrows(EntityNotFoundException.class, ()
                -> roleService.readById(anyLong())
        );

        assertEquals("Role with id 0 not found", exception.getMessage());
        verify(roleRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCorrectUpdate() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(roleRepository.save(expected)).thenReturn(expected);
        Role actual = roleService.update(expected);

        assertEquals(expected, actual);
        verify(roleRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).save(expected);
    }

    @Test
    public void testExceptionUpdate() {
        Exception exception = assertThrows(NullEntityReferenceException.class, ()
                -> roleService.update(null)
        );

        assertEquals("Role cannot be 'null'", exception.getMessage());
        verify(roleRepository, never()).save(new Role());
    }

    @Test
    public void testDelete() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(roleRepository).delete(any(Role.class));
        roleService.delete(anyLong());

        verify(roleRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).delete(any(Role.class));
    }

    @Test
    public void testGetAll() {
        List<Role> expected = List.of(new Role(), new Role(), new Role());

        when(roleRepository.findAll()).thenReturn(expected);
        List<Role> actual = roleService.getAll();

        assertEquals(expected, actual);
        verify(roleRepository, times(1)).findAll();
    }
}
