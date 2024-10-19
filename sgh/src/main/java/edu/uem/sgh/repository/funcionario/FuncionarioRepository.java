/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.funcionario;

import edu.uem.sgh.datasource.RemoteFuncionarioDataSource;
import edu.uem.sgh.model.Passagem;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class FuncionarioRepository {
    private final String tblName = "funcionario";
    private Connection remoteConnection;
    private RemoteFuncionarioDataSource remoteFuncionarioDataSource;

    public FuncionarioRepository(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setRemoteConnection(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }
    
    public Result<List<Passagem>> obterPassagensPorFuncionario(long idFuncionario) {
        if (getRemoteFuncionarioDataSource() == null) return new Result.Error<>(new Exception());
        return getRemoteFuncionarioDataSource().obterPassagensPorFuncionario(idFuncionario);
    }
    
    public Result<Boolean> inserirFuncionario(edu.uem.sgh.schema.Funcionario funcionario) {
        if (getRemoteFuncionarioDataSource() == null) return new Result.Error<>(new Exception());
        return getRemoteFuncionarioDataSource().inserirFuncionario(funcionario);
    }

    public Result<Boolean> editarFuncionario(edu.uem.sgh.schema.Funcionario funcionario) {
        if (getRemoteFuncionarioDataSource() == null) return new Result.Error<>(new Exception());
        return getRemoteFuncionarioDataSource().editarFuncionario(funcionario);
    }

    public Result<List<edu.uem.sgh.model.Funcionario>> obterFuncionariosPorGerente(Long idGerente) {
        if (getRemoteFuncionarioDataSource() == null) return new Result.Error<>(new Exception());
        return getRemoteFuncionarioDataSource().obterFuncionariosPorGerente(idGerente);
    }

    public Result<List<edu.uem.sgh.model.Funcionario>> obterTodosFuncionarios() {
        if (getRemoteFuncionarioDataSource() == null) return new Result.Error<>(new Exception());
        return getRemoteFuncionarioDataSource().obterTodosFuncionarios();
    }

    public RemoteFuncionarioDataSource getRemoteFuncionarioDataSource() {
        if (remoteFuncionarioDataSource == null && remoteConnection != null) remoteFuncionarioDataSource = new RemoteFuncionarioDataSource(remoteConnection, tblName);
        return remoteFuncionarioDataSource;
    }
}