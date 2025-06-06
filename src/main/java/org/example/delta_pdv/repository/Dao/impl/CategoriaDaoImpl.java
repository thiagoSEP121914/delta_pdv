package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.CategoriaDao;
import org.example.delta_pdv.repository.Dao.GenericDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl implements CategoriaDao {

    private final Connection conn;

    public CategoriaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> findAll() {
        String sql = "SELECT * FROM categorias WHERE ativo = TRUE";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            return instantiateListOfCategoria(rs);
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao executar consulta", exception);
        }
    }

    @Override
    public Categoria findById(Long id) {
        String sql = "SELECT * FROM ID_categoria = ? AND ativo = TRUE ";

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateCategoria(rs);
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao cadastrar categoria: " + exception.getMessage());
        }

    }

    @Override
    public void insert(Categoria categoria) {
        String sql = "INSERT INTO categorias(nome) VALUES (?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, categoria.getNome());
            pst.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir categoria: " + exception.getMessage());
        }
    }

    @Override
    public void delete(String nome) {
        String sql = "UPDATE categorias SET ativo = FALSE WHERE nome = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nome);
            pst.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao excluir categoria: " + exception.getMessage());
        }
    }


    private List<Categoria> instantiateListOfCategoria(ResultSet rs) throws SQLException {
        List<Categoria> listOfCategoria = new ArrayList<>();
        while (rs.next()) {
            Categoria categoria = instantiateCategoria(rs);
            listOfCategoria.add(categoria);
        }
        return listOfCategoria;
    }

    private Categoria instantiateCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getLong("ID_categoria"));
        categoria.setNome(rs.getString("nome"));
        return categoria;
    }
}
