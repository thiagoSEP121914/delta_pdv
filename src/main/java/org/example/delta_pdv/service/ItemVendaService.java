package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.ItemVenda;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.repository.Dao.ItemVendaDao;
import org.example.delta_pdv.repository.Dao.VendaDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;

public class ItemVendaService {

    private ItemVendaDao itemVendaRepository = DaoFactory.createItemvendaDao();
    private ProdutoService produtoService = new ProdutoService();
    private VendaService vendaService = new VendaService();


    public List<ItemVenda> findAll() {
        List<ItemVenda> itens = itemVendaRepository.findAll(); // traz IDs e dados básicos
        for (ItemVenda item : itens) {
            Produto produtoCompleto = produtoService.findById(item.getProduto().getIdProduto());
            item.setProduto(produtoCompleto);
            Venda vendaCompleta = vendaService.findById(item.getVenda().getIdVenda());
            item.setVenda(vendaCompleta);
        }
        return itens;
    }

    public ItemVenda findById(long id) {
        ItemVenda itemVenda = itemVendaRepository.findById(id);

        if (itemVenda.getidItemVenda() == null) {
            return null;
        }

        Venda venda = vendaService.findById(itemVenda.getVenda().getIdVenda());
        Produto produto = produtoService.findById(itemVenda.getProduto().getIdProduto());
        produto.calcularLucro();
        itemVenda.setVenda(venda);
        itemVenda.setProduto(produto);
        return itemVenda;
    }
    public List<ItemVenda> findByVenda(Long idVenda) {
        List<ItemVenda> itens = itemVendaRepository.findByVenda(idVenda);
        for (ItemVenda item : itens) {
            Produto produtoCompleto = produtoService.findByIdIncludesInative(item.getProduto().getIdProduto());
            item.setProduto(produtoCompleto);
            Venda vendaCompleta = vendaService.findById(item.getVenda().getIdVenda());
            item.setVenda(vendaCompleta);
        }
        return itens;
    }


    public long insert(ItemVenda itemVenda) {
        if (itemVenda == null) {
            throw new IllegalArgumentException("ItemVenda não pode ser nulo");
        }
        if (itemVenda.getVenda() == null || itemVenda.getVenda().getIdVenda() == null) {
            throw new IllegalArgumentException("Venda deve estar preenchida com id");
        }
        if (itemVenda.getProduto() == null || itemVenda.getProduto().getIdProduto() == null) {
            throw new IllegalArgumentException("Produto deve estar preenchido com id");
        }
        if (itemVenda.getQtd() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (itemVenda.getPrecoUnitario() <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior que zero");
        }
        return itemVendaRepository.insert(itemVenda);
    }

}
