/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.ServicoSituacao;

/**
 *
 * @author Kevin Ntumi
 */
public class Servico {

    public Servico() {
    }
    private long id;
    private String descricao;
    private long dataRegisto;
    private ServicoSituacao situacao;
    private Gerente gerente;

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

    public long getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public ServicoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(ServicoSituacao situacao) {
        this.situacao = situacao;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }   

    @Override
    public String toString() {
        return "Servico{" + "id=" + id + ", descricao=" + descricao + ", dataRegisto=" + dataRegisto + ", situacao=" + situacao + ", gerente=" + gerente + '}';
    }
}