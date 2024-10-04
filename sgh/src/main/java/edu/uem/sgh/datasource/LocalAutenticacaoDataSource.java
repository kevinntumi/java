/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.sql.Connection;

/**
 *
 * @author Kevin Ntumi
 */
public class LocalAutenticacaoDataSource extends AbstractDataSource {
    public LocalAutenticacaoDataSource(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Usuario> getCurrentUser() {
        Result<Usuario> result = null;
        
        try {
            throw new RuntimeException();
        } catch(Exception e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Usuario> logIn(Usuario usuario) {
        if (usuario == null) return new Result.Error<>(new Exception());
                
        Result<Usuario> result = null;
        
        try {
            
        } catch (Exception e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Boolean> logOut() {                
        Result<Boolean> result = null;
        
        try {
            
        } catch (Exception e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}