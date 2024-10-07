/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public class HospedeQuarto {
    private Hospede hospede;
    private Quarto quarto;

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
    
    public static class Estadia extends HospedeQuarto {
        private Long dataCheckIn;
        private Long dataCheckOut;
        
        public Estadia() {
            super();
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

        @Override
        public void setQuarto(Quarto quarto) {
            super.setQuarto(quarto); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setHospede(Hospede hospede) {
            super.setHospede(hospede); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public Quarto getQuarto() {
            return super.getQuarto(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public Hospede getHospede() {
            return super.getHospede(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }
}