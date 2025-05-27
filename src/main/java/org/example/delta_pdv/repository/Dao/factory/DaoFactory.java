package org.example.delta_pdv.repository.Dao.factory;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.impl.CategoriaDaoImpl;
import org.example.delta_pdv.repository.Dao.impl.ProdutoDaoImpl;

public class DaoFactory {

    public static GenericDao<Produto> createProdutoDao() {
        return new ProdutoDaoImpl(DB.getConn());
    }

    public static GenericDao<Categoria> createCategoriaDao() {
        return new CategoriaDaoImpl(DB.getConn());
    }
}
