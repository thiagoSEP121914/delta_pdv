package org.example.delta_pdv.exceptions;

public class DaoException extends RuntimeException {

    public DaoException() {

    }

    public DaoException(String message, Throwable cause) {
        super("ERRO AO ACESSAR O BACNO: " + message, cause);
    }

    public DaoException(String message) {
        super(message);
    }
}
