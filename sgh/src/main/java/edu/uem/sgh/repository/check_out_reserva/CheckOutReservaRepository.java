/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.check_out_reserva;

import edu.uem.sgh.datasource.RemoteCheckOutReservaDataSource;
import edu.uem.sgh.model.CheckOut;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class CheckOutReservaRepository {
    private final String tblName = "check_out_reserva";
    private Connection remoteConnection, localConnection;
    private RemoteCheckOutReservaDataSource remoteCheckOutReservaDataSource;

    public CheckOutReservaRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<List<CheckOut.Reserva>> obterTodos() {
        if (remoteConnection == null)
            return new Result.Error<>(new SQLException());
        
        return getRemoteCheckOutReservaDataSource().obterTodos();
    }

    public Result<List<CheckOut.Reserva>> obterCheckOutsPorFuncionario(long idFuncionario) {
        if (remoteConnection == null)
            return new Result.Error<>(new SQLException());
        
        return getRemoteCheckOutReservaDataSource().obterCheckOutsPorFuncionario(idFuncionario);
    }

    public Result<List<CheckOut.Reserva>> obterCheckOutsPorHospede(long idHospede) {
        if (remoteConnection == null)
            return new Result.Error<>(new SQLException());
        
        return getRemoteCheckOutReservaDataSource().obterCheckOutsPorHospede(idHospede);
    }

    public Result<CheckOut.Reserva> obterCheckOutPorIdCheckIn(int idCheckIn) {
        if (remoteConnection == null)
            return new Result.Error<>(new SQLException());
        
        return getRemoteCheckOutReservaDataSource().obterCheckOutPorIdCheckIn(idCheckIn);
    }
    
    public void setRemoteConnection(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setLocalConnection(Connection localConnection) {
        this.localConnection = localConnection;
    }

    public RemoteCheckOutReservaDataSource getRemoteCheckOutReservaDataSource() {
        if (remoteCheckOutReservaDataSource == null && remoteConnection != null) remoteCheckOutReservaDataSource = new RemoteCheckOutReservaDataSource(remoteConnection, tblName);
        return remoteCheckOutReservaDataSource;
    }
}