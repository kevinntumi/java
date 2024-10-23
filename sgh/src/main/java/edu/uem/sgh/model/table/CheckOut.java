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
    private SimpleLongProperty codigoReserva;
    private SimpleLongProperty codigoCheckIn;
    private SimpleStringProperty cliente;
    private SimpleStringProperty dataReserva;
    private SimpleStringProperty dataCheckIn;
    private SimpleStringProperty dataEsperadaCheckIn;
    private SimpleStringProperty dataEsperadaCheckOut;
    private SimpleStringProperty dataCheckOut;
    private SimpleDoubleProperty valorTotal;
    private SimpleDoubleProperty valorPagoReserva;
    private SimpleDoubleProperty valorPagoCheckOut;
    private SimpleStringProperty responsavel;
    private edu.uem.sgh.model.Funcionario funcionarioResponsavel;
    private edu.uem.sgh.model.Hospede clienteResponsavel;

    public CheckOut() {
        this.codigo = new SimpleLongProperty();
        this.codigoReserva = new SimpleLongProperty();
        this.cliente = new SimpleStringProperty();
        this.dataReserva = new SimpleStringProperty();
        this.dataCheckIn = new SimpleStringProperty();
        this.dataEsperadaCheckOut = new SimpleStringProperty();
        this.dataCheckOut = new SimpleStringProperty();
        this.valorTotal = new SimpleDoubleProperty();
        this.valorPagoCheckOut = new SimpleDoubleProperty();
        this.valorPagoReserva = new SimpleDoubleProperty();
        this.dataEsperadaCheckIn = new SimpleStringProperty();
        this.responsavel = new SimpleStringProperty();
        this.codigoCheckIn = new  SimpleLongProperty();
    }

    public Long getCodigo() {
        return codigo.get();
    }

    public void setCodigo(Long codigo) {
        this.codigo.set(codigo);
    }
    
    public Long getCodigoReserva() {
        return codigoReserva.get();
    }

    public void setCodigoReserva(Long codigoReserva) {
        this.codigoReserva.set(codigoReserva);
    }
    
    public Long getCodigoCheckIn() {
        return codigoCheckIn.get();
    }

    public void setCodigoCheckIn(Long codigoCheckIn) {
        this.codigoCheckIn.set(codigoCheckIn);
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
    
    public String getDataEsperadaCheckIn() {
        return dataEsperadaCheckIn.get();
    }

    public void setDataEsperadaCheckIn(String dataEsperadaCheckIn) {
        this.dataEsperadaCheckIn.set(dataEsperadaCheckIn);
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

    public Double getValorPagoReserva() {
        return valorPagoReserva.get();
    }

    public void setValorPagoReserva(Double valorPagoReserva) {
        this.valorPagoReserva.set(valorPagoReserva);
    }
    
    public Double getValorPagoCheckOut() {
        return valorPagoCheckOut.get();
    }

    public void setValorPagoCheckOut(Double valorPagoCheckOut) {
        this.valorPagoCheckOut.set(valorPagoCheckOut);
    }

    public String getResponsavel() {
        return responsavel.get();
    }

    public void setResponsavel(edu.uem.sgh.model.Funcionario responsavel) {
        this.funcionarioResponsavel = responsavel;
        this.responsavel.set(funcionarioResponsavel.getNome());
    }
}