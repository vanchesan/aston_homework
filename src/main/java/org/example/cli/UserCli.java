package org.example.cli;

import org.example.Service.UserService;

import java.util.Scanner;

public class UserCli {
    private final Scanner scanner;

    private final UserService userService;

    public UserCli(UserService userService) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
    }

    public void start() {
        while (true) {
            showMenu();
            if (checkInput()) break;
        }
    }

    private void showMenu() {
        System.out.println("/-----------------------------------------/");
        System.out.println("Выбирете действие:");
        System.out.println("1 Создать пользовотеля");
        System.out.println("2 Показать список всех пользователей");
        System.out.println("3 Найти пользователя по ID");
        System.out.println("4 Изменить пользователя");
        System.out.println("5 Удалить пользователя по ID");
        System.out.println("6 Выход из программы");
        System.out.println("/-----------------------------------------/");
    }
    private boolean checkInput() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> create();
                case 2 -> findAll();

                case 3 -> findForId();
                case 4 -> update();
                case 5 -> deleteUser();
                case 6 -> {
                    System.out.println("Выход из программы");
                    return true;
                }
                default -> System.out.println("Неккоретный выбор");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: необходимо ввести число");
            e.printStackTrace();
        }
        return false;
    }
    private void create() {
        System.out.println("Введите имя");
        String name = scanner.nextLine();
        System.out.println("Введите email");
        String email = scanner.nextLine();
        System.out.println("Введите возраст");
        Integer age = Integer.parseInt(scanner.nextLine());
        userService.createUser(name, email, age);
        System.out.println("Пользователь создан");
    }

    private void findAll() {
        userService.getAllUsers().forEach(System.out::println);
    }

    private void findForId() {
        System.out.println("Введите ID пользователя");
        Integer id = Integer.parseInt(scanner.nextLine());
        System.out.println(userService.getUserById(id));
    }

    private void update() {
        System.out.println("Введите ID пользователя");
        Integer id = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите новое имя");
        String name = scanner.nextLine();
        System.out.println("Введите новый email");
        String email = scanner.nextLine();
        System.out.println("Введите новый возраст");
        Integer age = Integer.parseInt(scanner.nextLine());
        userService.updateUser(id, name, email, age);
        System.out.println("Пользователь с id=" + id + " изменен" );
    }

    private void deleteUser() {
        System.out.println("Введите ID пользователя, чтобы удалить");
        Integer id = Integer.parseInt(scanner.nextLine());
        userService.deleteUser(id);
    }

}
