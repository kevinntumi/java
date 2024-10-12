/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.check_in_reserva;

import edu.uem.sgh.datasource.RemoteCheckInReservaDataSource;
import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class CheckInReservaRepository {
    private Connection remoteConnection;
    private RemoteCheckInReservaDataSource remoteCheckInReservaDataSource;

    public CheckInReservaRepository() {
    }
    
    public Result<Boolean> efectuar(CheckIn.Reserva checkInReserva) {
        return getRemoteCheckInReservaDataSource().efectuar(checkInReserva);
    }
    
    public Result<List<CheckIn.Reserva>> obterTodos() {
        return getRemoteCheckInReservaDataSource().obterTodos();
    }

    public RemoteCheckInReservaDataSource getRemoteCheckInReservaDataSource() {
        if (remoteCheckInReservaDataSource == null) remoteCheckInReservaDataSource = new RemoteCheckInReservaDataSource(remoteConnection);
        return remoteCheckInReservaDataSource;
    }
}