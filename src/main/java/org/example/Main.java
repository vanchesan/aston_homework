package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.Enity.User;
import org.example.repository.UserRepo;
import org.example.repository.UserRepoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        EntityManager em = sessionFactory.createEntityManager();

        UserRepo userRepo = new UserRepoImpl(em);
        User user = User.builder()
                .name("Kim")
                .email("kim@gmail1.com")
                .created_at(LocalDate.now())
                .age(45)
                .build();
        userRepo.save(user);
        System.out.println(userRepo.findById(1).toString());
        System.out.println();
        System.out.println(userRepo.findAll().toString());
        userRepo.delete(user);
        System.out.println(userRepo.findAll().toString());
        userRepo.deleteById(1);

    }
}