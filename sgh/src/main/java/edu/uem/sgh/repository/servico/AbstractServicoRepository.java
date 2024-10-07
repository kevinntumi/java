/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.repository.AbstractRepository;
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
public abstract class AbstractServicoRepository extends AbstractRepository {
    AbstractServicoRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }
    
    public Result<Boolean> add(Servico servico){
         Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO servicos(descricao, status) VALUES(?,?)")){
            statement.setString(1, servico.getDescricao());
//            statement.setBoolean(5, servico.estaEmUso());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Boolean> edit(Servico servico) {
       Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE quartos SET descricao = ?WHERE id = ?")){
            statement.setString(1, servico.getDescricao());
            statement.setLong(2, servico.getId());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
    
        return r; 
    }
    
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE quartos SET status = ? WHERE id = ?")){
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
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM servicos")){
            ResultSet rs = statement.executeQuery();
            List<Servico> servicos = new ArrayList<>();
            
            while (rs.next()) {
//                servicos.add(new Servico(rs.getLong("id"), rs.getString("descricao"), rs.getBoolean("status")));
            }
            
            r = new Result.Success<>(servicos);
            rs.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Servico> get(long id) {
        Result r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT descricao, status FROM quartos WHERE id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            
            Servico servico = null;
            
            while (rs.next()) {
    //            servico = new Servico(id, rs.getString("descricao"), rs.getBoolean("status"));
            }
            
            r = new Result.Success<>(servico);
            rs.close();
            statement.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}