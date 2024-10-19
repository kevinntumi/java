/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Usuario.Tipo;

/**
 *
 * @author Kevin Ntumi
 */
public class Passagem {
    private Long id;
    private FuncionarioSituacao situacao;
    private Long idTipo;
    private Tipo tipo;
    private Long dataInicio;
    private Long dataFim;

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public Tipo getTipo() {
        return tipo;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setSituacao(FuncionarioSituacao situacao) {
        this.situacao = situacao;
    }

    public void setDataInicio(Long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Long dataFim) {
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public FuncionarioSituacao getSituacao() {
        return situacao;
    }

    public Long getDataInicio() {
        return dataInicio;
    }

    public Long getDataFim() {
        return dataFim;
    }
}