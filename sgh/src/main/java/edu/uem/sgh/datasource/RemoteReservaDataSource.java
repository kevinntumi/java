/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.helper.ReservaSituacao;
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
public class RemoteReservaDataSource extends AbstractDataSource{
    public RemoteReservaDataSource(Connection connection) {
        super(connection);
    }
    
    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public Result<Boolean> inserirReserva(int numCliente, int numFuncionario, long dataCheckIn, long dataCheckOut, double valorPago, double valorTotal) {
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO reservas(num_cliente, data_check_in, data_check_out, data_confirmada, data_reserva, num_funcionario, valor_total, valor_pago) VALUES(?,?,?,?,?,?,?,?)")){
            statement.setInt(1, numCliente);
            statement.setDate(2, new java.sql.Date(dataCheckIn));
            statement.setDate(3, new java.sql.Date(dataCheckOut));
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            statement.setInt(6, numFuncionario);
            statement.setDouble(7, valorTotal);
            statement.setDouble(8, valorPago);
            r = new Result.Success<>(statement.executeUpdate() > 0); 
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }

    public Result<Boolean> editarReserva(Long dataSituacao, ReservaSituacao reservaSituacao, int id) {
        if (dataSituacao == null || reservaSituacao == null || id < 0) {
            return new Result.Error<>(new Exception());
        }
        
        Result<Boolean> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE reserva SET data_situacao = ? WHERE id = ?")){
            if (reservaSituacao != ReservaSituacao.POR_CONFIRMAR) {
                throw new SQLException();
            }
            
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            statement.setInt(2, id);
            r = new Result.Success<>(statement.executeUpdate() > 0);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }

    public Result<List<Reserva>> obterTodasReservas() {
        Result<List<Reserva>> r;
        
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT reserva.*, funcionario.nome AS funcionario_nome, hospede.nome AS cliente_nome FROM reserva JOIN funcionario ON reserva.id_funcionario = funcionario.id JOIN hospede ON reserva.id_cliente = hospede.id")){
            ResultSet rs = statement.executeQuery();
            List<Reserva> reservas = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            
            if (columnCount != 0) {
                while (rs.next()) {
                    Reserva reserva = new Reserva();
                    Hospede cliente = new Hospede();
                    Funcionario funcionario = new Funcionario();
                    Pagamento pagamento = new Pagamento();
                    
                    for (int i = 1 ; i <= columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnLabel(i);
                        
                        switch (columnName) {
                            case "id": 
                                reserva.setId(rs.getLong(columnName));
                                    break;
                            case "id_cliente":
                                cliente.setId(rs.getLong(columnName));
                                    break;
                            case "data_reserva":
                                reserva.setDataReserva(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_situacao":
                                reserva.setDataSituacao(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_check_in":
                                reserva.setDataCheckIn(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "data_check_out":
                                reserva.setDataCheckOut(rs.getTimestamp(columnName).getTime());
                                    break;
                            case "valor_total":
                                pagamento.setValorTotal(rs.getDouble(columnName));
                                    break;
                            case "valor_pago":
                                pagamento.setValorPago(rs.getDouble(columnName));
                                    break;
                            case "id_funcionario":
                                funcionario.setId(rs.getLong(columnName));
                                    break;
                            case "funcionario_nome":
                                funcionario.setNome(rs.getString(columnName));
                                    break;
                            case "cliente_nome":
                                cliente.setNome(rs.getString(columnName));
                                    break;
                            case "situacao": 
                                reserva.setSituacao(ReservaSituacao.obterViaString(rs.getString(columnName)));
                                    break;
                        }
                    }
                    
                    reserva.setFuncionario(funcionario);
                    reserva.setPagamento(pagamento);
                    reserva.setCliente(cliente);
                    reservas.add(reserva);
                }
            }
            
            r = new Result.Success<>(reservas);
        } catch(SQLException e) {
            r = new Result.Error<>(e);
        }
        
        return r;
    }
}