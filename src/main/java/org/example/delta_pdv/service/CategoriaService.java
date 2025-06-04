package org.example.delta_pdv.service;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.repository.Dao.CategoriaDao;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.factory.DaoFactory;

import java.security.PrivateKey;
import java.util.List;

public class CategoriaService {

    // Usamos a DaoFactory para desacoplar o Service da implementação concreta do DAO.
    // Assim, o service só conhece a interface GenericDao e não fica dependente de CategoriaDaoImpl.
    // Se der algum problema na Dao ou mudarmos a sua implementaçao isso não quebrará a classe service

    private CategoriaDao categoriaRepository = DaoFactory.createCategoriaDao();
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public void insert(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoriaRepository.insert(categoria);
    }

    public void delete(String nome) {
        categoriaRepository.delete(nome);
    }
}
