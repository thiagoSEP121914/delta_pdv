package org.example.delta_pdv.repository.Dao.impl;

import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.repository.Dao.GenericDao;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements GenericDao<Usuario> {

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
             throw new RuntimeException("Erro SQL no findAll(): " + e.getMessage());
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
            throw new RuntimeException("Erro SQL no findById: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> findByName(String name) {
        String sql = "SELECT * FROM usuarios WHERE Nome = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1,name);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    return instantiateListUsuario(rs);
                }
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException("Erro SQL no findByName: " + e.getMessage());
        }
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
                throw new RuntimeException("Nenhuma linha afetada ! Usuário não foi inserido.");
            }
            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()){
                    long idGerado = rs.getLong(1);
                    user.setId_usuario(idGerado);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro SQL no insert: " + e.getMessage());
        }

    }

    @Override
    public void update(Usuario user) {
        String sql = "UPDATE usuario SET Nome = ?, Email = ?, Senha = ?, tipo = ?) VALUES(?, ?, ?, ?) " +
                     "WHERE id_usuario = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1,user.getNome());
            st.setString(2,user.getEmail());
            st.setString(3,user.getSenha());
            st.setString(4,user.getTipo());
            st.setLong(5,user.getId_usuario());

            int affectedRows = st.executeUpdate();
            if(affectedRows == 0){
                throw new RuntimeException("Nenhuma lina afetada ! Usuário não foi atualizado.");
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro SQL no update: " + e.getMessage());
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setLong(1,id);
            int affectedRows = st.executeUpdate();
            if(affectedRows == 0){
                throw new RuntimeException("Nenhuma linha afetada ! Usuário não deletado.");
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro SQl no delete: " + e.getMessage());
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
        if(rs.next()){
            user.setId_usuario(rs.getLong("id_usuario"));
            user.setNome(rs.getString("Nome"));
            user.setEmail(rs.getString("Email"));
            user.setSenha(rs.getString("Senha"));
            user.setTipo(rs.getString("tipo"));
        }
        return user;
    }
}
