/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import edu.uem.sgh.helper.Situacao;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public final class ServicoQuarto {
    private final SimpleLongProperty codigo;
    private final SimpleLongProperty codigoQuarto;
    private final SimpleStringProperty descricaoQuarto;
    private final SimpleStringProperty dataAssociacao;
    private final SimpleObjectProperty<Situacao.ServicoQuarto> situacao;
    private edu.uem.sgh.model.ServicoQuarto servicoQuarto;
    private final SimpleStringProperty dataSituacao;

    public ServicoQuarto(Long codigo, Long codigoQuarto, String descricaoQuarto, String dataAssociacao, Situacao.ServicoQuarto situacao, String dataSituacao, edu.uem.sgh.model.ServicoQuarto servicoQuarto) {
        this.codigo = new SimpleLongProperty(codigo);
        this.codigoQuarto = new SimpleLongProperty(codigoQuarto);
        this.descricaoQuarto = new SimpleStringProperty(descricaoQuarto);
        this.dataAssociacao = new SimpleStringProperty(dataAssociacao);
        this.situacao = new SimpleObjectProperty<>(situacao);
        this.dataSituacao = new SimpleStringProperty(dataSituacao);
        this.setServicoQuarto(servicoQuarto);
    }

    public edu.uem.sgh.model.ServicoQuarto getServicoQuarto() {
        return servicoQuarto;
    }

    public void setServicoQuarto(edu.uem.sgh.model.ServicoQuarto servicoQuarto) {
        this.servicoQuarto = servicoQuarto;
    }

    public String getDataAssociacao() {
        return dataAssociacao.get();
    }

    public void setDataAssociacao(String newValue) {
        dataAssociacao.set(newValue);
    }

    public String getDataSituacao() {
        return dataSituacao.get();
    }

    public void setDataSituacao(String newValue) {
        dataSituacao.set(newValue);
    }

    public Situacao.ServicoQuarto getSituacao() {
        return situacao.get();
    }

    public void setSituacao(Situacao.ServicoQuarto newValue) {
        situacao.set(newValue);
    }

    public long getCodigo() {
        return codigo.get();
    }

    public void setCodigo(long codigo) {
        this.codigo.set(codigo);
    }
    
    public long getCodigoQuarto() {
        return codigoQuarto.get();
    }

    public void setCodigoQuarto(long codigoQuarto) {
        this.codigoQuarto.set(codigoQuarto);
    }

    public String getDescricao() {
        return descricaoQuarto.get();
    }

    public void setDescricao(String descricao) {
        this.descricaoQuarto.set(descricao);
    }
}