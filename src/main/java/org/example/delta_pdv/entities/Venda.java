package org.example.delta_pdv.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Venda implements Serializable {

    private Long idVenda;
    private Cliente cliente;
    private Double total;
    private FormaPagamento formaPagamento;
    private Date dataVenda;

    public Venda() {
    }

    public Venda(Long idVenda, Cliente cliente, Double total, FormaPagamento formaPagamento, Date dataVenda) {
        this.idVenda = idVenda;
        this.cliente = cliente;
        this.total = total;
        this.formaPagamento = formaPagamento;
        this.dataVenda = dataVenda;
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(idVenda, venda.idVenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenda);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", cliente=" + cliente +
                ", total=" + total +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", dataVenda=" + dataVenda +
                '}';
    }
}
