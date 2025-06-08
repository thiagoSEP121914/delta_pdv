package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.exceptions.ProdutoException
;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.ProdutoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDaoImpl implements ProdutoDao {

    private final Connection conn;

    public ProdutoDaoImpl (Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT produtos.*, C.nome AS Categoria, C.id_categoria " +
                "FROM produtos " +
                "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria " +
                "WHERE produtos.ativo = true";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            List<Produto> listOfprodutos = instantiateListOfProduto(rs);
            return listOfprodutos;
        }catch (SQLException exception) {
            throw new ProdutoException("Erro ao executar consulta: ", exception);
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Produto findById(Long id) {
        String sql = "SELECT produtos.*, C.id_categoria, C.nome AS Categoria " +
                "FROM produtos " +
                "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria " +
                "WHERE ID_Produto = ? AND produtos.ativo = true";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1,id);
            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateProduto(rs);
        }catch (SQLException exception) {
            throw new ProdutoException
("Erro ao executar consulta sql: ",exception);
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Produto findByIdIncludeInative(Long id) {

        String sql = "SELECT produtos.*, C.id_categoria, C.nome AS Categoria " +
                "FROM produtos " +
                "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria " +
                "WHERE ID_Produto = ?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateProduto(rs);
        } catch (SQLException exception) {
            throw new ProdutoException
("Erro ao executar consulta sql: ", exception);
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Produto> findByName(String name) {
        String sql =  "SELECT produtos.*, C.id_categoria, C.nome as Categoria " +
                "FROM produtos " +
                "LEFT JOIN categorias C ON produtos.id_categoria = C.id_categoria " +
                "WHERE LOWER(produtos.nome) LIKE ? AND produtos.ativo = true";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                produtos.add(instantiateProduto(rs));
            }
        } catch (SQLException e) {
            throw new ProdutoException
("Erro na tabela", e);
        }
        return produtos;
    }

    public List<Produto> findByCategoria(String nomeCategoria) {
        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT p.*, c.nome AS Categoria, c.id_categoria " +
                "FROM produtos p " +
                "JOIN categorias c ON p.id_categoria = c.id_categoria " +
                "WHERE LOWER(c.nome) = LOWER(?) AND p.Quantidade_Estoque > 0 AND p.ativo = TRUE";

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, nomeCategoria);
            rs = pst.executeQuery();

            while (rs.next()) {
                Produto produto = instantiateProduto(rs);
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new ProdutoException
("Erro ao buscar produtos por categoria: ", e);
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }

        return produtos;
    }

    @Override
    public Map<String, Integer> getProdutosMaisVendidos() {
        String sql = "SELECT p.Nome, SUM(iv.Quantidade) AS Total " +
                "FROM itemVenda iv " +
                "JOIN produtos p ON iv.ID_Produto = p.ID_Produto " +
                "GROUP BY p.Nome " +
                "ORDER BY Total DESC " +
                "LIMIT 5";

        PreparedStatement pst = null;
        ResultSet rs = null;

        Map<String, Integer> produtosMaisVendidos = new HashMap<>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("Nome");
                int quantidade = rs.getInt("Total");
                produtosMaisVendidos.put(nome, quantidade);
            }
            return produtosMaisVendidos;
        } catch (SQLException exception) {
            throw new ProdutoException
("Erro na tabela de Produtos: Não foi possível buscar produtos mais vendidos: ", exception);
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }


    @Override
    public int getTotaoEstoque() {
        String sql = "SELECT SUM(Quantidade_Estoque) FROM produtos WHERE ativo = TRUE"; //nossas tabelas são plurais
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException exception) {
            throw new ProdutoException
("Erro na tabela produtos: Não foi possivel buscar a quantidade total de produtos em estoque", exception);
        }
    }


    @Override
    public void insert(Produto produto) {
        String sql = "INSERT INTO produtos (Nome, caminho_imagem, Descricao ,Preco_Unitario, custo, Quantidade_Estoque, id_categoria) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = null;
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
            throw new ProdutoException
("Erro ao executar a consulta sql: ", exception);
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(Produto produto) {
        String sql = "UPDATE produtos "+
                      "SET nome = ?, caminho_imagem = ?,  Descricao = ?,  Preco_Unitario = ?, custo = ?,  Quantidade_Estoque = ?, id_categoria = ? " +
                      "WHERE ID_Produto = ?;";

        PreparedStatement pst = null;
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
            throw new ProdutoException
("Erro ao execultar consulta", exception);
        } finally {
            DB.closeStatement(pst);
        }
    }


    @Override
    public void delete(Long id) {
        String sql = "UPDATE produtos SET ativo = false WHERE ID_Produto = ?";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setLong(1, id);
            int rows = pst.executeUpdate();
        } catch (SQLException exception) {
            throw new ProdutoException
("Erro ao executar consulta SQL: ", exception);
        } finally {
            DB.closeStatement(pst);
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
        produto.calcularLucro();
        produto.setQuantidadeEstoque(rs.getInt("Quantidade_Estoque"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getLong("id_categoria"));
        categoria.setNome(rs.getString("Categoria"));
        produto.setCategoria(categoria);
        return produto;
    }
}
