package com.uh635c.task13.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();
    T getById(Long id);
    T save(T t);
    T update(T t);
    void deleteById(Long id);
}
