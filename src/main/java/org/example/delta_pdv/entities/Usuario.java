package org.example.delta_pdv.entities;

import java.util.Objects;

public class Usuario {

    private Long Id_usuario;
    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public Usuario() {
    }

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario(Long id_usuario, String nome, String email, String senha, String tipo) {
        Id_usuario = id_usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Long getId_usuario() {
        return Id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId_usuario(Long id_usuario) {
        Id_usuario = id_usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(getId_usuario(), usuario.getId_usuario());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId_usuario());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id_usuario=" + Id_usuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
