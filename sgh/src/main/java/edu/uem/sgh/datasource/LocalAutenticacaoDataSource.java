/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.model.Usuario.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Kevin Ntumi
 */
public class LocalAutenticacaoDataSource extends AbstractDataSource {
    private final String tblName;
    
    public LocalAutenticacaoDataSource(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Usuario> getCurrentUser() {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            Usuario usuario = null;
            
            while (usuario == null && rs.next()) {
                usuario = new Usuario();
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    
                    switch (columnName) {
                        case "id": usuario.setId(rs.getInt(columnName));
                            break;
                        case "id_tipo": usuario.setIdTipo(rs.getInt(columnName));
                            break;
                        case "nome": usuario.setNome(rs.getString(columnName));
                            break;
                        case "tipo": usuario.setTipo(obterTipoUsuario(rs.getString(columnName)));
                            break;
                        case "data_registo": usuario.setDataRegisto(rs.getDate(columnName).getTime());
                            break;
                        case "data_alterado": usuario.setDataAlterado(rs.getDate(columnName).getTime());
                            break;
                    }
                }
                
                usuario.setDataInicio(System.currentTimeMillis());
            }
            
            if (usuario == null) usuario = Usuario.VAZIO;
            
            result = new Result.Success<>(usuario);
            rs.close();
        } catch(SQLException e) {
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
    
    private Tipo obterTipoUsuario(String tipo) {
        switch (tipo) {
            case "CLIENTE":
                return Tipo.CLIENTE;
            case "FUNCIONARIO":
                return Tipo.FUNCIONARIO;
            case "GERENTE":
                return Tipo.GERENTE;
            default:
                return Tipo.ADMINISTRADOR;
        }
    }
}