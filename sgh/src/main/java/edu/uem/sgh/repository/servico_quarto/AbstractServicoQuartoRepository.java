/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico_quarto;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.ServicoQuarto;
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
public class AbstractServicoQuartoRepository extends AbstractRepository{
    public AbstractServicoQuartoRepository(Connection connection) {
        super(connection);
    }
    
    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Boolean> add(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
      Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO servico_quarto(id_servico, id_quarto, status) VALUES(?,?,?)")){
            statement.setLong(1, servicoQuarto.getIdServico());
            statement.setLong(2, servicoQuarto.getIdQuarto());
            statement.setBoolean(3, true);
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Boolean> edit(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
        Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE servico_quarto SET id_servico = ?, id_quarto = ? WHERE id = ?")){
            statement.setLong(1, servicoQuarto.getIdServico());
            statement.setLong(2, servicoQuarto.getIdQuarto());
            statement.setLong(3, servicoQuarto.getId());
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
    
        return r;
    }
    
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE servico_quarto SET status = ? WHERE id = ?")){
            statement.setBoolean(1, delete);
            statement.setLong(2, id);
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
    
        return r;
    }
    
    public Result<List<ServicoQuarto>> getAll(){
        Result<List<ServicoQuarto>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM servico_quarto")){
            ResultSet rs = statement.executeQuery();  
            List<ServicoQuarto> servicosQuartos = new ArrayList<>();
            
            while (rs.next()) {
    //            Servico servico = new Servico(rs.getLong("id_servico"), rs.getString("descricao_servico"), rs.getBoolean("status_servico"));
//                Quarto quarto = new Quarto(rs.getLong("id_quarto"), rs.getString("descricao_quarto"), rs.getInt("capacidade"), rs.getBlob("foto"), rs.getDouble("preco"), rs.getBoolean("status_quarto"));
  //              servicosQuartos.add(new ServicoQuarto(rs.getByte("id_servico_quarto"), servico, quarto));
            }
            
            r = new Result.Success<>(servicosQuartos);
            rs.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<ServicoQuarto> get(long id) {
        Result r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT id_servico, id_quarto, status FROM servico_quarto WHERE id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            
            ServicoQuarto servicoQuarto = null;
            
            while (rs.next()) {
         //       Servico servico = new Servico(rs.getLong("id_servico"), rs.getString("descricao_servico"), rs.getBoolean("status_servico"));
           //     Quarto quarto = new Quarto(rs.getLong("id_quarto"), rs.getString("descricao_quarto"), rs.getInt("capacidade"), rs.getBlob("foto"), rs.getDouble("preco"), rs.getBoolean("status_quarto"));
             //   servicoQuarto = new ServicoQuarto(id, servico, quarto);
            }
            
            r = new Result.Success<>(servicoQuarto);
            rs.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}