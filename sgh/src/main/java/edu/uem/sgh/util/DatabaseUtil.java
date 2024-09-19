/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseUtil {
    private static final String LOCAL_DRIVER_NAME = "SQLite JDBC";
    
    public static double fileSizeInMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }
    
    public static void initializeLocalDatabase(Connection connection) throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        if (!metaData.getDriverName().equals(LOCAL_DRIVER_NAME)) return;
        
        
    }
    
    public static void closeConnection(Connection connection) throws Exception {
        if (!connection.getAutoCommit()) connection.commit();
        connection.close();
    }
}