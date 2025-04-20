package org.example.Service;

import org.example.Enity.User;
import org.example.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        userService = new UserService(userRepo);
    }

    @Test
    void createUser_ValidInput_Success() {
        String name = "John";
        String email = "john@example.com";
        Integer age = 25;

        doNothing().when(userRepo).save(any(User.class));

        assertDoesNotThrow(() -> userService.createUser(name, email, age));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void createUser_InvalidEmail_ThrowsException() {
        String name = "John";
        String email = "not-email";
        Integer age = 25;

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(name, email, age));

        assertTrue(ex.getMessage().contains("Неккоретный email1"));
    }

    @Test
    void getAllUsers_ReturnsList() {
        when(userRepo.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void updateUser_ValidData_Success() {
        User existing = User.builder()
                .id(1)
                .name("Old")
                .email("old@email.com")
                .age(30)
                .created_at(LocalDate.now())
                .build();

        when(userRepo.findById(1)).thenReturn(existing);
        doNothing().when(userRepo).update(any(User.class));

        userService.updateUser(1, "New", "new@email.com", 35);

        verify(userRepo).update(argThat(user ->
                user.getName().equals("New") &&
                        user.getEmail().equals("new@email.com") &&
                        user.getAge() == 35));
    }

    @Test
    void deleteUser_CallsRepo() {
        doNothing().when(userRepo).deleteById(1);
        userService.deleteUser(1);
        verify(userRepo, times(1)).deleteById(1);
    }
}
