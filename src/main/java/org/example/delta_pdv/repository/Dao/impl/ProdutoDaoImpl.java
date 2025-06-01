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

public class ProdutoDaoImpl implements GenericDao<Produto> {

    private final Connection conn;

    public ProdutoDaoImpl (Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT produtos.*, C.nome AS Categoria, C.id_categoria " +
                     "FROM produtos " +
                     "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            List<Produto> listOfprodutos = instantiateListOfProduto(rs);
            return listOfprodutos;
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao executar consulta: ", exception);
        }
    }

    @Override
    public Produto findById(Long id) {
        String sql = "SELECT produtos.*, C.id_categoria, C.nome AS Categoria " +
                     "FROM produtos " +
                     "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria "+
                     "WHERE ID_Produto = ?;";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1,id);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateProduto(rs);
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao executar consulta sql: ",exception);
        }
    }

    @Override
    public List<Produto> findByName(String name) {
        String sql = "SELECT produtos.*, C.id_categoria, C.nome as Categoria " +
                "FROM produtos " +
                "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria " +
                "WHERE LOWER (produtos.nome) LIKE ?";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                produtos.add(instantiateProduto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos pelo nome: " + e.getMessage(), e);
        }
        return produtos;
    }

    @Override
    public void insert(Produto produto) {
        String sql = "INSERT INTO produtos (Nome, caminho_imagem, Descricao ,Preco_Unitario, custo, Quantidade_Estoque, id_categoria) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getCaminhoImagem());
            pst.setString(3, produto.getDescricao());
            pst.setDouble(4, produto.getPrecoUnitario());
            pst.setDouble(5, produto.getCusto());
            pst.setInt(6, produto.getQuantidadeEstoque());
            pst.setLong(7, produto.getCategoria().getIdCategoria());
            pst.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao executar a consulta sql: ", exception);
        }
    }

    @Override
    public void update(Produto produto) {
        String sql = "UPDATE produtos "+
                      "SET nome = ?, caminho_imagem = ?,  Descricao = ?,  Preco_Unitario = ?, custo = ?,  Quantidade_Estoque = ?, id_categoria = ? " +
                      "WHERE ID_Produto = ?;";

        PreparedStatement pst;

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, produto.getNome());
            pst.setString(2, produto.getCaminhoImagem());
            pst.setString(3, produto.getDescricao());
            pst.setDouble(4, produto.getPrecoUnitario());
            pst.setDouble(5, produto.getCusto());
            pst.setInt(6, produto.getQuantidadeEstoque());
            pst.setLong(7, produto.getCategoria().getIdCategoria());
            pst.setLong(8, produto.getIdProduto());
            int rows = pst.executeUpdate();
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao execultar consulta", exception);
        }
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE  FROM produtos "+
                      "WHERE ID_Produto = ?";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            int rows = pst.executeUpdate();
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao execultar consulta sql: ", exception);
        }
    }

    private List<Produto> instantiateListOfProduto(ResultSet rs) throws SQLException {
        List<Produto> listOfProdutos = new ArrayList<>();
            while (rs.next()) {
                Produto produto = instantiateProduto(rs);
                listOfProdutos.add(produto);
            }
            return listOfProdutos;
    }

    private Produto instantiateProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setIdProduto(rs.getLong("ID_Produto"));
        produto.setNome(rs.getString("Nome"));
        produto.setCaminhoImagem(rs.getString("caminho_imagem"));
        produto.setDescricao(rs.getString("Descricao"));
        produto.setPrecoUnitario(rs.getDouble("Preco_Unitario"));
        produto.setCusto(rs.getDouble("custo"));
        produto.setLucro(rs.getDouble("lucro"));
        produto.setQuantidadeEstoque(rs.getInt("Quantidade_Estoque"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getLong("id_categoria"));
        categoria.setNome(rs.getString("Categoria"));
        produto.setCategoria(categoria);
        return produto;
    }
}
