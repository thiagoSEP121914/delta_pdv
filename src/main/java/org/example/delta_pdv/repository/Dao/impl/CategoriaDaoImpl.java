package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.Dao.GenericDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoImpl implements GenericDao<Categoria> {

    private final Connection conn;

    public CategoriaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> findAll() {
        String sql= "SELECT * FROM categorias";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            List<Categoria> listCategorias = instantiateListOfCategoria(rs);
            return listCategorias;
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao execultar consulta", exception);
        }
    }

    @Override
    public Categoria findById(Long id) {
        return null;
    }

    @Override
    public List<Produto> findByName(String name) {
        return null;
    }

    @Override
    public void insert(Categoria object) {

    }

    @Override
    public void update(Categoria object) {

    }

    @Override
    public void delete(Long id) {

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
