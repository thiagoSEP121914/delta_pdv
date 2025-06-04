package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Produto;

import java.util.List;

public interface GenericDao<T> {

    List<T> findAll();
    T findById(Long id);

    List<Produto> findByName(String name);

    void insert(T object);

    int insertWithId(Long id);
    void update(T object);
    void delete(Long id);

}
