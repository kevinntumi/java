/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import edu.uem.sgh.helper.ReservaSituacao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public final class Reserva {
    private SimpleLongProperty codigo;
    private SimpleStringProperty cliente;
    private edu.uem.sgh.model.Hospede clienteReserva;
    private SimpleStringProperty dataReserva;
    private SimpleStringProperty dataCheckIn;
    private SimpleStringProperty dataCheckOut;
    private SimpleDoubleProperty valorPago;
    private SimpleDoubleProperty valorTotal;
    private SimpleStringProperty responsavel;
    private SimpleObjectProperty<ReservaSituacao> situacao;
    private SimpleStringProperty dataSituacao;
    private edu.uem.sgh.model.Funcionario funcionarioReserva;

    public Reserva(Long codigo, edu.uem.sgh.model.Hospede cliente, String dataReserva, String dataCheckIn, String dataCheckOut, Double valorPago, Double valorTotal, edu.uem.sgh.model.Funcionario responsavel, ReservaSituacao situacao, String dataSituacao) {
        this.codigo = new SimpleLongProperty(codigo);
        this.cliente = new SimpleStringProperty();
        this.dataReserva = new SimpleStringProperty(dataReserva);
        this.dataCheckIn = new SimpleStringProperty(dataCheckIn);
        this.dataCheckOut = new SimpleStringProperty(dataCheckOut);
        this.valorPago = new SimpleDoubleProperty(valorPago);
        this.valorTotal = new SimpleDoubleProperty(valorTotal);
        this.responsavel = new SimpleStringProperty();
        this.situacao = new SimpleObjectProperty<>(situacao);
        this.dataSituacao = new SimpleStringProperty(dataSituacao);
        this.setCliente(cliente);
        this.setResponsavel(responsavel);
    }

    public String getDataSituacao() {
        return dataSituacao.get();
    }

    public void setDataSituacao(String dataSituacao) {
        this.dataSituacao.set(dataSituacao);
    }
    
    public ReservaSituacao getSituacao() {
        return situacao.get();
    }

    public void setSituacao(ReservaSituacao situacao) {
        this.situacao.set(situacao);
    }

    public Long getCodigo() {
        return codigo.get();
    }

    public void setCodigo(Long codigo) {
        this.codigo.set(codigo);
    }

    public String getCliente() {
        return cliente.get();
    }

    public void setCliente(edu.uem.sgh.model.Hospede cliente) {
        this.clienteReserva = cliente;
        this.cliente.set(cliente.getNome());
    }

    public String getDataReserva() {
        return dataReserva.get();
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva.set(dataReserva);
    }

    public String getDataCheckIn() {
        return dataCheckIn.get();
    }

    public void setDataCheckIn(String dataCheckIn) {
        this.dataCheckIn.set(dataCheckIn);
    }

    public String getDataCheckOut() {
        return dataCheckOut.get();
    }

    public void setDataCheckOut(String dataCheckOut) {
        this.dataCheckOut.set(dataCheckOut);
    }

    public Double getValorPago() {
        return valorPago.get();
    }

    public void setValorPago(Double valorPago) {
        this.valorPago.set(valorPago);
    }

    public Double getValorTotal() {
        return valorTotal.get();
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal.set(valorTotal);
    }

    public String getResponsavel() {
        return responsavel.get();
    }

    public void setResponsavel(edu.uem.sgh.model.Funcionario responsavel) {
        this.funcionarioReserva = responsavel;
        this.responsavel.set(funcionarioReserva.getNome());
    }
}