/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseUtil {
    private static final String LOCAL_DRIVER_NAME = "SQLite JDBC";
    private static final int MAXIMUM_BATCH_ATTEMPTS = 5;
    
    public static void initLocalDatabase(Connection localConnection) throws Exception {
        DatabaseMetaData metaData = localConnection.getMetaData();
        
        if (!LOCAL_DRIVER_NAME.equals(metaData.getDriverName())) return;
        
        ResultSet tablesResultSet = obterTodasTabelas(metaData);
        
        if (tablesResultSet == null || temTabelas(tablesResultSet)) return;
        
        List<String> tableSchemas = SQLiteUtil.getTableSchemas(ClassUtil.getJavaClassesInFolder(Path.SQLITE_SCHEMA_FOLDER_PATH));
        
        try {
            Statement statement = localConnection.createStatement();
            
            for (String tableSchema : tableSchemas) {
                statement.addBatch(tableSchema);
            }
            
            int[] statementsResult = statement.executeBatch();
            
            if (wasBatchSucessful(statementsResult)) return;
            
            statement.clearBatch();
            
            if (wereRemainingStatementsExecuted(0, statementsResult, tableSchemas, statement)) {
                System.out.println("Sim");
            } else {
                System.out.println("Nao");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    static ResultSet obterTodasTabelas(DatabaseMetaData metaData) throws Exception {
        return metaData.getTables(null, null, null, new String[]{
            "TABLE"
        });
    }
    
    static boolean temTabelas(ResultSet tableResultSet) throws SQLException {
        int totalTabelas = 0;
        
        try {
            while (!tableResultSet.isClosed() && tableResultSet.next()) {
                totalTabelas++;
            }
        } catch (SQLException e) {
            return false;
        }

        return totalTabelas > 0;
    }
    
    static boolean wereRemainingStatementsExecuted(int attempts, int[] statementsResult, List<String> tableSchemas, Statement statement) throws Exception {
        if (!(attempts <= MAXIMUM_BATCH_ATTEMPTS)) return false;
        
        for (int i = 0 ; i < tableSchemas.size() ; i++) {
            if (isStatementSucessful(statementsResult[i])) continue;
            statementsResult[i] = statement.executeUpdate(tableSchemas.get(i));
        }
        
        if (wasBatchSucessful(statementsResult)) return true;
        return wereRemainingStatementsExecuted(attempts + 1, statementsResult, tableSchemas, statement);
    }
    
    static boolean isStatementSucessful(int statementResultDescriptionCode) {
        return statementResultDescriptionCode >= 0 || statementResultDescriptionCode == Statement.SUCCESS_NO_INFO;
    }
    
    static boolean wasBatchSucessful(int[] statementResult) {
        for (int i = 0 ; i < statementResult.length ; i++) {
            if (!(statementResult[i] >= 0 || statementResult[i] == Statement.SUCCESS_NO_INFO)) return false;
        }
        
        return true;
    }
    
    public static void closeConnection(Connection connection) throws Exception {
        if (!connection.isClosed() && !connection.getAutoCommit()) connection.commit();
        connection.close();
    }
}