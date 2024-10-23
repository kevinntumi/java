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
    private Long id;
    private Hospede cliente;
    private Long dataCheckIn;
    private Long dataCheckOut;
    private Long dataSituacao;
    private Long dataReserva;
    private Pagamento pagamento;
    private Funcionario funcionario;
    private List<HospedeQuarto> hospedes;
    private ReservaSituacao situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospede getCliente() {
        return cliente;
    }

    public void setCliente(Hospede cliente) {
        this.cliente = cliente;
    }

    public Long getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(Long dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Long getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(Long dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public Long getDataSituacao() {
        return dataSituacao;
    }

    public void setDataSituacao(Long dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    public Long getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Long dataReserva) {
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