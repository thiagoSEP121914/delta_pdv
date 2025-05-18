package org.example.delta_pdv.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProdutoExemplo {

    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty nome = new SimpleStringProperty();

    public ProdutoExemplo(int id, String nome) {
        this.id.set(id);
        this.nome.set(nome);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }
}
