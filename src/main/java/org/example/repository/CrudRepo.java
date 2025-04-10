package org.example.repository;

import java.util.List;

public interface CrudRepo <T> {

    void save(T entity);

    List<T> findAll();

    T findById(int id);

    T update(T entity);

    void delete(T entity);

    void deleteById(int id);
}
