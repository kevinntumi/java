/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class CheckOut {
    private Long id;
    private Long dataCheckOut;
    private Funcionario responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(Long dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public static class Reserva extends CheckOut {
        private CheckIn.Reserva checkIn;
        private Double valorPago;

        public Reserva() {
            super();
        }

        public CheckIn.Reserva getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(CheckIn.Reserva checkIn) {
            this.checkIn = checkIn;
        }

        public Double getValorPago() {
            return valorPago;
        }

        public void setValorPago(Double valorPago) {
            this.valorPago = valorPago;
        }
    }
}