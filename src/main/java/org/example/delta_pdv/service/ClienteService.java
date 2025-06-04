package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;

public class ClienteService {

    private GenericDao<Cliente> ClienteDao = DaoFactory.createClienteDao();

    public List<Cliente> findAll(){
        return ClienteDao.findAll();
    }

    public Cliente findById(long id){
        return ClienteDao.findById(id);
    }

    public List<Cliente> findByName(String nome){
        return ClienteDao.findByName(nome);
    }

    public void insert(Cliente cliente){
        ClienteDao.insert(cliente);
    }

    public void update(Cliente cliente){
        ClienteDao.update(cliente);
    }

    public void delete(long id){
        ClienteDao.delete(id);
    }
}
