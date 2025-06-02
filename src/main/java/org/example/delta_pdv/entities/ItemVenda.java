package org.example.delta_pdv.entities;

import java.io.Serializable;
import java.util.Objects;

public class ItemVenda implements Serializable {

    private Long idItemVenda;
    private Venda venda;
    private Produto produto;
    private int qtd;
    private double precoUnitario;

    public ItemVenda () {
        
    }
    
    public ItemVenda(Long idItemVenda, Produto produto, int qtd, double precoUnitario) {
        this.idItemVenda = idItemVenda;
        this.produto = produto;
        this.qtd = qtd;
        this.precoUnitario = precoUnitario;
    }


    public double getTotal() {
        return qtd * precoUnitario;
    }

    public Long getidItemVenda() {
        return idItemVenda;
    }

    public void setidItemVenda(Long idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Objects.equals(idItemVenda, itemVenda.idItemVenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItemVenda);
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "idItemVenda=" + idItemVenda +
                ", venda=" + venda +
                ", produto=" + produto +
                ", qtd=" + qtd +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
