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
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName + " ORDER BY data_inicio DESC LIMIT 1");
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario();
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    
                    switch (columnName) {
                        case "id": 
                            usuario.setId(rs.getInt(columnName));
                                break;
                        case "id_tipo": 
                            usuario.setIdTipo(rs.getInt(columnName));
                                break;
                        case "nome": 
                            usuario.setNome(rs.getString(columnName));
                                break;
                        case "tipo": 
                            usuario.setTipo(Tipo.obterTipoViaString(rs.getString(columnName)));
                                break;
                        case "data_registo": 
                            usuario.setDataRegisto(rs.getDate(columnName).getTime());
                                break;
                        case "data_inicio": 
                            usuario.setDataInicio(rs.getLong(columnName));
                                break;
                        case "palavra_passe": 
                            usuario.setPalavraPasse(rs.getString(columnName));
                                break;
                    }
                }
            }
            
            result = new Result.Success<>(usuario);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Usuario> logIn(Usuario usuario) {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO " + tblName + " (id, id_tipo, nome, tipo, data_registo, data_inicio) VALUES(?,?,?,?,?,?)");
            statement.setLong(1, usuario.getId());
            statement.setLong(2, usuario.getIdTipo());
            statement.setString(3, usuario.getNome());
            statement.setString(4, Tipo.converterTipoParaString(usuario.getTipo()));
            statement.setLong(5, usuario.getDataRegisto());
            statement.setLong(6, System.currentTimeMillis());
            result = new Result.Success<>((statement.executeUpdate() > 0) ? usuario : null);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Boolean> logOut() {                
        Result<Boolean> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("DELETE * FROM " + tblName);
            result = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}