/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.autenticacao;

import edu.uem.sgh.datasource.LocalAutenticacaoDataSource;
import edu.uem.sgh.datasource.RemoteAutenticacaoDataSource;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.sql.Connection;

/**
 *
 * @author Kevin Ntumi
 */
public class AutenticacaoRepository {
    private final Connection remoteConnection, localConnection;
    private RemoteAutenticacaoDataSource remoteAutenticacaoDataSource;
    private LocalAutenticacaoDataSource localAutenticacaoDataSource;

    public AutenticacaoRepository(Connection remoteConnection, Connection localConnection) {
        this.remoteConnection = remoteConnection;
        this.localConnection = localConnection;
    }

    public Result<Boolean> logIn(String email, String password) {
        Result<Usuario> result = getRemoteAutenticacaoDataSource().logIn(email, password);
        if (result instanceof Result.Error) return new Result.Error<>(((Result.Error<Usuario>) result).getException());        
        Result.Success<Usuario> success = ((Result.Success<Usuario>) result);
        return getLocalAutenticacaoDataSource().logIn(success.getData());
    }

    public Result<Usuario> getCurrentUser() {
        return getLocalAutenticacaoDataSource().getCurrentUser();
    }

    public Result<Boolean> logOut() {
        return getLocalAutenticacaoDataSource().logOut();
    }
    
    public RemoteAutenticacaoDataSource getRemoteAutenticacaoDataSource() {
        if (remoteAutenticacaoDataSource == null) remoteAutenticacaoDataSource = new RemoteAutenticacaoDataSource(remoteConnection);
        return remoteAutenticacaoDataSource;
    }

    public LocalAutenticacaoDataSource getLocalAutenticacaoDataSource() {
        if (localAutenticacaoDataSource == null) localAutenticacaoDataSource = new LocalAutenticacaoDataSource(localConnection);
        return localAutenticacaoDataSource;
    }   
}