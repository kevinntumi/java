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
public class Servico {
    private final SimpleLongProperty codigo;
    private final SimpleStringProperty descricao;
    private final SimpleStringProperty dataRegisto;
    private final SimpleStringProperty situacao;
    private final SimpleStringProperty inseridoPor;

    public Servico(long codigo, String descricao, String dataRegisto, String situacao, String inseridoPor) {
        this.codigo = new SimpleLongProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
        this.dataRegisto = new SimpleStringProperty(dataRegisto);
        this.situacao = new SimpleStringProperty(situacao);
        this.inseridoPor = new SimpleStringProperty(inseridoPor);
    }

    public long getCodigo() {
        return codigo.get();
    }

    public void setCodigo(long codigo) {
        this.codigo.set(codigo);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public String getDataRegisto() {
        return dataRegisto.get();
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto.set(dataRegisto);
    }

    public String getSituacao() {
        return situacao.get();
    }

    public void setSituacao(String situacao) {
        this.situacao.set(situacao);
    }

    public String getInseridoPor() {
        return inseridoPor.get();
    }

    public void setInseridoPor(String inseridoPor) {
        this.inseridoPor.set(inseridoPor);
    }
}