package org.example.delta_pdv.entities;

import java.util.Objects;

public class Produto {

    private Long id;
    private String nome;
    private String preco;
    private String caminhoImagem;

    public Produto () {

    }
    public Produto(String nome, String preco, String caminhoImagem) {
        this.nome = nome;
        this.preco = preco;
        this.caminhoImagem = caminhoImagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco='" + preco + '\'' +
                ", caminhoImagem='" + caminhoImagem + '\'' +
                '}';
    }
}
