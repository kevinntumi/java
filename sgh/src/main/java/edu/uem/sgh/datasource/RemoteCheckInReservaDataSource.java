/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.CheckIn;
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
public class RemoteCheckInReservaDataSource extends AbstractDataSource {
    public RemoteCheckInReservaDataSource(Connection connection) {
        super(connection);
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Boolean> efectuar(CheckIn.Reserva checkInReserva) {
        Result<Boolean> r;
        
        String sql = "INSERT INTO check_in_reserva(num_reserva, num_funcionario, data_check_in) VALUES(?,?,?)";
      
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, checkInReserva.getReserva().getId());
            statement.setLong(2, checkInReserva.getFuncionario().getId());  
            statement.setDate(3, new java.sql.Date(checkInReserva.getDataCheckIn()));
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
   
        return r;
    }

    public Result<List<CheckIn.Reserva>> obterTodos() {
        Result<List<CheckIn.Reserva>> r;
  
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT check_ins.num_reserva AS num_reserva, check_ins.num_check_in AS num_check_in, check_ins.num_cliente AS num_cliente, check_ins.num_funcionario AS num_funcionario, check_ins.data_check_in AS data_check_in, check_ins.data_check_out AS data_check_out, funcionarios.nome AS nome_funcionario, reservas.valor_pago AS valor_pago, reservas.valor_total AS valor_total, hospedes.nome AS nome_cliente FROM check_ins JOIN funcionarios ON check_ins.num_funcionario = funcionarios.num_funcionario JOIN hospedes ON check_ins.num_cliente = hospedes.num_hospede JOIN reservas ON check_ins.num_reserva = reservas.num_reserva")) {
            ResultSet rs = statement.executeQuery();
            List<CheckIn.Reserva> checkIns = new ArrayList<>();

            while (rs.next()) {
                
            }
            
            rs.close();
            r = new Result.Success<>(checkIns);   
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}