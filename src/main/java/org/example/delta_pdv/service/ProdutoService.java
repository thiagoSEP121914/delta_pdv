package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.GenericDao;

import java.util.List;

public class ProdutoService {

    private GenericDao<Produto> repository;

    public ProdutoService(GenericDao GenericDao) {
        this.repository = GenericDao;
    }
    public List<Produto> findAll() {
       return repository.findAll();
    }
    public Produto findById(Long id) {
        if (id <= 0) {
            throw new RuntimeException("O id informado não existe!!");
        }
        return repository.findById(id);
    }
    public void insert(Produto produto) {
        repository.insert(produto);
    }
    public void update(Produto novoProduto) {
        repository.update(novoProduto);
    }
    public void delete(Long id) {
        repository.delete(id);
    }
}
