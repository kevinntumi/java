/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public class Hospede {
    private long id;
    private String numDocIdentidade;
    private String nacionalidade;
    private String nome;
    private String morada;
    private long dataNascimento;
    private long dataRegisto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumDocIdentidade() {
        return numDocIdentidade;
    }

    public void setNumDocIdentidade(String numDocIdentidade) {
        this.numDocIdentidade = numDocIdentidade;
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

    public long getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }   
}