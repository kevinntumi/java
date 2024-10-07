/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public class Usuario {
    private Long id;
    private Long idTipo;
    private String nome;
    private Tipo tipo;
    private Long dataInicio;
    private Long dataRegisto;
    private Long dataAlterado;
    
    public static Usuario VAZIO = new Usuario();
    
    public Usuario() {
    }

    public Usuario(Long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public long getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public long getDataAlterado() {
        return dataAlterado;
    }

    public void setDataAlterado(long dataAlterado) {
        this.dataAlterado = dataAlterado;
    }
    
    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public enum Tipo {
        CLIENTE,
        FUNCIONARIO,
        GERENTE,
        ADMINISTRADOR
    }
}