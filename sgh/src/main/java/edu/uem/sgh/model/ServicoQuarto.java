/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.Situacao;

/**
 *
 * @author Kevin Ntumi
 */
public class ServicoQuarto {
    private long id;
    private Servico servico;
    private Quarto quarto;
    private long dataAssociacao;
    private long dataSituacao;
    private Situacao.ServicoQuarto situacao;
    
    public ServicoQuarto(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public long getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(long dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    public long getDataSituacao() {
        return dataSituacao;
    }

    public void setDataSituacao(long dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    public Situacao.ServicoQuarto getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao.ServicoQuarto situacao) {
        this.situacao = situacao;
    }
}