/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Passagem;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemotePassagemDataSource extends AbstractDataSource {    
    public RemotePassagemDataSource(Connection connection) {
        super(connection);
    }
    
    public Result<Boolean> inserirPassagem(Passagem passagem) {
        Result<Boolean> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO passagem(situacao, id_tipo, tipo, data_inicio, data_fim) VALUES(?,?,?,?,?)")){
            statement.setString(1, FuncionarioSituacao.obterPorValor(passagem.getSituacao()));
            statement.setLong(2, passagem.getIdTipo());
            statement.setString(3, Usuario.Tipo.converterTipoParaString(passagem.getTipo()));
            statement.setTimestamp(4, new java.sql.Timestamp(passagem.getDataInicio()));
            statement.setTimestamp(5, null);
            result = new Result.Success<>(statement.executeUpdate() > 0);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<Boolean> editarPassagem(Passagem passagem) {
        Result<Boolean> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE passagem SET situacao = ?, data_fim = ? WHERE id = ?")){
            statement.setString(1, FuncionarioSituacao.obterPorValor(passagem.getSituacao()));
            statement.setTimestamp(2, (passagem.getSituacao() == FuncionarioSituacao.DEMITIDO) ? new java.sql.Timestamp(System.currentTimeMillis()) : null);
            statement.setLong(3, passagem.getId());
            result = new Result.Success<>(statement.executeUpdate() > 0);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
    
    public Result<List<Passagem>> obterPassagensPorFuncionario(Long idFuncionario) {
        Result<List<Passagem>> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE passagem SET situacao = ?, data_fim = ? WHERE id = ?")){
            statement.setLong(1, idFuncionario);
            List<Passagem> passagens = new ArrayList<>();
            
            result = new Result.Success<>(passagens);
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}