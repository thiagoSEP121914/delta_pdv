package org.example.delta_pdv.exceptions;

import org.example.delta_pdv.entities.Produto;

public class ProdutoException extends DaoException {

    public ProdutoException(String msg) {
        super(msg);
    }

    public ProdutoException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
