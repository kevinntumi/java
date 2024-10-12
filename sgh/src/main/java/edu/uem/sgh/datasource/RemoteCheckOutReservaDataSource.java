/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import edu.uem.sgh.model.CheckOut;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteCheckOutReservaDataSource  extends AbstractDataSource {
    private final String tblName;

    public RemoteCheckOutReservaDataSource(Connection connection, String tblName) {
        super(connection);
        this.tblName = tblName;
    }
    
    public Result<List<CheckOut>> obterTodos() {
        Result<List<CheckOut>> result = null;
        
        return result;
    }

    public Result<List<CheckOut>> obterCheckOutsPorFuncionario(long idFuncionario) {
        Result<List<CheckOut>> result = null;
        
        return result;
    }
    
    public Result<List<CheckOut>> obterCheckOutsPorHospede(long idHospede) {
        Result<List<CheckOut>> result = null;
        
        return result;
    }

    public Result<CheckOut> obterCheckOutPorIdCheckIn(int idCheckIn) {
        Result<CheckOut> result = null;
        
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + tblName);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            CheckOut checkOut = null;
            
            while (rs.next()) {
                checkOut = new CheckOut();
                
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