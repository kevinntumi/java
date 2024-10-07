/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class HospedeQuarto {
    private long idQuarto;
    private long idHospede;

    public long getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(long idQuarto) {
        this.idQuarto = idQuarto;
    }

    public long getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(long idHospede) {
        this.idHospede = idHospede;
    }
    
    private class Reserva extends HospedeQuarto {
        private long idReserva;

        public long getIdReserva() {
            return idReserva;
        }

        public void setIdReserva(long idReserva) {
            this.idReserva = idReserva;
        }

        @Override
        public void setIdHospede(long idHospede) {
            super.setIdHospede(idHospede); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getIdHospede() {
            return super.getIdHospede(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setIdQuarto(long idQuarto) {
            super.setIdQuarto(idQuarto); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getIdQuarto() {
            return super.getIdQuarto(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }
    
    private class CheckIn extends HospedeQuarto {
        private long dataCheckIn;
        private long dataCheckOut;
        private long idCheckIn;
        private String tipoCheckIn;

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

        public long getIdCheckIn() {
            return idCheckIn;
        }

        public void setIdCheckIn(long idCheckIn) {
            this.idCheckIn = idCheckIn;
        }

        public String getTipoCheckIn() {
            return tipoCheckIn;
        }

        public void setTipoCheckIn(String tipoCheckIn) {
            this.tipoCheckIn = tipoCheckIn;
        }
    }
}