/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import edu.uem.sgh.model.Gerente;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Kevin Ntumi
 */
public class Funcionario {
    private final SimpleLongProperty codigo;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty dataNascimento;
    private final SimpleStringProperty dataRegisto;
    private final SimpleStringProperty morada;
    private final SimpleStringProperty numBilheteIdentidade;
    private final SimpleIntegerProperty numTelefone;
    private final SimpleStringProperty situacao;
    private final SimpleStringProperty email;
    private final SimpleObjectProperty<Gerente> gerente;

    public Funcionario(Long codigo, String nome, String dataNascimento, String dataRegisto, String morada, String numBilheteIdentidade, Integer numTelefone, String email, Gerente gerente, String situacao) {
        this.codigo = new SimpleLongProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.dataNascimento = new SimpleStringProperty(dataNascimento);
        this.dataRegisto = new SimpleStringProperty(dataRegisto);
        this.morada = new SimpleStringProperty(morada);
        this.numBilheteIdentidade = new SimpleStringProperty(numBilheteIdentidade);
        this.numTelefone = new SimpleIntegerProperty(numTelefone);
        this.email = new SimpleStringProperty(email);
        this.gerente = new SimpleObjectProperty<>(gerente);
        this.situacao = new SimpleStringProperty(situacao);
    }
    
    public void setSituacao(String situacao) {
        this.situacao.set(situacao);
    }
    
    public void setCodigo(Long codigo) {
        this.codigo.set(codigo);
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento.set(dataNascimento);
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto.set(dataRegisto);
    }

    public void setMorada(String morada) {
        this.morada.set(morada);
    }

    public void setNumBilheteIdentidade(String numBilheteIdentidade) {
        this.numBilheteIdentidade.set(numBilheteIdentidade);
    }

    public void setNumTelefone(Integer numTelefone) {
        this.numTelefone.set(numTelefone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setGerente(Gerente gerente) {
        this.gerente.set(gerente);
    }
    
    public String getSituacao() {
        return situacao.get();
    }
    
    public Long getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getDataNascimento() {
        return dataNascimento.get();
    }

    public String getDataRegisto() {
        return dataRegisto.get();
    }

    public String getMorada() {
        return morada.get();
    }

    public String getNumBilheteIdentidade() {
        return numBilheteIdentidade.get();
    }

    public Integer getNumTelefone() {
        return numTelefone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public Gerente getGerente() {
        return gerente.get();
    }
}