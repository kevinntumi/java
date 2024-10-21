/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class CheckIn {
    private long id;
    private long idFuncionario;
    private long dataCheckIn;
    private long dataCheckOut;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
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
    
    public class Imediato extends CheckIn{
        private long idCliente;
        private double valorTotal;
        private double percentagemPaga;
    }
    
    public class Reserva extends CheckIn {
        private long idReserva;

        public long getIdReserva() {
            return idReserva;
        }

        public void setIdReserva(long idReserva) {
            this.idReserva = idReserva;
        }
    }
}