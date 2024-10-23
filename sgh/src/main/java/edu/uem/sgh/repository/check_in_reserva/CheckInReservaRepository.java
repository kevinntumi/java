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

    public CheckInReservaRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }
    
    public Result<Boolean> efectuar(CheckIn.Reserva checkInReserva) {
        if (remoteConnection == null) {
            return new Result.Error<>(new Exception());
        }
        
        return getRemoteCheckInReservaDataSource().efectuar(checkInReserva);
    }
    
    public Result<List<edu.uem.sgh.model.CheckIn.Reserva>> obterTodos() {
        if (remoteConnection == null) {
            return new Result.Error<>(new Exception());
        }
        
        return getRemoteCheckInReservaDataSource().obterTodos();
    }

    public RemoteCheckInReservaDataSource getRemoteCheckInReservaDataSource() {
        if (remoteCheckInReservaDataSource == null && remoteConnection != null) remoteCheckInReservaDataSource = new RemoteCheckInReservaDataSource(remoteConnection);
        return remoteCheckInReservaDataSource;
    }
}