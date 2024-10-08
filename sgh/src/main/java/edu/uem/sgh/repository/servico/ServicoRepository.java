/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico;

import edu.uem.sgh.annotation.Constructor;
import edu.uem.sgh.connection.ConnectionType;
import edu.uem.sgh.connection.Type;
import edu.uem.sgh.dao.ServicoDao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class ServicoRepository implements ServicoDao{
    private final Connection remoteConnection, localConnection;
    private LocalServicoRepository localServicoRepository;
    private RemoteServicoRepository remoteServicoRepository;

    @Constructor
    public ServicoRepository(@ConnectionType(type = Type.REMOTE) Connection remoteConnection, @ConnectionType Connection localConnection) {
        this.remoteConnection = remoteConnection;
        this.localConnection = localConnection;
    }

    public Result<Servico> get(long id) {
        Result<Servico> remoteResult = getRemoteServicoRepository().get(id);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return localServicoRepository.get(id);
    }

    public Result<List<Servico>> getAll() {
        Result<List<Servico>> remoteResult = getRemoteServicoRepository().getAll();
        if (remoteResult instanceof Result.Success) return remoteResult;
        return localServicoRepository.getAll();
    }

    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        Result<Boolean> remoteResult = getRemoteServicoRepository().deleteOrUndelete(id, delete);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return localServicoRepository.deleteOrUndelete(id, delete);
    }

    public Result<Boolean> edit(Servico servico) {
        Result<Boolean> remoteResult = getRemoteServicoRepository().edit(servico);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return localServicoRepository.edit(servico);
    }

    public Result<Boolean> add(Servico servico) {
        Result<Boolean> remoteResult = getRemoteServicoRepository().add(servico);
        if (remoteResult instanceof Result.Success) return remoteResult;
        return localServicoRepository.add(servico);
    }

    public LocalServicoRepository getLocalServicoRepository() {
        if (localServicoRepository == null) localServicoRepository = new LocalServicoRepository(localConnection);
        return localServicoRepository;
    }

    public RemoteServicoRepository getRemoteServicoRepository() {
        if (remoteServicoRepository == null) remoteServicoRepository = new RemoteServicoRepository(remoteConnection);
        return remoteServicoRepository;
    }
}