package org.example.delta_pdv.repository.Dao;

import org.example.delta_pdv.entities.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> findAll();
    Usuario findById(Long id);

    Usuario findByEmail(String email);

    List<Usuario> findByName(String name);

    void insert(Usuario usuario);
    void update(Usuario usuario);
    void delete(Long id);
}
