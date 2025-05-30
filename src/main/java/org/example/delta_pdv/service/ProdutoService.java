package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private final GenericDao<Produto> produtoRepository = DaoFactory.createProdutoDao();

    public List<Produto> findAll() {
       return produtoRepository.findAll();
    }
    public Object findById(Long id) {
        if (id <= 0) {
            throw new RuntimeException("O id informado não existe!!");
        }
        return produtoRepository.findById(id);
    }
    public void insert(Produto produto) throws SQLException {
        produtoRepository.insert(produto);
    }
    public void update(Produto novoProduto) {
        produtoRepository.update(novoProduto);
    }
    public void delete(Long id) {
        produtoRepository.delete(id);
    }
}
