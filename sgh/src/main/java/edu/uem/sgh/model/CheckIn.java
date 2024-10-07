/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class CheckIn {
    private long id;
    private Funcionario funcionario;
    private List<HospedeQuarto> hospedes;
    private long dataCheckIn;
    
    private CheckIn(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(long dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }
       
    public static class Imediato extends CheckIn {
        private Hospede cliente;
        private Pagamento pagamento;
        private long dataCheckOut;
        
        public Imediato() {
            super();
        }

        public Hospede getCliente() {
            return cliente;
        }

        public void setCliente(Hospede cliente) {
            this.cliente = cliente;
        }

        public Pagamento getPagamento() {
            return pagamento;
        }

        public void setPagamento(Pagamento pagamento) {
            this.pagamento = pagamento;
        }

        public long getDataCheckOut() {
            return dataCheckOut;
        }

        public void setDataCheckOut(long dataCheckOut) {
            this.dataCheckOut = dataCheckOut;
        }

        @Override
        public void setDataCheckIn(long dataCheckIn) {
            super.setDataCheckIn(dataCheckIn); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getDataCheckIn() {
            return super.getDataCheckIn(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setHospedes(List<HospedeQuarto> hospedes) {
            super.setHospedes(hospedes); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public List<HospedeQuarto> getHospedes() {
            return super.getHospedes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setFuncionario(Funcionario funcionario) {
            super.setFuncionario(funcionario); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public Funcionario getFuncionario() {
            return super.getFuncionario(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setId(long id) {
            super.setId(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getId() {
            return super.getId(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }
    
    public static class Reserva extends CheckIn {
        private edu.uem.sgh.model.Reserva reserva;

        public Reserva() {
            super();
        }

        public edu.uem.sgh.model.Reserva getReserva() {
            return reserva;
        }

        public void setReserva(edu.uem.sgh.model.Reserva reserva) {
            this.reserva = reserva;
        }

        @Override
        public void setDataCheckIn(long dataCheckIn) {
            super.setDataCheckIn(dataCheckIn); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getDataCheckIn() {
            return super.getDataCheckIn(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setHospedes(List<HospedeQuarto> hospedes) {
            super.setHospedes(hospedes); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public List<HospedeQuarto> getHospedes() {
            return super.getHospedes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setFuncionario(Funcionario funcionario) {
            super.setFuncionario(funcionario); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public Funcionario getFuncionario() {
            return super.getFuncionario(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setId(long id) {
            super.setId(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public long getId() {
            return super.getId(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }
}