package org.example.delta_pdv.repository.Dao.factory;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.*;
import org.example.delta_pdv.repository.Dao.impl.*;

public class DaoFactory {

    public static ProdutoDao createProdutoDao() {
        return new ProdutoDaoImpl(DB.getConn());

    }

    public static CategoriaDao createCategoriaDao() {
        return new CategoriaDaoImpl(DB.getConn());
    }

    public static VendaDao createVendaDao() {
        return new VendaDaoImpl(DB.getConn());
    }

    public static ItemVendaDao createItemvendaDao() {
        return new ItemVendaDaoImpl(DB.getConn());
    }
}
