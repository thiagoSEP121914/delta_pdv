package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;
import java.util.List;

public class ProdutoService {

    private final GenericDao<Produto> produtoRepository = DaoFactory.createProdutoDao();

    public List<Produto> findAll() {
       return produtoRepository.findAll();
    }


    public Produto findById(Long id) {
        if (id == null || id <= 0) return null;
        return produtoRepository.findById(id);
    }

    public List<Produto> findByName(String name) {
        return produtoRepository.findByName(name);
    }

    private void insert(Produto produto)  {
        produtoRepository.insert(produto);
    }
    private void update(Produto novoProduto) {
        produtoRepository.update(novoProduto);
    }
    public void delete(Long id) {
        produtoRepository.delete(id);
    }

    // Estrategia upSet
    public void saveProducts(Produto produtoNovo) {
        Produto produtoExistente = findById(produtoNovo.getIdProduto());
        if (produtoExistente != null) {
            this.update(produtoNovo);
            return;
        }
        this.insert(produtoNovo);
    }
}
