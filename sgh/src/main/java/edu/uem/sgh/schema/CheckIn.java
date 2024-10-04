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
   
    private class Imediato extends CheckIn{
        private long idCliente;
        private double valorTotal;
        private double percentagemPaga;
    }
    
    private class Reserva extends CheckIn {
        private long idReserva;
    }
}