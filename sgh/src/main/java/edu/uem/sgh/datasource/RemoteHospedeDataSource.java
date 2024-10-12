/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteHospedeDataSource extends AbstractDataSource{
    public RemoteHospedeDataSource(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Boolean> inserir(Hospede hospede) {
        Result<Boolean> r;
        String sql = "INSERT INTO hospedes(num_doc_id, nacionalidade, nome, morada, data_nascimento, data_registrado) VALUES(?,?,?,?,?,?)";
        
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, hospede.getNumDocumentoIdentidade());
            statement.setString(2, hospede.getNacionalidade());
            statement.setString(3, hospede.getNome());
            statement.setString(4, hospede.getMorada());
            statement.setDate(5, new java.sql.Date(hospede.getDataNascimento()));
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }

    public Result<Boolean> editar(Hospede hospede) {
        Result<Boolean> r;
        String sql = "UPDATE hospedes SET num_doc_id = ?, nacionalidade = ?, morada = ?, data_nascimento = ? WHERE num_hospede = ?";
        
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, hospede.getNumDocumentoIdentidade());
            statement.setString(2, hospede.getNacionalidade());
            statement.setString(3, hospede.getMorada());
            statement.setDate(4, new java.sql.Date(hospede.getDataNascimento()));
            statement.setLong(5, hospede.getId());
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }

    public Result<Hospede> obterHospedePorNumDocumentoIdentidade(String numDocIdHospede) {
        Result<Hospede> r;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT num_hospede, nacionalidade, nome, morada, idade, data_registrado FROM hospedes WHERE num_doc_id = ?");
            ResultSet rs = statement.executeQuery();   
            Hospede hospede = null;
            
            while (rs.next()) {
                //hospedes.add(new Hospede(rs.getInt("num_hospede"), numDocIdHospede, rs.getString("nacionalidade"), rs.getString("nome"), rs.getString("morada"), rs.getDate("data_nascimento").getTime()));
            }
            
            rs.close();
            statement.close();
            r = new Result.Success<>(hospede);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<List<Hospede>> obterTodos() {
        Result<List<Hospede>> r;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM hospedes");
            ResultSet rs = statement.executeQuery();
            List<Hospede> hospedes = new ArrayList<>();
            
            while (rs.next()) {
                //hospedes.add(new Hospede(rs.getInt("num_hospede"), rs.getString("num_doc_id"), rs.getString("nacionalidade"), rs.getString("nome"), rs.getString("morada"), rs.getDate("data_nascimento").getTime()));
            }
            
            rs.close();
            statement.close();
            r = new Result.Success<>(hospedes);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}
