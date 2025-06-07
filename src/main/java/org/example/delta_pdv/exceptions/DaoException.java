package org.example.delta_pdv.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(String message, Throwable cause) {
        super("Erro no ao acessar o banco" + message, cause);
    }
}
