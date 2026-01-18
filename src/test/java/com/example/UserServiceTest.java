package com.example.service;


import com.example.model.User;
import com.example.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repo;
    private UserService service;

    @BeforeEach
    void setUp() {
        repo = mock(UserRepository.class);
        service = new UserService(repo);
    }

    @Test
    void testGetAllUsers() {
        User u1 = new User(); u1.setName("Riya"); u1.setEmail("riya@gmail.com");
        User u2 = new User(); u2.setName("Alex"); u2.setEmail("alex@gmail.com");

        when(repo.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<User> users = service.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("Riya", users.get(0).getName());
    }

    @Test
    void testGetUserById() {
        User u = new User(); u.setId(1L); u.setName("Riya");

        when(repo.findById(1L)).thenReturn(Optional.of(u));

        User result = service.getUserById(1L);
        assertNotNull(result);
        assertEquals("Riya", result.getName());
    }

    @Test
    void testCreateUser() {
        User u = new User(); u.setName("Riya");

        when(repo.save(u)).thenReturn(u);

        User result = service.addUser(u);
        assertEquals("Riya", result.getName());
    }

    @Test
    void testUpdateUser() {
        User existing = new User(); existing.setId(1L); existing.setName("Old");
        User updated = new User(); updated.setName("New"); updated.setEmail("new@gmail.com");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(User.class))).thenReturn(updated);

        User result = service.updateUser(1L, updated);
        assertEquals("New", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(repo).deleteById(1L);
        service.deleteUser(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
