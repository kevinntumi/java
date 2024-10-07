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

    public DatabaseConnection() {
    }
    
    public void initLocalConnection() throws Exception {
        localConnection = DriverManager.getConnection(Path.LOCAL_DATABASE_PATH);
        DatabaseUtil.initLocalDatabase(localConnection);
    }
    
    public Connection getRemoteConnection() {
        if (remoteConnection == null) {
            try {
                remoteConnection = DriverManager.getConnection(Path.REMOTE_DATABASE_URL, Path.REMOTE_DATABASE_USERNAME, Path.REMOTE_DATABASE_PASSWORD);
            } catch (SQLException ex) {
                return null;
            }
        }
        
        return remoteConnection;
    }

    public Connection getLocalConnection() {
        if (localConnection == null) {
            try {
                localConnection = DriverManager.getConnection(Path.LOCAL_DATABASE_PATH);
            } catch(SQLException e) {
                return null;
            }
        }
        
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