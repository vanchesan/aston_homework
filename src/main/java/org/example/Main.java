package org.example;


import org.example.Service.UserService;
import org.example.cli.UserCli;
import org.example.repository.UserRepo;
import org.example.repository.UserRepoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        UserRepo userRepo = new UserRepoImpl(sessionFactory);
        UserService userService = new UserService(userRepo);
        UserCli userCli = new UserCli(userService);

       userCli.start();
       sessionFactory.close();

    }


}