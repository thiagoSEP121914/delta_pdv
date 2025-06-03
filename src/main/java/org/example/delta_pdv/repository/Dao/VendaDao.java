package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Venda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface VendaDao {

    List<Venda> findAll();
    Venda findById(Long id);

    List<Venda> findAllVendasHoje();

    Double getFaturamentoTotal();

    double getVendasHoje();

    public Map<String, Double> getFaturamentoPorMes();

    public double getFaturamentoMesAtual();


    long insert(Venda venda);
}
