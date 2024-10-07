/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.ReservaSituacao;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class Reserva {
    private long id;
    private Hospede cliente;
    private long dataCheckIn;
    private long dataCheckOut;
    private Long dataConfirmada;
    private long dataReserva;
    private Pagamento pagamento;
    private Funcionario funcionario;
    private List<HospedeQuarto> hospedes;
    private ReservaSituacao situacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hospede getCliente() {
        return cliente;
    }

    public void setCliente(Hospede cliente) {
        this.cliente = cliente;
    }

    public long getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(long dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public long getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(long dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public Long getDataConfirmada() {
        return dataConfirmada;
    }

    public void setDataConfirmada(Long dataConfirmada) {
        this.dataConfirmada = dataConfirmada;
    }

    public long getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(long dataReserva) {
        this.dataReserva = dataReserva;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<HospedeQuarto> getHospedes() {
        return hospedes;
    }

    public void setHospedes(List<HospedeQuarto> hospedes) {
        this.hospedes = hospedes;
    }

    public ReservaSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(ReservaSituacao situacao) {
        this.situacao = situacao;
    }
}