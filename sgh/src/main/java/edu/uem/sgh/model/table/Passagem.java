/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Usuario;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public class Passagem {
    private SimpleLongProperty id;
    private SimpleObjectProperty<FuncionarioSituacao> situacao;
    private SimpleLongProperty idTipo;
    private SimpleObjectProperty<Usuario.Tipo> tipo;
    private SimpleStringProperty dataInicio;
    private SimpleStringProperty dataFim;
    
    public Passagem(Long id, FuncionarioSituacao situacao, Long idTipo, Usuario.Tipo tipo, String dataInicio, String dataFim) {
        this.id = new SimpleLongProperty(id);
        this.situacao = new SimpleObjectProperty<>(situacao);
        this.idTipo = new SimpleLongProperty(idTipo);
        this.tipo = new SimpleObjectProperty<>(tipo);
        this.dataInicio = new SimpleStringProperty(dataInicio);
        this.dataFim = new SimpleStringProperty(dataFim);
    }
    
    
    public void setIdTipo(Long idTipo) {
        this.idTipo.set(idTipo);
    }

    public void setTipo(Usuario.Tipo tipo) {
        this.tipo.set(tipo);
    }

    public Long getIdTipo() {
        return idTipo.get();
    }

    public Usuario.Tipo getTipo() {
        return tipo.get();
    }
    
    public void setId(Long id) {
        this.id.set(id);
    }

    public void setSituacao(FuncionarioSituacao situacao) {
        this.situacao.set(situacao);
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio.set(dataInicio);
    }

    public void setDataFim(String dataFim) {
        this.dataFim.set(dataFim);
    }

    public Long getId() {
        return id.get();
    }

    public FuncionarioSituacao getSituacao() {
        return situacao.get();
    }

    public String getDataInicio() {
        return dataInicio.get();
    }

    public String getDataFim() {
        return dataFim.get();
    }
}