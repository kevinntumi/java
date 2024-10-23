/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.hospede;

import edu.uem.sgh.datasource.LocalHospedeDataSource;
import edu.uem.sgh.datasource.RemoteHospedeDataSource;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class HospedeRepository {
    private Connection localConnection, remoteConnection;
    private RemoteHospedeDataSource remoteHospedeDataSource;
    private LocalHospedeDataSource localHospedeDataSource;

    public HospedeRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setRemoteConnection(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setLocalHospedeDataSource(LocalHospedeDataSource localHospedeDataSource) {
        this.localHospedeDataSource = localHospedeDataSource;
    }
    
    public void setRemoteHospedeDataSource(RemoteHospedeDataSource remoteHospedeDataSource) {
        this.remoteHospedeDataSource = remoteHospedeDataSource;
    }
    
    public Result<Boolean> inserir(Hospede hospede) {
        return getRemoteHospedeDataSource().inserir(hospede);
    }

    public Result<Boolean> editar(Hospede hospede) {
        return getRemoteHospedeDataSource().editar(hospede);
    }
    
    public Result<List<Hospede>> obterTodos() {
        return getRemoteHospedeDataSource().obterTodos();
    }

    public RemoteHospedeDataSource getRemoteHospedeDataSource() {
        if (remoteHospedeDataSource == null && remoteConnection != null) remoteHospedeDataSource = new RemoteHospedeDataSource(remoteConnection);
        return remoteHospedeDataSource;
    }

    public LocalHospedeDataSource getLocalHospedeDataSource() {
        if (localHospedeDataSource == null && localConnection == null) localHospedeDataSource = new LocalHospedeDataSource(localConnection);
        return localHospedeDataSource;
    }
}