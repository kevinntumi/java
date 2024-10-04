/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.connection;

/**
 *
 * @author Kevin Ntumi
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import edu.uem.sgh.util.DatabaseUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseConnection {
    private final String REMOTE_DATABASE_NAME = "hotel";
    private final String REMOTE_DATABASE_PORT = "3306";
    private final String REMOTE_DATABASE_BASE_ENDPOINT = "localhost:" + REMOTE_DATABASE_PORT;
    private final String REMOTE_DATABASE_URL = "jdbc:mysql://" + REMOTE_DATABASE_BASE_ENDPOINT + "/" + REMOTE_DATABASE_NAME;
    private final String REMOTE_DATABASE_USERNAME = "root";
    private final String REMOTE_DATABASE_PASSWORD = "";
    private final String LOCAL_DATABASE_NAME = "local.db";
    private final String LOCAL_DATABASE_PATH = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + LOCAL_DATABASE_NAME;
    private Connection remoteConnection, localConnection;

    public DatabaseConnection() {
    }
    
    public void initLocalConnection() throws Exception {
        localConnection = DriverManager.getConnection(LOCAL_DATABASE_PATH);
        DatabaseUtil.initializeLocalDatabase(localConnection);
    }
    
    public Connection getRemoteConnection() {
        if (remoteConnection == null) {
            try {
                remoteConnection = DriverManager.getConnection(REMOTE_DATABASE_URL, REMOTE_DATABASE_USERNAME, REMOTE_DATABASE_PASSWORD);
            } catch (SQLException ex) {
                return null;
            }
        }
        
        return remoteConnection;
    }

    public Connection getLocalConnection() {
        return localConnection;
    }
    
    public void closeAllConnections() {
        if (remoteConnection != null) {
            try {
                DatabaseUtil.closeConnection(remoteConnection);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        
        if (localConnection == null) return;
        
        try {
            DatabaseUtil.closeConnection(localConnection);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}