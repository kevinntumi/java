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
import edu.uem.sgh.util.Path;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseConnection {
    private Connection remoteConnection, localConnection;
    private static final int MAX_CONNECTION_ATTEMPTS = 5;

    public DatabaseConnection() {
    }
    
    public void initLocalConnection() throws Exception {
        localConnection = DriverManager.getConnection(Path.LOCAL_DATABASE_PATH);
        DatabaseUtil.initLocalDatabase(localConnection);
    }
    
    public void initRemoteConnection() throws Exception {
        remoteConnection = DriverManager.getConnection(Path.REMOTE_DATABASE_URL, Path.REMOTE_DATABASE_USERNAME, Path.REMOTE_DATABASE_PASSWORD);
    }
    
    public Connection getRemoteConnection() {
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
    
    public boolean areAllConnectionsUnnavailable() {
        return isConnectionUnnavailable(getRemoteConnection()) && isConnectionUnnavailable(getLocalConnection());
    }
    
    public boolean isConnectionUnnavailable(Connection connection) {
        if (connection == null) return true;
        
        try {
            if (connection.isClosed()) return true;
        } catch (SQLException e) {
            return true;
        }
        
        return false;
    }
    
    public void attemptInitConnection (int attempt, Connection connection, Type type) {
        if (attempt < 0 || attempt > MAX_CONNECTION_ATTEMPTS) return;
        if (type == null) return; 
        
        try {
            if (type == Type.LOCAL)
                initLocalConnection();
            else
                initRemoteConnection();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void attemptInitConnections() {
        attemptInitConnection(0, localConnection, Type.LOCAL);
        attemptInitConnection(0, remoteConnection, Type.REMOTE);
    }
}