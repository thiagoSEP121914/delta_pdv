package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.UsuarioDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;

public class UsuarioService {

    private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

    public List<Usuario> findAll(){ return usuarioDao.findAll();}

    public Usuario findById(Long id){
        if (id == null || id <= 0) return null;
        return usuarioDao.findById(id);
    }

    public List<Usuario> findByName(String name){
        return usuarioDao.findByName(name);
    } //Se pa bugou (nome cinza)

    public void insert(Usuario user){ usuarioDao.insert(user);}

    public void update(Usuario user){usuarioDao.update(user);}

    public void saveUsuario(Usuario novousuario) {
        Usuario usuarioExistente = findById(novousuario.getId_usuario());
        if (usuarioExistente != null) {
            this.update(novousuario);
            return;
        }
        this.insert(novousuario);
    }


    public Usuario validarLogin(String email, String senha){
        Usuario usuario = usuarioDao.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    public void delete(Long id){ usuarioDao.delete(id);}

}
