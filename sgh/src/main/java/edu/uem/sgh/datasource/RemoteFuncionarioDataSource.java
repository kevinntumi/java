/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Gerente;
import edu.uem.sgh.model.Result;
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
public class RemoteFuncionarioDataSource extends AbstractDataSource {
    private final String tblName;
    
    public RemoteFuncionarioDataSource(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }
    
    public Result<Boolean> inserirFuncionario(edu.uem.sgh.schema.Funcionario funcionario) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO " + tblName + "(nome, data_nascimento, data_registo, num_bilhete_id, morada, num_telefone, email, id_gerente) VALUES(?,?,?,?,?,?,?,?,?)")) {
            statement.setString(1, funcionario.getNome());
            statement.setDate(2, new java.sql.Date(funcionario.getDataNascimento()));
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            statement.setString(4, funcionario.getNumBilheteIdentidade());
            statement.setString(5, funcionario.getMorada());
            statement.setInt(6, funcionario.getNumTelefone());
            statement.setString(7, funcionario.getEmail());
            statement.setLong(8, funcionario.getIdGerente());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }

    public Result<Boolean> editarFuncionario(Funcionario funcionario) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE " + tblName + " SET nome = ?, data_nascimento = ?, data_registo = ?, num_bilhete_id = ?, morada = ?, num_telefone = ?, email = ?  WHERE id = ?")){
            statement.setString(1, funcionario.getNome());
            statement.setDate(2, new java.sql.Date(funcionario.getDataNascimento()));
            statement.setDate(3, new java.sql.Date(funcionario.getDataRegisto()));
            statement.setString(4, funcionario.getNumBilheteIdentidade());
            statement.setString(5, funcionario.getMorada());
            statement.setInt(6, funcionario.getNumTelefone());
            statement.setString(7, funcionario.getEmail());
            statement.setLong(8, funcionario.getId());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<List<Funcionario>> obterFuncionariosPorGerente(Long idGerente) {
        Result<List<Funcionario>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName + " WHERE id_gerente = ?")){
            statement.setLong(1, idGerente);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            List<Funcionario> funcionarios = new ArrayList<>();
            int columnCount = resultSetMetaData.getColumnCount();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                Gerente gerente = new Gerente(idGerente);
                
                for (int i = 1 ; i <= columnCount ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);
                    
                    switch (nomeColuna) {
                        case "id":
                            funcionario.setId(rs.getLong(nomeColuna));
                                break;
                        case "nome":
                            funcionario.setNome(rs.getString(nomeColuna));
                                break;
                        case "data_nascimento":
                            funcionario.setDataNascimento(rs.getDate(nomeColuna).getTime());
                                break;
                        case "data_registo":
                            funcionario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;  
                        case "morada":
                            funcionario.setMorada(rs.getString(nomeColuna));
                                break;
                        case "num_bilhete_id":
                            funcionario.setNumBilheteIdentidade(rs.getString(nomeColuna));
                                break;
                        case "num_telefone":
                            funcionario.setNumTelefone(rs.getInt(nomeColuna));
                                break;
                        case "email":
                            funcionario.setEmail(rs.getString(nomeColuna));
                                break;
                    }
                }
                
                funcionario.setGerente(gerente);
                funcionarios.add(funcionario);
            }
            
            r = new Result.Success<>(funcionarios);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }   

    public Result<List<Funcionario>> obterTodosFuncionarios() {
        Result<List<Funcionario>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT funcionario.*, gerente.nome AS gerente_nome, gerente.num_doc_id AS gerente_num_bilhete_identidade FROM " + tblName + " JOIN gerente ON gerente.id = funcionario.id_gerente")) {
            ResultSet rs = statement.executeQuery();
            List<Funcionario> funcionarios = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                Gerente gerente = new Gerente();
                
                for (int i = 1 ; i <= columnCount ; i++) {
                    String nomeColuna = resultSetMetaData.getColumnName(i);
                    
                    switch (nomeColuna) {
                        case "id":
                            funcionario.setId(rs.getLong(nomeColuna));
                                break;
                        case "nome":
                            funcionario.setNome(rs.getString(nomeColuna));
                                break;
                        case "data_nascimento":
                            funcionario.setDataNascimento(rs.getDate(nomeColuna).getTime());
                                break;
                        case "data_registo":
                            funcionario.setDataRegisto(rs.getDate(nomeColuna).getTime());
                                break;  
                        case "morada":
                            funcionario.setMorada(rs.getString(nomeColuna));
                                break;
                        case "num_bilhete_id":
                            funcionario.setNumBilheteIdentidade(rs.getString(nomeColuna));
                                break;
                        case "num_telefone":
                            funcionario.setNumTelefone(rs.getInt(nomeColuna));
                                break;
                        case "email":
                            funcionario.setEmail(rs.getString(nomeColuna));
                                break;
                        case "id_gerente":
                            gerente.setId(rs.getLong(nomeColuna));
                                break;
                        case "gerente_nome":
                            gerente.setNome(rs.getString(nomeColuna));
                                break;
                        case "situacao":
                            funcionario.setFuncionarioSituacao(FuncionarioSituacao.obterViaString(rs.getString(nomeColuna)));
                                break;
                    }
                }
                
                funcionario.setGerente(gerente);
                funcionarios.add(funcionario);
            }
            
            r = new Result.Success<>(funcionarios);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }    
}