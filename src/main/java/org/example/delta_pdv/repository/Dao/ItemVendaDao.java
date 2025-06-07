package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.ItemVenda;

import java.util.List;

public interface ItemVendaDao {
    List<ItemVenda> findAll();
    ItemVenda findById(long id);

    List<ItemVenda> findByVenda(Long idVenda);
    long insert(ItemVenda itemVenda);
}
