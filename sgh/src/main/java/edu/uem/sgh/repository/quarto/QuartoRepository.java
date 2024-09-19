/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.quarto;

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
    private final Connection remoteConnection, localConnection;
    private RemoteQuartoRepository remoteQuartoRepository;
    private LocalQuartoRepository localQuartoRepository;

    public QuartoRepository(Connection remoteConnection, Connection localConnection) {
        this.remoteConnection = remoteConnection;
        this.localConnection = localConnection;
    }

    public Result<Boolean> add(Quarto t, FileInputStream fis) {
        Result<Boolean> remoteResult = getRemoteQuartoRepository().add(t, fis);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return getLocalQuartoRepository().add(t, fis);
    }

    public Result<Boolean> edit(Quarto t, FileInputStream fis) {
        Result<Boolean> remoteResult = getRemoteQuartoRepository().edit(t, fis);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return getLocalQuartoRepository().edit(t, fis);
    }

    public Result<Boolean> delete(long id, boolean delete) {
        Result<Boolean> remoteResult = getRemoteQuartoRepository().deleteOrUndelete(id, delete);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return getLocalQuartoRepository().deleteOrUndelete(id, delete);
    }

    public Result<List<Quarto>> getAll() {
        Result<List<Quarto>> remoteResult = getRemoteQuartoRepository().getAll();
        if (remoteResult instanceof Result.Success) return remoteResult;
        return getLocalQuartoRepository().getAll();
    }

    public Result<Quarto> get(long id) {
        Result<Quarto> remoteResult = getRemoteQuartoRepository().get(id);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return getLocalQuartoRepository().get(id);
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