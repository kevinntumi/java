/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public class Usuario {
    private long id;
    private long idTipo;
    private String nome;
    private String tipo;
    private long dataRegisto;
    private long dataAlterado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Usuario(long id, long idTipo, String nome, String tipo, long dataRegisto, long dataAlterado) {
        this.id = id;
        this.idTipo = idTipo;
        this.nome = nome;
        this.tipo = tipo;
        this.dataRegisto = dataRegisto;
        this.dataAlterado = dataAlterado;
    }   
}