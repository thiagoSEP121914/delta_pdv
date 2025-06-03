package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;
import org.example.delta_pdv.repository.Dao.ProdutoDao;

import java.util.List;
import java.util.Map;

public class ProdutoService {

    private final ProdutoDao produtoRepository = DaoFactory.createProdutoDao();

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

    public List<Produto> findByCategoria(String nomeCategoria) {
        return produtoRepository.findByCategoria(nomeCategoria);
    }

    private void insert(Produto produto)  {
        produtoRepository.insert(produto);
    }
    private void update(Produto novoProduto) {
        produtoRepository.update(novoProduto);
    }

    public int totEstoque () {
        return produtoRepository.getTotaoEstoque();
    }
    public Map<String, Integer> totalVendidos() {

        return produtoRepository.getProdutosMaisVendidos();
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
