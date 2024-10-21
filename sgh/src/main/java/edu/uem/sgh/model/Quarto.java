/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.ServicoSituacao;
import java.sql.Blob;

/**
 *
 * @author Kevin Ntumi
 */
public class Quarto {
    private long id;
    private String descricao;
    private int capacidade;
    private Blob foto;
    private double preco;
    private ServicoSituacao situacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ServicoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(ServicoSituacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Quarto{" + "id=" + id + ", descricao=" + descricao + ", capacidade=" + capacidade + ", foto=" + foto + ", preco=" + preco + ", situacao=" + situacao + '}';
    }
}