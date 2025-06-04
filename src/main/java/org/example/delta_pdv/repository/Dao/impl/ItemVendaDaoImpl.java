package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.ItemVenda;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.ItemVendaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDaoImpl implements ItemVendaDao {

    private Connection conn;

    public ItemVendaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<ItemVenda> findAll() {
        String sql = "SELECT ID_ItemVenda, ID_Venda, ID_Produto, Quantidade, Preco_Unitario " +
                      "FROM itemvenda";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            List<ItemVenda> listOfItemVendas = instantiateListOfItemVenda(rs);
            return listOfItemVendas;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro sql na Tabela de ItemVenda: erro: " + exception.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public ItemVenda findById(long id) {
        String sql = "SELECT  ID_ItemVenda, ID_Venda, ID_Produto, Quantidade, Preco_Unitario " +
                    "FROM ItemVenda " +
                    "WHERE ID_ItemVenda = ? ";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateItemVenda(rs);
        } catch (SQLException exception) {
            throw new RuntimeException("Erro sql na tabela de ItemVenda: Erro " + exception.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public long insert(ItemVenda itemVenda) {
        String sql = "INSERT INTO ItemVenda(ID_Venda, ID_Produto, Quantidade, Preco_Unitario) "+
                     "VALUES (?, ?, ?, ?)";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setLong(1, itemVenda.getVenda().getIdVenda());
            pst.setLong(2, itemVenda.getProduto().getIdProduto());
            pst.setLong(3, itemVenda.getQtd());
            pst.setDouble(4, itemVenda.getPrecoUnitario());
            int affectedrRows = pst.executeUpdate();

            if (affectedrRows == 0) {
                throw new RuntimeException("Falha ao inserir vendas. Nenhuma linha afetada!!!");
            }
            rs = pst.getGeneratedKeys();

            if (!rs.next()) {
                throw new RuntimeException("Falha ao inserir na tabela");
            }
            long idGerado = rs.getLong(1);
            return idGerado;
        } catch (SQLException exception) {
            throw new RuntimeException("Erro sql: Erro ao inserir na tabela ItemVenda" + exception.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }


    private List<ItemVenda> instantiateListOfItemVenda(ResultSet rs) throws SQLException {
        List<ItemVenda> listOfItemVenda = new ArrayList<>();
        while (rs.next()) {
            ItemVenda itemVenda = instantiateItemVenda(rs);
            listOfItemVenda.add(itemVenda);
        }
        return listOfItemVenda;
    }

    private ItemVenda instantiateItemVenda(ResultSet rs) throws SQLException {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setidItemVenda(rs.getLong("ID_ItemVenda"));

        Venda venda = new Venda();
        venda.setIdVenda(rs.getLong("ID_Venda"));
        Produto produto = new Produto();
        produto.setIdProduto(rs.getLong("ID_Produto"));

        itemVenda.setVenda(venda);
        itemVenda.setProduto(produto);
        itemVenda.setQtd(rs.getInt("Quantidade"));
        itemVenda.setPrecoUnitario(rs.getDouble ("Preco_Unitario"));
        return itemVenda;
    }
}
