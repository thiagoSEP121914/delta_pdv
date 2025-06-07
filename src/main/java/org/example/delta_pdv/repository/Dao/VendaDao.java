package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Venda;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface VendaDao {

    List<Venda> findAll();
    Venda findById(Long id);
    List<Venda> findAllvendaPorData(Date date);
    List<Venda> findAllVendasHoje();
    Double getFaturamentoTotal();
    double getVendasHoje();
    Map<String, Double> getFaturamentoPorMes();
    double getFaturamentoMesAtual();
    long insert(Venda venda);
}
