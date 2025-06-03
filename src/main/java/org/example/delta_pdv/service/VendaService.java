package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.repository.Dao.VendaDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;
import java.util.Map;

public class VendaService {

    private VendaDao vendaRepository = DaoFactory.createVendaDao();

    public List<Venda> findAll() {
       return vendaRepository.findAll();
    }

    public Venda findById(long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> findAllVendasHoje() {
        return vendaRepository.findAllVendasHoje();
    }

    public double getFaturamentoTotal() {
        return vendaRepository.getFaturamentoTotal();
    }
    public double getVendasHoje() {
        return vendaRepository.getVendasHoje();
    }

    public Map<String, Double> faturamentoMensal() {
        return vendaRepository.getFaturamentoPorMes();
    }

    public double getfaturamentoNoMesAtual() {
        return vendaRepository.getFaturamentoMesAtual();
    }

    public long insert(Venda venda) {
        return vendaRepository.insert(venda);
    }



}
