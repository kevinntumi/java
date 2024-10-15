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
    private Connection remoteConnection, localConnection;
    private final String tblName = "servico";
    private LocalServicoRepository localServicoRepository;
    private RemoteServicoRepository remoteServicoRepository;

    @Constructor
    public ServicoRepository(@ConnectionType(type = Type.REMOTE) Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<Servico> get(long id) {
        return getRemoteServicoRepository().get(id);
    }

    public Result<List<Servico>> getAll() {
        return getRemoteServicoRepository().getAll();
    }

    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        return getRemoteServicoRepository().deleteOrUndelete(id, delete);
    }

    public Result<Boolean> edit(Servico servico) {
        return getRemoteServicoRepository().edit(servico);
    }

    public Result<Boolean> add(edu.uem.sgh.schema.Servico servico) {
        return getRemoteServicoRepository().add(servico);
    }

    public LocalServicoRepository getLocalServicoRepository() {
        if (localServicoRepository == null && localConnection != null) localServicoRepository = new LocalServicoRepository(localConnection, tblName);
        return localServicoRepository;
    }

    public RemoteServicoRepository getRemoteServicoRepository() {
        if (remoteServicoRepository == null && remoteConnection != null) remoteServicoRepository = new RemoteServicoRepository(remoteConnection, tblName);
        return remoteServicoRepository;
    }
}