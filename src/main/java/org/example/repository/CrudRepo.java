package org.example.repository;

import org.example.Enity.User;

import java.util.List;

public interface CrudRepo <T> {

    void save(T entity);

    List<T> findAll();

    T findById(int id);

    void update(User user);

    void delete(T entity);

    void deleteById(int id);
}
