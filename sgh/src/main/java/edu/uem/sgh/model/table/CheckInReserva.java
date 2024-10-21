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
public final class CheckInReserva {
    private final SimpleLongProperty codigo;
    private final SimpleLongProperty codigoReserva;
    private final SimpleStringProperty cliente;
    private final SimpleStringProperty dataReserva;
    private final SimpleStringProperty dataCheckIn;
    private final SimpleStringProperty dataEsperadaCheckOut;
    private SimpleStringProperty dataEsperadaCheckIn;
    private final SimpleDoubleProperty valorTotal;
    private final SimpleDoubleProperty valorPago;
    private final SimpleStringProperty responsavel;

    public CheckInReserva() {
        this.codigo = new SimpleLongProperty();
        this.cliente = new SimpleStringProperty();
        this.dataReserva = new SimpleStringProperty();
        this.dataCheckIn = new SimpleStringProperty();
        this.codigoReserva = new SimpleLongProperty();
        this.dataEsperadaCheckOut = new SimpleStringProperty();
        this.dataEsperadaCheckIn = new SimpleStringProperty();
        this.valorTotal = new SimpleDoubleProperty();
        this.valorPago = new SimpleDoubleProperty();
        this.responsavel = new SimpleStringProperty();
        this.dataEsperadaCheckIn = new SimpleStringProperty();
    }
    
    public String getDataEsperadaCheckIn() {
        return dataEsperadaCheckIn.get();
    }

    public void setDataEsperadaCheckIn(String dataEsperadaCheckIn) {
        this.dataEsperadaCheckIn.set(dataEsperadaCheckIn);
    }
    
    public Long getCodigoReserva() {
        return codigoReserva.get();
    }

    public void setCodigoReserva(Long codigoReserva) {
        this.codigoReserva.set(codigoReserva);
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

    public void setCliente(String cliente) {
        this.cliente.set(cliente);
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

    public void setResponsavel(String responsavel) {
        this.responsavel.set(responsavel);
    }
}