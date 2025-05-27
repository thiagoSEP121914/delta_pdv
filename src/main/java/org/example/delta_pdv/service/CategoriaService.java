package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.util.List;

public class CategoriaService {

    private GenericDao<Categoria> categoriaRepository = DaoFactory.createCategoriaDao();

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

}
