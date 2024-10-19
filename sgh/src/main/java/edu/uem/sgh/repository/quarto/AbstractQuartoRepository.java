/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.quarto;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.repository.AbstractRepository;
import java.io.FileInputStream;
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
public abstract class AbstractQuartoRepository extends AbstractRepository {
    public AbstractQuartoRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Boolean> add(Quarto quarto, FileInputStream fis) {
        Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO quartos(descricao, capacidade, foto, preco, situacao) VALUES(?,?,?,?,?)")){
            statement.setString(1, quarto.getDescricao());
            statement.setInt(2, quarto.getCapacidade());
            statement.setBlob(3, fis);
            statement.setDouble(4, quarto.getPreco());
            statement.setString(5, ServicoSituacao.obterPorValor(quarto.getSituacao()));
            r = new Result.Success<>(statement.executeUpdate() > 0);
            statement.close();
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Boolean> edit(Quarto t, FileInputStream fis) {
        Result<Boolean> r;

        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE quartos SET descricao = ?, capacidade = ?, foto = ?, preco = ?, situacao = ? WHERE id = ?")){
            statement.setString(1, t.getDescricao());
            statement.setInt(2, t.getCapacidade());
            statement.setBlob(3, fis);
            statement.setDouble(4, t.getPreco());
            statement.setString(5, ServicoSituacao.obterPorValor(t.getSituacao()));
            statement.setLong(6, t.getId());
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
    
    public Result<List<Quarto>> getAll(){
        Result<List<Quarto>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM quarto")){
            ResultSet rs = statement.executeQuery();  
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<Quarto> quartos = new ArrayList<>();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    Quarto quarto = new Quarto();
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                        
                        switch (columnName) {
                            case "id":
                                quarto.setId(rs.getLong(columnName));
                                    break;
                            case "descricao":
                                quarto.setDescricao(rs.getString(columnName));
                                    break;
                            case "foto":
                                quarto.setFoto(rs.getBlob(columnName));
                                    break;  
                            case "preco":
                                quarto.setPreco(rs.getDouble(columnName));
                                    break;
                            case "situacao":
                                quarto.setSituacao(ServicoSituacao.obterViaString(rs.getString(columnName)));
                                    break;  
                            case "capacidade":
                                quarto.setCapacidade(rs.getInt(columnName));
                                    break;
                        }
                    }
                    
                    quartos.add(quarto);
                }
            }
            
            r = new Result.Success<>(quartos);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
    
    public Result<Quarto> get(long id) {
        Result r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT descricao, capacidade, foto, preco, status FROM quartos WHERE id = ?")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            
            Quarto quarto = null;
            
            while (rs.next()) {
//                quarto = new Quarto(id, rs.getString("descricao"), rs.getInt("capacidade"), rs.getBlob("foto"), rs.getDouble("preco"), rs.getBoolean("status"));
            }
            
            r = new Result.Success<>(quarto);
            rs.close();
            statement.close();
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}