package org.example.Service;

import org.example.Enity.User;
import org.example.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


class UserServiceTest {
    private static final int EXISTED_USER_ID = 1;
    private static final int NOT_EXISTED_USER_ID = 4;
    private static final List<User> EXISTED_USER = List.of(User.builder().id(1)
            .name("Jhon").email("jhon@mail.ru").age(18).created_at(LocalDate.parse("2025-04-17"))
            .build(),
            User.builder().id(2)
                    .name("Nik").email("nik@mail.ru").age(16).created_at(LocalDate.parse("2025-04-17"))
                    .build(),
            User.builder().id(3)
                    .name("Mike").email("mike@mail.ru").age(25).created_at(LocalDate.parse("2025-04-17"))
                    .build());
    @Mock
    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepo);

    }

    @Test
    void createUser() {
        User newUser = User.builder()
                .name("Nik")
                .email("nik@mail.ru")
                .age(16)
                .created_at(LocalDate.parse("2025-04-19"))
                .build();
        userService.createUser("Nik", "nik@mail.ru", 16);
        verify(userRepo, times(1)).save(newUser);
    }

    @Test
    void getAllUsers() {
        when(userRepo.findAll()).thenReturn(EXISTED_USER);
        List<User> users = userService.getAllUsers();
        assertEquals(3, users.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void getUserById() {
        User mockUser = User.builder()
                .id(1)
                .name("Nik")
                .email("nik@mail.ru")
                .age(16)
                .created_at(LocalDate.parse("2025-04-19"))
                .build();
        when(userRepo.findById(EXISTED_USER_ID)).thenReturn(mockUser);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void validateUser() {
    }
}