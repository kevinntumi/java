/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.helper.Situacao;
import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.model.ServicoQuarto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteServicoQuartoDataSource extends AbstractDataSource {
    public RemoteServicoQuartoDataSource(Connection connection) {
        super(connection);
    }
    
    public Result<Boolean> associarQuartoEServico(ServicoQuarto servicoQuarto) {
        Result<Boolean> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO servico_quarto(id_servico, id_quarto, situacao, data_associacao, data_situacao) VALUES(?,?,?,?,?)")){
            statement.setLong(1, servicoQuarto.getServico().getId());
            statement.setLong(2, servicoQuarto.getQuarto().getId());
            statement.setString(3, Situacao.ServicoQuarto.obterEmString(servicoQuarto.getSituacao()));
            statement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            result = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Boolean> editarAssociacao(ServicoQuarto servicoQuarto) {
        Result<Boolean> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE servico_quarto SET situacao = ?, data_situacao = ? WHERE id = ?")){
            statement.setString(1, Situacao.ServicoQuarto.obterEmString(servicoQuarto.getSituacao()));
            statement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setLong(3, servicoQuarto.getId());
            result = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<List<ServicoQuarto>> obterAssociacoesPorQuarto(long idQuarto) {
        Result<List<ServicoQuarto>> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT servico_quarto.id, servico_quarto.id_quarto, servico_quarto.data_associacao, servico_quarto.data_situacao, servico_quarto.situacao, servico.descricao AS servico_descricao, servico.situacao AS servico_situacao, quarto.descricao AS quarto_descricao, quarto.situacao AS quarto_situacao FROM servico_quarto JOIN servico ON servico.id = servico_quarto.id_servico JOIN quarto ON quarto.id = servico_quarto.id_quarto WHERE servico_quarto.id_quarto = ?")){
            statement.setLong(1, idQuarto);
            ResultSet rs = statement.executeQuery();
            List<ServicoQuarto> servicoQuartos = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    ServicoQuarto servicoQuarto = new ServicoQuarto();
                    Servico servico = new Servico();
                    Quarto quarto = new Quarto();
                    quarto.setId(idQuarto);
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id":
                                servicoQuarto.setId(rs.getLong(columnName));
                                    break;
                            case "id_servico":
                                servico.setId(rs.getLong(columnName));
                                    break;
                            case "id_quarto":
                                quarto.setId(rs.getLong(columnName));
                                    break;
                            case "data_associacao":
                                servicoQuarto.setDataAssociacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_situacao":
                                servicoQuarto.setDataSituacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "situacao":
                                servicoQuarto.setSituacao(Situacao.ServicoQuarto.obterViaString(rs.getString(columnName)));
                                    break;
                            case "servico_descricao":
                                servico.setDescricao(rs.getString(columnName));
                                    break;
                            case "servico_situacao":
                                servico.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                            case "quarto_descricao":
                                quarto.setDescricao(rs.getString(columnName));
                                    break;
                            case "quarto_situacao":
                                quarto.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                    
                    servicoQuarto.setQuarto(quarto);
                    servicoQuarto.setServico(servico);
                    servicoQuartos.add(servicoQuarto);
                }
            }
            
            result = new Result.Success<>(servicoQuartos);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<List<ServicoQuarto>> obterAssociacoesPorServico(long idServico) {
        Result<List<ServicoQuarto>> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT servico_quarto.id, servico_quarto.id_quarto, servico_quarto.data_associacao, servico_quarto.data_situacao, servico_quarto.situacao, servico.descricao AS servico_descricao, servico.situacao AS servico_situacao, quarto.descricao AS quarto_descricao AS quarto_descricao, quarto.situacao AS quarto_situacao FROM servico_quarto JOIN servico ON servico.id = servico_quarto.id_servico JOIN quarto ON quarto.id = servico_quarto.id_quarto WHERE servico_quarto.id_servico = ?")){
            statement.setLong(1, idServico);
            ResultSet rs = statement.executeQuery();
            List<ServicoQuarto> servicoQuartos = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    ServicoQuarto servicoQuarto = new ServicoQuarto();
                    Servico servico = new Servico();
                    Quarto quarto = new Quarto();
                    servico.setId(idServico);
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id":
                                servicoQuarto.setId(rs.getLong(columnName));
                                    break;
                            case "id_quarto":
                                quarto.setId(rs.getLong(columnName));
                                    break;
                            case "data_associacao":
                                servicoQuarto.setDataAssociacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_situacao":
                                servicoQuarto.setDataSituacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "situacao":
                                servicoQuarto.setSituacao(Situacao.ServicoQuarto.obterViaString(rs.getString(columnName)));
                                    break;
                            case "servico_descricao":
                                servico.setDescricao(rs.getString(columnName));
                                    break;
                            case "servico_situacao":
                                servico.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                            case "quarto_descricao":
                                quarto.setDescricao(rs.getString(columnName));
                                    break;
                            case "quarto_situacao":
                                quarto.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                    
                    servicoQuarto.setQuarto(quarto);
                    servicoQuarto.setServico(servico);
                    servicoQuartos.add(servicoQuarto);
                }
            }
            
            result = new Result.Success<>(servicoQuartos);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<ServicoQuarto> obterPorId(long id) {
        Result<ServicoQuarto> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT servico_quarto.id, servico_quarto.id_quarto, servico_quarto.data_associacao, servico_quarto.data_situacao, servico_quarto.situacao, servico.descricao AS servico_descricao, servico.situacao AS servico_situacao, quarto.descricao AS quarto_descricao, quarto.situacao AS quarto_situacao FROM servico_quarto JOIN servico ON servico.id = servico_quarto.id_servico JOIN quarto ON quarto.id = servico_quarto.id_quarto WHERE servico_quarto.id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            ServicoQuarto servicoQuarto = null;
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    servicoQuarto = new ServicoQuarto();
                    Servico servico = new Servico();
                    Quarto quarto = new Quarto();
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id":
                                servicoQuarto.setId(rs.getLong(columnName));
                                    break;
                            case "id_quarto":
                                quarto.setId(rs.getLong(columnName));
                                    break;
                            case "id_servico":
                                servico.setId(rs.getLong(columnName));
                                    break;
                            case "data_associacao":
                                servicoQuarto.setDataAssociacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_situacao":
                                servicoQuarto.setDataSituacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "situacao":
                                servicoQuarto.setSituacao(Situacao.ServicoQuarto.obterViaString(rs.getString(columnName)));
                                    break;
                            case "servico_descricao":
                                servico.setDescricao(rs.getString(columnName));
                                    break;
                            case "servico_situacao":
                                servico.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                            case "quarto_descricao":
                                quarto.setDescricao(rs.getString(columnName));
                                    break;
                            case "quarto_situacao":
                                quarto.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                    
                    servicoQuarto.setQuarto(quarto);
                    servicoQuarto.setServico(servico);
                }
            }
            
            result = new Result.Success<>(servicoQuarto);
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}