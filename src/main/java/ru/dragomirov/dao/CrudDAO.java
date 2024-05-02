package ru.dragomirov.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void save(T entity);
    Optional<T> update(T entity);
    void delete(ID id);
}
