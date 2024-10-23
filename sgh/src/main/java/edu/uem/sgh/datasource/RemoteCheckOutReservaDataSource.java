/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.CheckOut;
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
public class RemoteCheckOutReservaDataSource extends AbstractDataSource {
    private final String tblName;

    public RemoteCheckOutReservaDataSource(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }
    
    public Result<List<CheckOut.Reserva>> obterTodos() {
        Result<List<CheckOut.Reserva>> result;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT check_out_reserva.id, check_out_reserva.data_check_out, check_out_reserva.id_funcionario AS funcionario_check_out_id, funcionario_check_out.nome AS funcionario_check_out_nome, funcionario_check_out.num_bilhete_id AS funcionario_check_out_num_bilhete_id, check_out_reserva.valorPago AS check_out_valor_pago, chck_in_rsv.id AS check_in_id, chck_in_rsv.data_check_in AS check_in_reserva_data_check_in, chck_in_rsv.id_funcionario AS funcionario_check_in_id, funcionario_check_in.nome AS funcionario_check_in_nome, funcionario_check_in.num_bilhete_id as funcionario_check_in_num_bilhete_id, funcionario_reserva.nome AS funcionario_reserva_nome, funcionario_reserva.id AS funcionario_reserva_id, funcionario_reserva.num_bilhete_id AS funcionario_reserva_num_bilhete_id, r.id AS reserva_id, r.data_check_in AS reserva_data_check_in, r.data_reserva AS data_reserva, r.data_check_out AS reserva_data_check_out, r.valor_pago AS reserva_valor_pago, r.valor_total AS reserva_valor_total, h.id AS cliente_id, h.nome AS cliente_nome, h.num_doc_id AS cliente_num_bilhete_id FROM check_out_reserva JOIN check_in_reserva chck_in_rsv ON chck_in_rsv.id = check_out_reserva.id_check_in JOIN funcionario funcionario_check_out ON funcionario_check_out.id = check_out_reserva.id_funcionario JOIN funcionario funcionario_check_in ON chck_in_rsv.id_funcionario = funcionario_check_in.id JOIN funcionario funcionario_reserva ON chck_in_rsv.id_funcionario = funcionario_reserva.id JOIN reserva r ON chck_in_rsv.id_reserva = r.id JOIN hospede h ON r.id_cliente = h.id")) {
            ResultSet rs = statement.executeQuery();
            List<CheckOut.Reserva> checkOuts = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if (columnCount != 0) {
                while (rs.next()) {
                    CheckOut.Reserva checkOutReserva = new CheckOut.Reserva();
                    CheckIn.Reserva checkInReserva = new CheckIn.Reserva();
                    Reserva reserva = new Reserva();
                    Hospede cliente = new Hospede();
                    Funcionario funcionarioReserva = new Funcionario(), funcionarioCheckIn = new Funcionario(), funcionarioCheckOut = new Funcionario();
                    Pagamento pagamento = new Pagamento();
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id":
                                checkOutReserva.setId(rs.getLong(columnName));
                                    break;
                            case "check_out_valor_pago":
                                checkOutReserva.setValorPago(rs.getDouble(columnName));
                                    break;
                            case "data_check_out":
                                checkOutReserva.setDataCheckOut(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "funcionario_check_out_id":
                                funcionarioCheckOut.setId(rs.getLong(columnName));
                                    break;
                            case "funcionario_check_out_nome":
                                funcionarioCheckOut.setNome(rs.getString(columnName));
                                    break;
                            case "funcionario_check_out_num_bilhete_id":
                                funcionarioCheckOut.setNumBilheteIdentidade(rs.getString(columnName));
                                    break;
                            case "valor_pago":
                                checkOutReserva.setValorPago(rs.getDouble(columnName));
                                    break;
                            case "check_in_id": 
                                checkInReserva.setId(rs.getLong(columnName));
                                    break;
                            case "check_in_reserva_data_check_in":
                                checkInReserva.setDataCheckIn(rs.getTimestamp(columnName).getTime());
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
                            case "reserva_valor_total":
                                pagamento.setValorTotal(rs.getDouble(columnName));
                                    break;
                            case "reserva_valor_pago":
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
                            case "funcionario_check_in_num_bilhete_id":
                                funcionarioCheckIn.setNumBilheteIdentidade(rs.getString(columnName));
                                    break;
                            case "funcionario_reserva_num_bilhete_id":
                                funcionarioReserva.setNumBilheteIdentidade(rs.getString(columnName));
                                    break;
                            case "cliente_nome":
                                cliente.setNome(rs.getString(columnName));
                                    break;
                            case "cliente_num_bilhete_id":
                                cliente.setNumDocumentoIdentidade(rs.getString(columnName));
                                    break;
                        }
                    }
                  System.out.println(reserva.getId());
                    reserva.setFuncionario(funcionarioReserva);
                    reserva.setPagamento(pagamento);
                    reserva.setCliente(cliente);
                    checkInReserva.setFuncionario(funcionarioCheckIn);
                    checkInReserva.setReserva(reserva);
                    checkOutReserva.setCheckIn(checkInReserva);
                    checkOutReserva.setResponsavel(funcionarioCheckOut);
                    checkOuts.add(checkOutReserva);
                }
            }
                                
            result = new Result.Success<>(checkOuts);   
        } catch(SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }

    public Result<List<CheckOut.Reserva>> obterCheckOutsPorFuncionario(long idFuncionario) {
        Result<List<CheckOut.Reserva>> result = null;
        
        return result;
    }
    
    public Result<List<CheckOut.Reserva>> obterCheckOutsPorHospede(long idHospede) {
        Result<List<CheckOut.Reserva>> result = null;
        
        return result;
    }

    public Result<CheckOut.Reserva> obterCheckOutPorIdCheckIn(int idCheckIn) {
        Result<CheckOut.Reserva> result;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            CheckOut.Reserva checkOut = null;
            
            while (rs.next()) {
                checkOut = new CheckOut.Reserva();
                
                for (int i = 1 ; i <= columnCount ; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    
                    switch (columnName) {
                        case "id": 
                            break;
                    }
                }
            }
            
            result = new Result.Success<>(checkOut);
            
        } catch (SQLException e) {
            result = new Result.Error<>(e);
        }
        
        return result;
    }
}