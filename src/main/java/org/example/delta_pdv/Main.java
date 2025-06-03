package org.example.delta_pdv;

import org.example.delta_pdv.entities.*;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.ItemVendaDao;
import org.example.delta_pdv.repository.Dao.VendaDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;
import org.example.delta_pdv.service.ItemVendaService;
import org.example.delta_pdv.service.VendaService;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        GenericDao<Produto> produtoRepository = DaoFactory.createProdutoDao();

        System.out.println("TESTE DO FIND ALL");
        List<Produto> listaDeProdutos = produtoRepository.findAll();
        listaDeProdutos.forEach(System.out::println);

        System.out.println("\nTESTE DO FIND BY ID");
        Produto produto = produtoRepository.findById(1L);
        System.out.println(produto);

        System.out.println("\nTESTE DO INSERT");
        Produto novoProduto = new Produto();
        novoProduto.setNome("Teclado Mecânico RGB");
        novoProduto.setCaminhoImagem("imagens/teclado.png");
        novoProduto.setDescricao("Teclado mecânico com iluminação RGB");
        novoProduto.setPrecoUnitario(299.90);
        novoProduto.setCusto(200.00);
        novoProduto.setQuantidadeEstoque(50);

        // Criar categoria fictícia (deve existir no banco com esse ID)
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1L); // substitua pelo ID válido
        novoProduto.setCategoria(categoria);
        System.out.println(novoProduto.getCategoria().getIdCategoria());

        produtoRepository.insert(novoProduto);
        System.out.println("Produto inserido!");

        System.out.println("\nTESTE DO UPDATE");
        Produto produtoExistente = produtoRepository.findById(1L);
        produtoExistente.setCategoria(categoria);
        if (produtoExistente != null) {
            produtoExistente.setNome("Produto Atualizado");
            produtoExistente.setPrecoUnitario(159.90);
            produtoExistente.setCusto(100.00);
            produtoExistente.setQuantidadeEstoque(30);
            produtoRepository.update(produtoExistente);
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto com ID 1 não encontrado para atualizar.");
        }

        System.out.println("\nTESTE DO DELETE");
        Long idParaDeletar = 2L; // ID do produto que você deseja deletar
        produtoRepository.delete(idParaDeletar);
        System.out.println("Produto com ID " + idParaDeletar + " deletado com sucesso!");
    }

        GenericDao<Categoria> categoriaService = DaoFactory.createCategoriaDao();

        List<Categoria> listOfCategorias = categoriaService.findAll();

        for (Categoria categoria : listOfCategorias) {
            System.out.println(categoria);
        }
    */

        VendaService vendaService = new VendaService();

        System.out.println(vendaService.getVendasHoje());
        System.out.println(vendaService.getFaturamentoTotal());
    }
}