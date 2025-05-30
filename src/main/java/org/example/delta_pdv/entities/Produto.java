package org.example.delta_pdv.entities;

import java.io.Serializable;
import java.util.Objects;

public class Produto implements Serializable {

    private Long idProduto;
    private String nome;
    private String caminhoImagem;
    private String descricao;
    private Double precoUnitario;
    private Double custo;
    private Double lucro;
    private Integer quantidadeEstoque;
    private Categoria categoria;

    public Produto () {

    }

    public Produto(Long idProduto, String nome, String caminhoImagem, String descricao, Double precoUnitario, Double custo, Double lucro, Integer quantidadeEstoque, Categoria categoria) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.caminhoImagem = caminhoImagem;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.custo = custo;
        this.lucro = lucro;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    public Produto(String nome, String caminhoImagem, String descricao, Double precoUnitario, Double custo, Integer quantidadeEstoque, Categoria categoria) {
        this.nome = nome;
        this.caminhoImagem = caminhoImagem;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.custo = custo;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    public Produto(Double precoUnitario, Double custo) {
        this.precoUnitario = precoUnitario;
        this.custo = custo;
    }
    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public Double getLucro() {
        return precoUnitario - custo;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }


    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setCategoria(Categoria categoria) {
     this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(idProduto, produto.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", caminhoImagem='" + caminhoImagem + '\'' +
                ", descricao='" + descricao + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}
