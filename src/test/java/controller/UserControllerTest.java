package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Riya");
        u1.setEmail("riya@gmail.com");

        List<User> users = Arrays.asList(u1);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Riya"))
                .andExpect(jsonPath("$[0].email").value("riya@gmail.com"));
    }

    @Test
    void testAddUser() throws Exception {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Riya");
        u1.setEmail("riya@gmail.com");

        when(userService.addUser(any(User.class))).thenReturn(u1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Riya\",\"email\":\"riya@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Riya"))
                .andExpect(jsonPath("$.email").value("riya@gmail.com"));
    }
}
