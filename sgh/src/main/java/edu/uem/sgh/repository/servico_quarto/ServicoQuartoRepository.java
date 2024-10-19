/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico_quarto;

import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.ServicoQuarto;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class ServicoQuartoRepository {
    private Connection remoteConnection;
    private RemoteServicoQuartoRepository remoteServicoQuartoRepository;

    public ServicoQuartoRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<ServicoQuarto> get(long id) {
        return getRemoteServicoQuartoRepository().get(id);
    }

    public Result<List<ServicoQuarto>> getAll() {
        return getRemoteServicoQuartoRepository().getAll();
    }

    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        return getRemoteServicoQuartoRepository().deleteOrUndelete(id, delete);
    }

    public Result<Boolean> edit(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
        return getRemoteServicoQuartoRepository().edit(servicoQuarto);
    }

    public Result<Boolean> add(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
        return getRemoteServicoQuartoRepository().add(servicoQuarto);
    }

    public Result<List<Quarto>> obterQuartosPorServico(long idServico) {
        return getRemoteServicoQuartoRepository().obterQuartosPorServico(idServico);
    }    

    public RemoteServicoQuartoRepository getRemoteServicoQuartoRepository() {
        if (remoteServicoQuartoRepository == null || remoteConnection != null) remoteServicoQuartoRepository = new RemoteServicoQuartoRepository(remoteConnection);
        return remoteServicoQuartoRepository;
    }
}