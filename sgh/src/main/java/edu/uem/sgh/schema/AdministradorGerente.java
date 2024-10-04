/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public class AdministradorGerente {
    private long idGerente;
    private boolean estado;
    private long dataAlterado;

    public long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(long idGerente) {
        this.idGerente = idGerente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public long getDataAlterado() {
        return dataAlterado;
    }

    public void setDataAlterado(long dataAlterado) {
        this.dataAlterado = dataAlterado;
    }   
}