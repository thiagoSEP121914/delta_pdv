package org.example.delta_pdv.repository.Dao.factory;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.ItemVendaDao;
import org.example.delta_pdv.repository.Dao.ProdutoDao;
import org.example.delta_pdv.repository.Dao.VendaDao;
import org.example.delta_pdv.repository.Dao.impl.*;

public class DaoFactory {

    public static ProdutoDao createProdutoDao() {
        return new ProdutoDaoImpl(DB.getConn());

    }

    public static GenericDao<Categoria> createCategoriaDao() {
        return new CategoriaDaoImpl(DB.getConn());
    }

    public static VendaDao createVendaDao() {
        return new VendaDaoImpl(DB.getConn());
    }

    public static ItemVendaDao createItemvendaDao() {
        return new ItemVendaDaoImpl(DB.getConn());
    }
}
