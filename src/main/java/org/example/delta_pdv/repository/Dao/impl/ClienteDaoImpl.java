package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.GenericDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteDaoImpl implements GenericDao<Cliente> {

    private Connection conn;

    public ClienteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clientes;";
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            List<Cliente> listaCliente = instanciateListCliente(rs);
            return listaCliente;
        }catch(SQLException e){
            throw new RuntimeException("Erro SQL na tabela clientes: " + e.getMessage());
        }finally{
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Cliente findById(Long id) {
        String sql = "SELECT * FROM clientes " + //ddl
                     "WHERE ID_Cliente = ?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Cliente cliente;
        try{
            pst = conn.prepareStatement(sql);
            pst.setLong(1,id);
            rs = pst.executeQuery();

            if(rs.next()) {
                cliente = instanciateCliente(rs);
            } else {
                throw new RuntimeException("Cliente não encontrado com o id: " + id);
            }
            return cliente;
        }catch(SQLException e){
            throw new RuntimeException("Erro SQL na tabela clientes: " + e.getMessage());
        }finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> findByName(String name) {
        String sql = "SELECT * FROM clientes " +
                     " WHERE Nome = ?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();

            List<Cliente> listaCliente = instanciateListCliente(rs);
            return listaCliente;
        } catch (SQLException e) {
            throw new RuntimeException("Erro SQL na tabela cleintes: " + e.getMessage());
        }finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void insert(Cliente cliente) {
        String sql = "INSERT INTO clientes(Nome, Cpf, Telefone, Email, Data_Criacao, Data_Atualizacao) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getCpf());
            pst.setString(3, cliente.getTelefone());
            pst.setString(4, cliente.getEmail());
            Date dataAgora = new Date(); // caso cliente.getDataCriacao() esteja nulo
            pst.setDate(5, new java.sql.Date(
                    cliente.getDataCriacao() != null ? cliente.getDataCriacao().getTime() : dataAgora.getTime()));
            pst.setDate(6, new java.sql.Date(
                    cliente.getDataAtualizacao() != null ? cliente.getDataAtualizacao().getTime() : dataAgora.getTime()));

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Falha ao inserir cliente! Nenhum cliente inserido.");
            }

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                long idGerado = rs.getLong(1);
                cliente.setIdCliente(idGerado); // opcional, se quiser usar depois
            }

        } catch(SQLException e) {
            throw new RuntimeException("Erro SQL na tabela clientes: " + e.getMessage());
        } finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }


    //Deu um B.O na hora de salvar a edição de um cliente no ClienteCadastroController porque não existem os campos das
    //datas na tela, então quando o update é chamado ele não tem como passar esses parâmetros.


    @Override
    public void update(Cliente cliente) {
        String sql = "UPDATE clientes "+
                     "SET Nome = ?,  Cpf = ?, Telefone = ?, Email = ?, Data_Atualizacao = ? "+
                     "WHERE ID_Cliente = ?";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getCpf());
            pst.setString(3, cliente.getTelefone());
            pst.setString(4, cliente.getEmail());
            pst.setDate(5, new java.sql.Date(System.currentTimeMillis()));

            pst.setLong(6, cliente.getIdCliente());

            int affectedRows = pst.executeUpdate();

            if(affectedRows == 0){
                throw new RuntimeException("Falha ao atualizar clientes! Cliente não atualizado.");
            }

        } catch(SQLException e) {
            throw new RuntimeException("Erro SQL na tabela clientes: " + e.getMessage());
        }finally{
            DB.closeStatement(pst);
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE from clientes " +
                     "WHERE ID_Cliente = ?";
        PreparedStatement pst = null;
        try{
            pst = conn.prepareStatement(sql);
            pst.setLong(1,id);
            int rows = pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro na remoção do cliente: " + e.getMessage());
        }finally {
            DB.closeStatement(pst);
        }

    }

    public Cliente instanciateCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getLong("ID_Cliente"));
        cliente.setNome(rs.getString("Nome"));
        cliente.setCpf(rs.getString("Cpf"));
        cliente.setTelefone(rs.getString("Telefone"));
        cliente.setEmail(rs.getString("Email"));
        cliente.setDataCriacao(rs.getDate("Data_Criacao"));
        cliente.setDataAtualizacao(rs.getDate("Data_Atualizacao"));

        return cliente;
    }

    public List<Cliente> instanciateListCliente(ResultSet rs) throws SQLException{
        List<Cliente> listaCliente = new ArrayList<>();

        while(rs.next()){
            listaCliente.add(instanciateCliente(rs));
        }
        return listaCliente;
    }
}
