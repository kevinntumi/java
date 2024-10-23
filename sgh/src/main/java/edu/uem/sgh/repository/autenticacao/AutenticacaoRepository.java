/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.autenticacao;

import edu.uem.sgh.annotation.Constructor;
import edu.uem.sgh.connection.ConnectionType;
import edu.uem.sgh.datasource.LocalAutenticacaoDataSource;
import edu.uem.sgh.datasource.RemoteAutenticacaoDataSource;
import edu.uem.sgh.connection.Type;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.sql.Connection;

/**
 *
 * @author Kevin Ntumi
 */
public class AutenticacaoRepository {
    private final String tblName = "usuario";
    private final Connection remoteConnection, localConnection;
    private RemoteAutenticacaoDataSource remoteAutenticacaoDataSource;
    private LocalAutenticacaoDataSource localAutenticacaoDataSource;

    @Constructor
    public AutenticacaoRepository(@ConnectionType(type = Type.REMOTE) Connection remoteConnection, @ConnectionType Connection localConnection) {
        this.remoteConnection = remoteConnection;
        this.localConnection = localConnection;
    }
    
    public Result<Usuario> getUserById(long id) {
        return getRemoteAutenticacaoDataSource().getUserById(id);
    }

    public Result<Usuario> logIn(long id, String password, boolean manterSessaoIniciada) {
        Result<Usuario> result = getRemoteAutenticacaoDataSource().getUserByIdAndPassword(id, password);
        
        if (!(result instanceof Result.Error || result instanceof Result.Success)) {
            return null;
        }
        
        if (result instanceof Result.Error) {
            return result;
        }
        
        Result.Success<Usuario> success = (Result.Success<Usuario>) result;

        if (success.getData() == null || manterSessaoIniciada == false) {
            return result;
        }
        
        return getLocalAutenticacaoDataSource().logIn(success.getData());
    }

    public Result<Usuario> getCurrentUser() {
        return getLocalAutenticacaoDataSource().getCurrentUser();
    }

    public Result<Boolean> logOut() {
        return getLocalAutenticacaoDataSource().logOut();
    }
    
    private RemoteAutenticacaoDataSource getRemoteAutenticacaoDataSource() {
        if (remoteAutenticacaoDataSource == null) {
            remoteAutenticacaoDataSource = new RemoteAutenticacaoDataSource(remoteConnection, tblName);
        }
        
        return remoteAutenticacaoDataSource;
    }

    private LocalAutenticacaoDataSource getLocalAutenticacaoDataSource() {
        if (localAutenticacaoDataSource == null) {
            localAutenticacaoDataSource = new LocalAutenticacaoDataSource(localConnection, tblName);
        }
        
        return localAutenticacaoDataSource;
    }   
}