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
    
    public Result<Usuario> getUserByIdTipoETipo(long idTipo, Tipo tipo) {
        Result<Usuario> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName + " WHERE id_tipo = ? AND tipo = ?");
            statement.setLong(1, idTipo);
            statement.setString(2, Tipo.converterTipoParaString(tipo));
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario();
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);

                    switch (nomeColuna) {
                        case "data_registo":
                            usuario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;
                        case "id" :
                            usuario.setId(rs.getLong(nomeColuna));
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
            
            statement.close();
            result = new Result.Success<>(usuario);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Usuario> getUserById(long id) {
        Result<Usuario> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM usuario WHERE usuario.id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario(System.currentTimeMillis(), id);
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);
                    
                    switch (nomeColuna) {
                        case "id": 
                            usuario.setId(rs.getInt(nomeColuna));
                                break;
                        case "id_tipo": 
                            usuario.setIdTipo(rs.getInt(nomeColuna));
                                break;
                        case "nome": 
                            usuario.setNome(rs.getString(nomeColuna));
                                break;
                        case "tipo": 
                            usuario.setTipo(Tipo.obterTipoViaString(rs.getString(nomeColuna)));
                                break;
                        case "data_registo": 
                            usuario.setDataRegisto(rs.getDate(nomeColuna).getTime());
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
    
    public Result<Usuario> getUserByIdAndPassword(long id, String password) {
        Result<Usuario> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT usuario.*, gerente.nome AS nome FROM usuario JOIN gerente ON gerente.id = usuario.id_tipo WHERE usuario.tipo = 'GERENTE' AND usuario.id = ? AND usuario.palavra_passe = ? UNION SELECT usuario.*, funcionario.nome AS nome FROM usuario JOIN funcionario ON funcionario.id = usuario.id_tipo WHERE usuario.tipo = 'FUNCIONARIO' AND usuario.id = ? AND usuario.palavra_passe = ? UNION SELECT usuario.*, hospede.nome AS nome FROM usuario JOIN hospede ON hospede.id = usuario.id_tipo WHERE usuario.tipo = 'CLIENTE' AND usuario.id = ? AND usuario.palavra_passe = ?")){
            for (int i = 1 ; i <= 6 ; i++) {
                if (i % 2 != 0)
                    statement.setLong(i, id);
                else
                    statement.setString(i, password);
            }

            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            
            Usuario usuario = null;
            
            while (rs.next()) {
                usuario = new Usuario(System.currentTimeMillis(), id);
                
                for (int i = 1 ; i <= resultSetMetaData.getColumnCount() ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);
                    
                    switch (nomeColuna) {
                        case "id": 
                            usuario.setId(rs.getInt(nomeColuna));
                                break;
                        case "id_tipo": 
                            usuario.setIdTipo(rs.getInt(nomeColuna));
                                break;
                        case "nome": 
                            usuario.setNome(rs.getString(nomeColuna));
                                break;
                        case "tipo": 
                            usuario.setTipo(Tipo.obterTipoViaString(rs.getString(nomeColuna)));
                                break;
                        case "data_registo": 
                            usuario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;
                        case "palavra_passe": 
                            usuario.setPalavraPasse(rs.getString(nomeColuna));
                                break;
                    }
                }
            }
            
            if (usuario != null && (usuario.getPalavraPasse() == null || !usuario.getPalavraPasse().equals(password))) {
                usuario = null;
            }
            
            result = new Result.Success<>(usuario);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}