/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico_quarto;

import edu.uem.sgh.datasource.RemoteServicoQuartoDataSource;
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
    private RemoteServicoQuartoDataSource remoteServicoQuartoDataSource;

    public ServicoQuartoRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<Boolean> associarQuartoEServico(ServicoQuarto servicoQuarto) {
        return getRemoteServicoQuartoDataSource().associarQuartoEServico(servicoQuarto);
    }

    public Result<Boolean> editarAssociacao(ServicoQuarto servicoQuarto) {
        return getRemoteServicoQuartoDataSource().editarAssociacao(servicoQuarto);
    }

    public Result<List<ServicoQuarto>> obterAssociacoesPorQuarto(long idQuarto) {
        return getRemoteServicoQuartoDataSource().obterAssociacoesPorQuarto(idQuarto);
    }

    public Result<List<ServicoQuarto>> obterAssociacoesPorServico(long idServico) {
        return getRemoteServicoQuartoDataSource().obterAssociacoesPorServico(idServico);
    }

    public Result<ServicoQuarto> obterPorId(long id) {
        return remoteServicoQuartoDataSource.obterPorId(id);
    }

    public RemoteServicoQuartoDataSource getRemoteServicoQuartoDataSource() {
        if (remoteServicoQuartoDataSource == null && remoteConnection != null) remoteServicoQuartoDataSource = new RemoteServicoQuartoDataSource(remoteConnection);
        return remoteServicoQuartoDataSource;
    }
}