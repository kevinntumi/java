/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

/**
 *
 * @author Kevin Ntumi
 */
public class Servico {
    private long id;
    private String descricao;
    private String dataRegisto;
    private String situacao;
    private String gerente;

    public Servico(long id, String descricao, String dataRegisto, String situacao, String gerente) {
        this.id = id;
        this.descricao = descricao;
        this.dataRegisto = dataRegisto;
        this.situacao = situacao;
        this.gerente = gerente;
    }

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

    public String getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }
}