/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Pagamento;
import edu.uem.sgh.model.Reserva;
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
        
        String sql = "INSERT INTO check_in_reserva(id_reserva, id_funcionario, data_check_in) VALUES(?,?,?)";
      
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, checkInReserva.getReserva().getId());
            statement.setLong(2, checkInReserva.getFuncionario().getId());  
            statement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch (SQLException e) {
            r = new Result.Error<>(e);
        }
   
        return r;
    }

    public Result<List<CheckIn.Reserva>> obterTodos() {
        Result<List<CheckIn.Reserva>> r;
  
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT check_in_reserva.id, check_in_reserva.data_check_in, check_in_reserva.id_funcionario AS funcionario_check_in_id, funcionario_check_in.nome AS funcionario_check_in_nome, funcionario_reserva.nome AS funcionario_reserva_nome, funcionario_reserva.id AS funcionario_reserva_id, r.id AS reserva_id, r.data_check_in AS reserva_data_check_in, r.data_reserva AS data_reserva, r.data_check_out AS reserva_data_check_out, r.valor_pago AS valor_pago, r.valor_total AS valor_total, h.id AS cliente_id, h.nome AS cliente_nome FROM check_in_reserva JOIN funcionario funcionario_reserva ON check_in_reserva.id_funcionario = funcionario_reserva.id JOIN funcionario funcionario_check_in ON check_in_reserva.id_funcionario = funcionario_check_in.id JOIN reserva r ON check_in_reserva.id_reserva = r.id JOIN hospede h ON r.id_cliente = h.id")) {
            ResultSet rs = statement.executeQuery();
            List<CheckIn.Reserva> checkIns = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if (columnCount != 0) {
                while (rs.next()) {
                    CheckIn.Reserva checkInReserva = new CheckIn.Reserva();
                    Reserva reserva = new Reserva();
                    Hospede cliente = new Hospede();
                    Funcionario funcionarioReserva = new Funcionario(), funcionarioCheckIn = new Funcionario();
                    Pagamento pagamento = new Pagamento();
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id": 
                                checkInReserva.setId(rs.getLong(columnName));
                                    break;
                            case "id_cliente":
                                cliente.setId(rs.getLong(columnName));
                                    break;
                            case "data_reserva":
                                reserva.setDataReserva(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_check_in":
                                checkInReserva.setDataCheckIn(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "reserva_id":
                                reserva.setId(rs.getLong(columnName));
                                    break;
                            case "reserva_data_check_out":
                                reserva.setDataCheckOut(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "reserva_data_check_in":
                                reserva.setDataCheckIn(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "valor_total":
                                pagamento.setValorTotal(rs.getDouble(columnName));
                                    break;
                            case "valor_pago":
                                pagamento.setValorPago(rs.getDouble(columnName));
                                    break;
                            case "funcionario_reserva_id":
                                funcionarioReserva.setId(rs.getLong(columnName));
                                    break;
                            case "funcionario_reserva_nome":
                                funcionarioReserva.setNome(rs.getString(columnName));
                                    break;
                            case "funcionario_check_in_id":
                                funcionarioCheckIn.setId(rs.getLong(columnName));
                                    break;
                            case "funcionario_check_in_nome":
                                funcionarioCheckIn.setNome(rs.getString(columnName));
                                    break;
                            case "cliente_nome":
                                cliente.setNome(rs.getString(columnName));
                                    break;
                        }
                    }
                    
                    reserva.setFuncionario(funcionarioReserva);
                    reserva.setPagamento(pagamento);
                    reserva.setCliente(cliente);
                    checkInReserva.setFuncionario(funcionarioCheckIn);
                    checkInReserva.setReserva(reserva);
                    checkIns.add(checkInReserva);
                }
            }
                                
            r = new Result.Success<>(checkIns);   
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}