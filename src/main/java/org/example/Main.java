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

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        UserRepo userRepo = new UserRepoImpl(sessionFactory);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выбирете действие:");
            System.out.println("1 Создать пользовотеля");
            System.out.println("2 Показать список всех пользователей");
            System.out.println("3 Найти пользователя по ID");
            System.out.println("4 Изменить пользователя");
            System.out.println("5 Удалить пользователя по ID");
            System.out.println("6 Выход из программы");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> userRepo.save(User.builder()

                            .name(scanner.nextLine())
                            .email(scanner.nextLine())
                            .age(Integer.parseInt(scanner.nextLine()))
                            .created_at(LocalDate.now())
                            .build());
                    case 2-> {
                        List<User> users = userRepo.findAll();
                        users.forEach(System.out::println);
                    }
                    case 3 -> System.out.println(userRepo.findById(Integer.parseInt(scanner.nextLine())));
                    case 4 -> userRepo.update (Integer.parseInt(scanner.nextLine()));
                    case 5 -> userRepo.deleteById(Integer.parseInt(scanner.nextLine()));
                    case 6 -> {
                        System.out.println("Выход из программы");
                        return;
                    }
                    default -> System.out.println("Неккоретный выбор");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: необходимо ввести число");
                e.printStackTrace();
            }
        }

    }
}