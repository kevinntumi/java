/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public class Hospede {
    private long id;
    private String numDocumentoIdentidade;
    private String nacionalidade;
    private String nome;
    private String morada;
    private long dataNascimento;
    private long dataRegistrado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumDocumentoIdentidade() {
        return numDocumentoIdentidade;
    }

    public void setNumDocumentoIdentidade(String numDocumentoIdentidade) {
        this.numDocumentoIdentidade = numDocumentoIdentidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public long getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(long dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getDataRegistrado() {
        return dataRegistrado;
    }

    public void setDataRegistrado(long dataRegistrado) {
        this.dataRegistrado = dataRegistrado;
    }
}