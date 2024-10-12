/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.quarto;

import edu.uem.sgh.annotation.Constructor;
import edu.uem.sgh.connection.ConnectionType;
import edu.uem.sgh.connection.Type;
import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class QuartoRepository {
    private Connection remoteConnection, localConnection;
    private RemoteQuartoRepository remoteQuartoRepository;
    private LocalQuartoRepository localQuartoRepository;

    @Constructor
    public QuartoRepository(@ConnectionType(type = Type.REMOTE) Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setRemoteConnection(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }
    
    public Result<Boolean> add(Quarto t, FileInputStream fis) {
        return getRemoteQuartoRepository().add(t, fis);
    }

    public Result<Boolean> edit(Quarto t, FileInputStream fis) {
        return getRemoteQuartoRepository().edit(t, fis);
    }

    public Result<Boolean> delete(long id, boolean delete) {
        return getRemoteQuartoRepository().deleteOrUndelete(id, delete);
    }

    public Result<List<Quarto>> getAll() {
        return getRemoteQuartoRepository().getAll();
    }

    public Result<Quarto> get(long id) {
        return getRemoteQuartoRepository().get(id);
    }   

    public RemoteQuartoRepository getRemoteQuartoRepository() {
        if (remoteQuartoRepository == null) remoteQuartoRepository = new RemoteQuartoRepository(remoteConnection);
        return remoteQuartoRepository;
    }

    public LocalQuartoRepository getLocalQuartoRepository() {
        if (localQuartoRepository == null) localQuartoRepository = new LocalQuartoRepository(localConnection);
        return localQuartoRepository;
    }
}