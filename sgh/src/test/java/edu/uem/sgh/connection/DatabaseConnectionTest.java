/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseConnectionTest {
    
    public DatabaseConnectionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of initLocalConnection method, of class DatabaseConnection.
     */
    @Test
    public void testInitLocalConnection() throws Exception {
        System.out.println("initLocalConnection");
        DatabaseConnection instance = new DatabaseConnection();
        instance.initLocalConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemoteConnection method, of class DatabaseConnection.
     */
    @Test
    public void testGetRemoteConnection() {
        System.out.println("getRemoteConnection");
        DatabaseConnection instance = new DatabaseConnection();
        Connection expResult = null;
        Connection result = instance.getRemoteConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocalConnection method, of class DatabaseConnection.
     */
    @Test
    public void testGetLocalConnection() {
        System.out.println("getLocalConnection");
        DatabaseConnection instance = new DatabaseConnection();
        Connection expResult = null;
        Connection result = instance.getLocalConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeAllConnections method, of class DatabaseConnection.
     */
    @Test
    public void testCloseAllConnections() {
        System.out.println("closeAllConnections");
        DatabaseConnection instance = new DatabaseConnection();
        instance.closeAllConnections();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void findRemoteConnectionTableNames() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        
        Connection connection = databaseConnection.getRemoteConnection();
        System.out.println("tsooak " + connection);
        if (connection == null) return;
        
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tablesResultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
        
        while(tablesResultSet.next()) {
            String tableName = tablesResultSet.getString("TABLE_NAME");
            try(ResultSet columns = databaseMetaData.getColumns(null,null, tableName, null)){
                while(columns.next()) {
                  String columnName = columns.getString("COLUMN_NAME");
                  String columnSize = columns.getString("COLUMN_SIZE");
                  String datatype = columns.getString("DATA_TYPE");
                    System.out.println(columnName);
                }
              }
            
            System.out.println();
        }
    }
    
    @Test
    public void testLocalConnectionInitialization() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.initLocalConnection();
    }
    
    @Test
    public void findLocalConnectionTableNames() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        
        Connection connection = databaseConnection.getLocalConnection();
        System.out.println("tsooak " + connection);
        if (connection == null) return;
        
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tablesResultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
        
        while (tablesResultSet.next()) {
            String nomeTabela = tablesResultSet.getString("TABLE_NAME");
            ResultSet columns = databaseMetaData.getColumns(null, null, nomeTabela, null);
            
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                System.out.println(columnName);
            }
              
            System.out.println();
        }
    }
    
}
