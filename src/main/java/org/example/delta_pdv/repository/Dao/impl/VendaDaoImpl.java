package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.entities.FormaPagamento;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.VendaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDaoImpl implements VendaDao {

    private  Connection conn;

    public VendaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Venda> findAll() {
        String sql = "SELECT vendas.*, C.nome "+
                     "FROM vendas "+
                     "LEFT JOIN clientes  C ON vendas.ID_Cliente = C.ID_Cliente";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
            List<Venda> listOfVenda = instantiateListOfVendas(rs);
            return listOfVenda;
        } catch (SQLException exception) {
            throw new RuntimeException("ERRO SQL na TABELA VENDAS :Erro ao buscar vendas: " + exception.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Venda findById(Long id) {
        String sql = "SELECT vendas.*, C.nome AS nome " +
                     "FROM vendas " +
                     "LEFT JOIN clientes C ON vendas.ID_Cliente = C.ID_Cliente "+
                     "WHERE vendas.ID_venda = ?";

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
           pst = conn.prepareStatement(sql);
           pst.setLong(1, id);
           rs = pst.executeQuery();
           if (!rs.next()) {
               return null;
           }
           return instantiateVenda(rs);
        } catch (SQLException exception) {
            throw new RuntimeException("Erro Sql na tabela Vendas: Erro ao buscar vendas por id" + exception.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public long insert(Venda venda) {
        String sql = "INSERT INTO vendas (ID_Cliente, Total, forma_pagamento, DATA_Venda) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setLong(1, venda.getCliente().getIdCliente());
            pst.setDouble(2, venda.getTotal());
            pst.setString(3, venda.getFormaPagamento().name());
            pst.setDate(4, new java.sql.Date(venda.getDataVenda().getTime()));
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Falha ao inserir venda: nenhuma linha afetada.");
            }

            rs = pst.getGeneratedKeys();

            if (!rs.next()) {
                throw new RuntimeException("Falha ao inserir na tabela de vendas.");
            }
            Long idGerado = rs.getLong(1);
            return idGerado;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro SQL ao inserir venda: " + exception.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }


    private List<Venda> instantiateListOfVendas(ResultSet rs) throws SQLException {
        List<Venda> listOfVenda = new ArrayList<>();
        while (rs.next()) {
            Venda venda = instantiateVenda(rs);
            listOfVenda.add(venda);
        }
        return listOfVenda;
    }

    private Venda instantiateVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setIdVenda(rs.getLong("ID_venda"));

        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getLong("ID_Cliente"));
        cliente.setNome(rs.getString("nome"));

        venda.setCliente(cliente);
        venda.setTotal(rs.getDouble("Total"));
        venda.setFormaPagamento(FormaPagamento.valueOf(rs.getString("forma_pagamento")));
        venda.setDataVenda(rs.getDate("DATA_Venda"));
        return venda;
    }
}
