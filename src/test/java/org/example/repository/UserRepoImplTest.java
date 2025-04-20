package org.example.repository;

import org.example.Enity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepoImplTest {

    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private static SessionFactory sessionFactory;
    private UserRepo userRepo;

    @BeforeAll
    static void startContainer() {
        postgres.start();

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml") // можешь сделать hibernate.test.cfg.xml
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword());

        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void stopContainer() {
        sessionFactory.close();
        postgres.stop();
    }

    @BeforeEach
    void setup() {
        userRepo = new UserRepoImpl(sessionFactory);
    }

    @Test
    void saveAndFindById() {
        User user = User.builder()
                .name("Alice")
                .email("alice@mail.com")
                .age(30)
                .created_at(LocalDate.now())
                .build();

        userRepo.save(user);

        List<User> all = userRepo.findAll();
        assertEquals(1, all.size());

        User found = userRepo.findById(all.get(0).getId());
        assertEquals("Alice", found.getName());
    }

    @Test
    void deleteById_RemovesUser() {
        User user = User.builder()
                .name("ToDelete")
                .email("del@mail.com")
                .age(40)
                .created_at(LocalDate.now())
                .build();

        userRepo.save(user);
        int id = userRepo.findAll().get(0).getId();

        userRepo.deleteById(id);
        assertNull(userRepo.findById(id));
    }
}
