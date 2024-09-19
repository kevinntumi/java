/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.request_body;

/**
 *
 * @author Kevin Ntumi
 */
public class ServicoQuarto {
    private long id;
    private long idServico;
    private long idQuarto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdServico() {
        return idServico;
    }

    public void setIdServico(long idServico) {
        this.idServico = idServico;
    }

    public long getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(long idQuarto) {
        this.idQuarto = idQuarto;
    }
}