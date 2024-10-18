/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Gerente;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.repository.AbstractRepository;
import java.sql.Connection;
import java.sql.Date;
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
public abstract class AbstractServicoRepository extends AbstractRepository {
    private final String tblName;
            
    AbstractServicoRepository(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }
    
    public Result<Boolean> add(edu.uem.sgh.schema.Servico servico){
        if (servico == null)
            return new Result.Error<>(new NullPointerException());
        
        Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO " + tblName + " (descricao, situacao, id_gerente, data_registo) VALUES(?,?,?,?)")){
            statement.setString(1, servico.getDescricao());
            statement.setString(2, servico.getSituacao());
            statement.setLong(3, servico.getIdGerente());
            statement.setDate(4, new Date(System.currentTimeMillis()));
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Boolean> edit(edu.uem.sgh.schema.Servico servico) {
        if (servico == null)
            return new Result.Error<>(new NullPointerException());
        
        Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE " + tblName + " SET descricao = ? AND situacao = ? WHERE id = ?")){
            statement.setString(1, servico.getDescricao());
            statement.setString(2, servico.getSituacao());
            statement.setLong(3, servico.getId());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
    
        return r; 
    }
    
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE " + tblName + " SET status = ? WHERE id = ?")){
            statement.setBoolean(1, delete);
            statement.setLong(2, id);
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
    
        return r;
    }
    
    public Result<List<Servico>> getAll() {
        Result<List<Servico>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT servico.*, gerente.nome AS nome, gerente.num_doc_id AS num_bilhete_identidade FROM servico JOIN gerente ON servico.id_gerente = gerente.id")){
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<Servico> servicos = new ArrayList<>();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    Servico servico = new Servico();
                    servico.setGerente(new Gerente());
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                       
                        switch (columnName) {
                            case "id": 
                                servico.setId(rs.getLong(columnName));
                                    break;
                            case "descricao":
                                servico.setDescricao(rs.getString(columnName));
                                    break;
                            case "data_registo":
                                servico.setDataRegisto(rs.getDate(columnName).getTime());
                                    break;
                            case "id_gerente":
                                servico.getGerente().setId(rs.getLong(columnName));
                                    break;
                            case "nome":
                                servico.getGerente().setNome(rs.getString(columnName));
                                    break;
                            case "num_bilhete_identidade":
                                servico.getGerente().setNumBilheteIdentidade(rs.getString(columnName));
                                    break;
                            case "situacao": 
                                servico.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                    
                    servicos.add(servico);
                }
            }
            
            rs.close();
            r = new Result.Success<>(servicos);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Servico> get(long id) {
        Result<Servico> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName + " WHERE id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            Servico servico = null;
            
            if (columnCount != 0) {
                while (rs.next()) {
                    servico = new Servico();
                    servico.setGerente(new Gerente());
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                        
                        switch (columnName) {
                            case "id": 
                                servico.setId(rs.getLong(columnName));
                                    break;
                            case "descricao":
                                servico.setDescricao(rs.getString(columnName));
                                    break;
                            case "data_registo":
                                servico.setDataRegisto(rs.getDate(columnName).getTime());
                                    break;
                            case "id_gerente":
                                servico.getGerente().setId(rs.getLong(columnName));
                                    break;
                            case "gerente_nome":
                                servico.getGerente().setNome(rs.getString(columnName));
                                    break;
                            case "gerente_sexo":
                                servico.getGerente().setSexo(rs.getString(columnName).charAt(0));
                                    break;
                            case "gerente_num_telefone":
                                servico.getGerente().setNumTelefone(rs.getInt(columnName));
                                    break;
                            case "gerente_num_bilhete_identidade":
                                servico.getGerente().setNumBilheteIdentidade(rs.getString(columnName));
                                    break;
                            case "gerente_data_nascimento":
                                servico.getGerente().setDataNascimento(rs.getDate(columnName).getTime());
                                    break;
                            case "situacao": 
                                servico.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                }
            }
            
            r = new Result.Success<>(servico);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}