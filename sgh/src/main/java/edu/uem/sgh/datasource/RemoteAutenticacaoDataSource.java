/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteAutenticacaoDataSource extends AbstractDataSource {
    public RemoteAutenticacaoDataSource(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Result<Usuario> logIn(String email, String password) {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT COUNT(*) FROM usuarios WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario();
            }
            
            result = new Result.Success<>(usuario);
            rs.close();
            statement.close();
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}