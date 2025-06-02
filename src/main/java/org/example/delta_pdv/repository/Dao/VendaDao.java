package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Venda;

import java.util.List;

public interface VendaDao {

    List<Venda> findAll();
    Venda findById(Long id);
    long insert(Venda venda);
}
