package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;

import java.util.List;

public interface CategoriaDao {

    List<Categoria> findAll();
    Categoria findById(Long id);
    void insert(Categoria categoria);
    void delete(String nome);

}
