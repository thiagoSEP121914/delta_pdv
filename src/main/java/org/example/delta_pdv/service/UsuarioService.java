package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;

public class UsuarioService {

    private GenericDao<Usuario> UsuarioDao = DaoFactory.createUsuarioDao();

    public List<Usuario> findAll(){ return UsuarioDao.findAll();}

    public Usuario findById(Long id){ return UsuarioDao.findById(id);}

    public List<Usuario> findByName(String name){ return UsuarioDao.findByName(name);} //Se pa bugou (nome cinza)

    public void insert(Usuario user){ UsuarioDao.insert(user);}

    public void update(Usuario user){ UsuarioDao.update(user);}

    public void delete(Long id){ UsuarioDao.delete(id);}

}
