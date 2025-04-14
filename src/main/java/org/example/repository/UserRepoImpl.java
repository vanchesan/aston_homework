package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.example.Enity.User;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {
    private final SessionFactory sessionFactory;


    @Override
    public void save(User entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder("Ошибки валидации:\n");
            for (ConstraintViolation<User> violation : violations) {
                errors.append("- ").append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(errors.toString());
        }

        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            System.out.println("Запись сохранена");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = sessionFactory.createEntityManager();
        List<User> user = em.createQuery("from User", User.class).getResultList();
        em.close();
        return user;

    }

    @Override
    public User findById(int id) {
        EntityManager em = sessionFactory.createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }

    @Override
    public void update(int id) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            System.out.println("Изменить имя");
            user.setName(scanner.next());
            System.out.println("Изменить почту");
            user.setEmail(scanner.next());
            System.out.println("Изменить возраст");
            user.setAge(Integer.parseInt(scanner.next()));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(User entity) {
        EntityManager em = sessionFactory.getCurrentSession();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, entity.getId());
            if (user != null) {
                em.remove(entity);
            }
            System.out.println("Запись успешено удалена");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    @Override
    public void deleteById(int id) {
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            transaction.commit();
            System.out.println("Запись успешно удалена");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
