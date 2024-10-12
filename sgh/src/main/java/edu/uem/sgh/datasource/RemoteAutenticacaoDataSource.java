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
public class RemoteAutenticacaoDataSource extends AbstractDataSource {
    private final String tblName;
            
    public RemoteAutenticacaoDataSource(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Usuario> getUserById(long id) {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName + " WHERE id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario(System.currentTimeMillis(), id);
                
                for (int i = 1 ; i < resultSetMetaData.getColumnCount() ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);

                    switch (nomeColuna) {
                        case "data_alterado":
                            usuario.setDataAlterado(rs.getDate(nomeColuna).getTime());
                                break;
                        case "data_registo":
                            usuario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;
                        case "tipo":
                            usuario.setTipo(Tipo.obterTipoViaString(rs.getString(nomeColuna)));
                                break;
                        case "id_usuario": 
                            usuario.setIdTipo(rs.getInt(nomeColuna));
                                break;
                    }
                }
                
                break;
            }
            
            result = new Result.Success<>(usuario);
            rs.close();
            statement.close();
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }

    public Result<Usuario> logIn(long id, String password) {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT id_usuario, tipo, data_registo, data_alterado, palavra_passe FROM " + tblName + " WHERE id = ? AND palavra_passe = ?");
            statement.setLong(1, id);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            
            Usuario usuario = null;
            
            while (usuario == null && rs.next()) {
                usuario = new Usuario(System.currentTimeMillis(), id);
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);
                    System.out.println("coluna: " + nomeColuna);
                    switch (nomeColuna) {
                        case "data_alterado":
                            usuario.setDataAlterado(rs.getDate(nomeColuna).getTime());
                                break;
                        case "data_registo":
                            usuario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;
                        case "tipo":
                            usuario.setTipo(Tipo.obterTipoViaString(rs.getString(nomeColuna)));
                                break;
                        case "id_usuario": 
                            usuario.setIdTipo(rs.getInt(nomeColuna));
                                break;
                        case "palavra_passe": 
                            usuario.setPalavraPasse(rs.getString(nomeColuna));
                                break;
                    }
                }
            }
            
            if (usuario != null && (usuario.getPalavraPasse() == null || !usuario.getPalavraPasse().equals(password)))
                usuario = null;
            
            result = new Result.Success<>(usuario);
            rs.close();
            statement.close();
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}