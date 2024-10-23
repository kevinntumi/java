/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.passagem;

import edu.uem.sgh.datasource.RemotePassagemDataSource;
import edu.uem.sgh.model.Passagem;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class PassagemRepository {
    private Connection remoteConnection;
    private RemotePassagemDataSource remotePassagemDataSource;

    public PassagemRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public Result<Boolean> inserirPassagem(Passagem passagem) {
        return getRemotePassagemDataSource().inserirPassagem(passagem);
    }

    public Result<Boolean> editarPassagem(Passagem passagem) {
        return getRemotePassagemDataSource().editarPassagem(passagem);
    }

    public Result<List<Passagem>> obterPassagensPorFuncionario(Long idFuncionario) {
        return getRemotePassagemDataSource().obterPassagensPorFuncionario(idFuncionario);
    }

    public RemotePassagemDataSource getRemotePassagemDataSource() {
        if (remotePassagemDataSource == null && remoteConnection != null) remotePassagemDataSource = new RemotePassagemDataSource(remoteConnection);
        return remotePassagemDataSource;
    }
}