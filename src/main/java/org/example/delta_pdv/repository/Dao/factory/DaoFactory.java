package org.example.delta_pdv.repository.Dao.factory;

import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.repository.DB;
import org.example.delta_pdv.repository.Dao.GenericDao;
import org.example.delta_pdv.repository.Dao.ItemVendaDao;
import org.example.delta_pdv.repository.Dao.VendaDao;
import org.example.delta_pdv.repository.Dao.impl.*;

public class DaoFactory {

    public static GenericDao<Produto> createProdutoDao() {
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

    public static GenericDao<Cliente> createClienteDao(){ return new ClienteDaoImpl(DB.getConn());
    }
}
