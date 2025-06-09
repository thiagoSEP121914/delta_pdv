package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.exceptions.DaoException;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.UsuarioDao;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection conn;

    public UsuarioDaoImpl(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<Usuario> findAll() {
         try(PreparedStatement st = conn.prepareStatement("SELECT * FROM usuarios");
             ResultSet rs = st.executeQuery()){
             return instantiateListUsuario(rs);
         } catch (SQLException e) {
             throw new DaoException("Erro SQL no findAll(): " + e.getMessage());
         }
    }

    @Override
    public Usuario findById(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setLong(1,id);
            try(ResultSet rs = st.executeQuery()) {
                if(rs.next()) {
                    return instantiateUsuario(rs);
                }
            }
            return null;
        } catch (SQLException e){
            throw new DaoException("Erro SQL no findById: ",e);
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        ResultSet rs = null;
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
             rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return instantiateUsuario(rs);
        } catch (SQLException exception) {
            throw new DaoException("Erro ao buscar usuário por email: ", exception);
        } finally {
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Usuario> findByName(String name) {
        return null;
    }


    @Override
    public void insert(Usuario user) {
        String sql = "INSERT INTO usuarios (Nome, Email, Senha, tipo) VALUES(?, ?, ?, ?)";
        try(PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            st.setString(1,user.getNome());
            st.setString(2,user.getEmail());
            st.setString(3,user.getSenha());
            st.setString(4,user.getTipo());

            int affectedRows = st.executeUpdate();
            if(affectedRows == 0){
                throw new DaoException("Nenhuma linha afetada ! Usuário não foi inserido.");
            }
            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()){
                    long idGerado = rs.getLong(1);
                    user.setId_usuario(idGerado);
                }
            }
        } catch(SQLException e){
            throw new DaoException
("Erro SQL no insert: " + e.getMessage());
        }

    }

    @Override
    public void update(Usuario user) {
        String sql = "UPDATE usuarios SET Nome = ?, Email = ?, Senha = ?, tipo = ? WHERE id_usuario = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, user.getNome());
            st.setString(2, user.getEmail());
            st.setString(3, user.getSenha());
            st.setString(4, user.getTipo());
            st.setLong(5, user.getId_usuario());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException
("Nenhuma linha afetada! Usuário não foi atualizado.");
            }
        } catch (SQLException e) {
            throw new DaoException
("Erro SQL no update: " + e.getMessage());
        }
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setLong(1,id);
            int affectedRows = st.executeUpdate();
            if(affectedRows == 0){
                throw new DaoException
("Nenhuma linha afetada ! Usuário não deletado.");
            }
        } catch(SQLException e){
            throw new DaoException
("Erro SQl no delete: " + e.getMessage());
        }
    }

    public List<Usuario> instantiateListUsuario(ResultSet rs) throws SQLException{
        List<Usuario> listaUsuario = new ArrayList<>();

        while(rs.next()){
            listaUsuario.add(instantiateUsuario(rs));
        }
        return listaUsuario;
    }

    public Usuario instantiateUsuario(ResultSet rs) throws SQLException{
        Usuario user = new Usuario();
        user.setId_usuario(rs.getLong("id_usuario"));
        user.setNome(rs.getString("Nome"));
        user.setEmail(rs.getString("Email"));
        user.setSenha(rs.getString("Senha"));
        user.setTipo(rs.getString("tipo"));
        return user;
    }
}
