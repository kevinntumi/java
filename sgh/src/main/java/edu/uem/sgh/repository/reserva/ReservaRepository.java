/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.reserva;

import edu.uem.sgh.datasource.RemoteReservaDataSource;
import edu.uem.sgh.helper.ReservaSituacao;
import edu.uem.sgh.model.Reserva;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class ReservaRepository {
    private Connection remoteConnection, localConnection;
    private RemoteReservaDataSource remoteReservaDataSource;

    public ReservaRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<Boolean> inserirReserva(int idCliente, int idFuncionario, long dataCheckIn, long dataCheckOut, double valorPago, double valorTotal) {
        return remoteReservaDataSource.inserirReserva(idCliente, idFuncionario, dataCheckIn, dataCheckOut, valorPago, valorTotal);
    }

    public Result<Boolean> editarReserva(Long dataSituacao, ReservaSituacao reservaSituacao, int id) {
        return remoteReservaDataSource.editarReserva(dataSituacao, reservaSituacao, id);
    }

    public Result<List<Reserva>> obterTodasReservas() {
        return remoteReservaDataSource.obterTodasReservas();
    }

    private RemoteReservaDataSource getRemoteReservaDataSource() {
        if (remoteReservaDataSource == null && remoteConnection != null) remoteReservaDataSource = new RemoteReservaDataSource(remoteConnection);
        return remoteReservaDataSource;
    }
}