package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Produto;

import java.util.List;
import java.util.Map;

public interface ProdutoDao {

    List<Produto> findAll();
    Produto findById(Long id);

    List<Produto> findByName(String name);

    List<Produto> findByCategoria(String nomeCategoria);

    Map<String, Integer> getProdutosMaisVendidos();


    int getTotaoEstoque();

    void insert(Produto object);
    void update(Produto object);
    void delete(Long id);

}
