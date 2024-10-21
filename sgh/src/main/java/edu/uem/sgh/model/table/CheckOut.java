/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public class CheckOut {
    private SimpleLongProperty codigo;
    private SimpleStringProperty cliente;
    private SimpleStringProperty dataReserva;
    private SimpleStringProperty dataCheckIn;
    private SimpleStringProperty dataEsperadaCheckOut;
    private SimpleStringProperty dataCheckOut;
    private SimpleDoubleProperty valorTotal;
    private SimpleDoubleProperty valorPago;
    private SimpleStringProperty responsavel;
    private edu.uem.sgh.model.Funcionario funcionarioResponsavel;
    private edu.uem.sgh.model.Hospede clienteResponsavel;

    public CheckOut(Long codigo, edu.uem.sgh.model.Hospede cliente, String dataReserva, String dataCheckIn, String dataEsperadaCheckOut, String dataCheckOut, Double valorTotal, Double valorPago, edu.uem.sgh.model.Funcionario responsavel) {
        this.codigo = new SimpleLongProperty(codigo);
        this.clienteResponsavel = cliente;
        this.cliente = new SimpleStringProperty(clienteResponsavel.getNome());
        this.dataReserva = new SimpleStringProperty(dataReserva);
        this.dataCheckIn = new SimpleStringProperty(dataCheckIn);
        this.dataEsperadaCheckOut = new SimpleStringProperty(dataEsperadaCheckOut);
        this.dataCheckOut = new SimpleStringProperty(dataCheckOut);
        this.valorTotal = new SimpleDoubleProperty(valorTotal);
        this.valorPago = new SimpleDoubleProperty(valorPago);
        this.funcionarioResponsavel = responsavel;
        this.responsavel = new SimpleStringProperty(funcionarioResponsavel.getNome());
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
        this.clienteResponsavel = cliente;
        this.cliente.set(clienteResponsavel.getNome());
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

    public String getDataEsperadaCheckOut() {
        return dataEsperadaCheckOut.get();
    }

    public void setDataEsperadaCheckOut(String dataEsperadaCheckOut) {
        this.dataEsperadaCheckOut.set(dataEsperadaCheckOut);
    }

    public String getDataCheckOut() {
        return dataCheckOut.get();
    }

    public void setDataCheckOut(String dataCheckOut) {
        this.dataCheckOut.set(dataCheckOut);
    }

    public Double getValorTotal() {
        return valorTotal.get();
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal.set(valorTotal);
    }

    public Double getValorPago() {
        return valorPago.get();
    }

    public void setValorPago(Double valorPago) {
        this.valorPago.set(valorPago);
    }

    public String getResponsavel() {
        return responsavel.get();
    }

    public void setResponsavel(edu.uem.sgh.model.Funcionario responsavel) {
        this.funcionarioResponsavel = responsavel;
        this.responsavel.set(funcionarioResponsavel.getNome());
    }
}