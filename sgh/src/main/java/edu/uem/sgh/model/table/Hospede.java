/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public class Hospede {
    private SimpleLongProperty codigo;
    private SimpleStringProperty nome;
    private SimpleStringProperty dataNascimento;
    private SimpleStringProperty dataRegisto;
    private SimpleStringProperty numBilheteIdentidade;
    private SimpleStringProperty morada;

    public Hospede(Long codigo, String nome, String dataNascimento, String dataRegisto, String numBilheteIdentidade, String morada) {
        this.codigo = new SimpleLongProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.dataNascimento = new SimpleStringProperty(dataNascimento);
        this.dataRegisto = new SimpleStringProperty(dataRegisto);
        this.numBilheteIdentidade = new SimpleStringProperty(numBilheteIdentidade);
        this.morada = new SimpleStringProperty(morada);
    }

    public Long getCodigo() {
        return codigo.get();
    }

    public void setCodigo(Long codigo) {
        this.codigo.set(codigo);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getDataNascimento() {
        return dataNascimento.get();
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento.set(dataNascimento);
    }

    public String getDataRegisto() {
        return dataRegisto.get();
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto.set(dataRegisto);
    }

    public String getNumBilheteIdentidade() {
        return numBilheteIdentidade.get();
    }

    public void setNumBilheteIdentidade(String numBilheteIdentidade) {
        this.numBilheteIdentidade.set(numBilheteIdentidade);
    }

    public String getMorada() {
        return morada.get();
    }

    public void setMorada(String morada) {
        this.morada.set(morada);
    }
}