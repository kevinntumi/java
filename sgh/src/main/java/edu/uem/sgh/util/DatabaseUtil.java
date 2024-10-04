/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class DatabaseUtil {
    private static final String LOCAL_DRIVER_NAME = "SQLite JDBC";
    private static final int MAXIMUM_BATCH_ATTEMPTS = 5;
    
    public static double fileSizeInMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }
    
    public static void initializeLocalDatabase(Connection localConnection) throws Exception {
        DatabaseMetaData metaData = localConnection.getMetaData();
        
        if (!LOCAL_DRIVER_NAME.equals(metaData.getDriverName())) return;
        
        ResultSet schemas = metaData.getSchemas();
        int i = 0;
        
        while (schemas.next()) {
            i += 1;
        }
        
        if (i != 0) return;
        
        String url = metaData.getURL();
        List<String> tableSchemas = getTableSchemas(getJavaClassesInFolder(url.substring(12, url.length())));
        
        try (Statement statement = localConnection.createStatement()) {
            for (String tableSchema : tableSchemas) {
                System.out.println(tableSchema);
                //statement.executeUpdate(tableSchema);
            }
            
            int[] statementsResult = statement.executeBatch();
            
            if (wasBatchSucessful(statementsResult)) return;
            
            int attempts = 0;
            
            if (wereRemainingStatementsExecuted(attempts, statementsResult, tableSchemas, statement)) {
                System.out.println("Sim");
            } else {
                System.out.println("Nao");
            }
        }
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
    
    static List<String> getTableSchemas(List<Class> classes) {
        if (classes == null || classes.isEmpty()) return null;
        
        List<String> tableSchemas = new ArrayList<>();
        String tableQuery;
        
        for (Class<?> clazz : classes) {
            
        }
        
        return tableSchemas;
    }
    
    public static String getSQLiteTableName(String className) {
        char classNameFirstCharacter = className.charAt(0); 
        String sqLiteTableName = (Character.isUpperCase(classNameFirstCharacter) ? Character.toLowerCase(classNameFirstCharacter) : classNameFirstCharacter) + "";
        char[] characters = className.toCharArray();
        
        for (int i = 1 ; i < characters.length ; i++) {
            char character = characters[i];
            sqLiteTableName += (Character.isUpperCase(character)) ? "_" + Character.toLowerCase(character) : character;
        }
        
        return sqLiteTableName;
    }
    
    private static String getSQLiteColumnTypeName(String typeName) throws RuntimeException {
        switch(typeName) {
            case "java.lang.Long":
            case "Long":
            case "long":
            case "java.lang.Integer": 
            case "Integer":
            case "int":
                return "INTEGER";
            case "java.lang.String":
            case "String":
                return "TEXT";
            case "java.lang.Double":
            case "Double":
            case "double":
                return "REAL";
            case "java.sql.Blob":
            case "Blob": 
                return "BLOB";
            case "java.lang.Boolean":
            case "Boolean":
            case "boolean":
                return "BOOLEAN";
        }
        
        throw new RuntimeException();
    }
    
    private static String getSQLiteColumnName(String fieldName) {        
        String sqLiteColumnName = "";
        char[] characters = fieldName.toCharArray();
        
        for (char character : characters) {
            sqLiteColumnName += (Character.isUpperCase(character)) ? "_" + Character.toLowerCase(character) : character;
        }
        
        return sqLiteColumnName;
    }
    
    public static List<Field> getFieldsNameByClass(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        
        if (declaredFields.length == 0) return fields;
        
        for (Field field : declaredFields) {
            if (!Modifier.isPrivate(field.getModifiers())) continue;
            fields.add(field);
        }
        
        return fields;
    }
    
    public static List<Class> getJavaClassesInFolder(String folderPath) {
        File folder = new File(folderPath);
        
        if (folder.exists() && folder.isFile() || !folder.exists()) return null;
        
        String suffix = ".java";
        String[] folderList = folder.list();        
        List<Class> classes = new ArrayList<>();
        
        for (String path : folderList) {
            if (!path.endsWith(suffix)) continue;

            Class<?> clazz;
            
            try {
                clazz = Class.forName(folder.getName() + "." + path.replace(suffix, ""));
            } catch (ClassNotFoundException ex) {
                clazz = null;
            }
            
            if (clazz != null) classes.add(clazz);
        }
        
        return classes;
    }
    
    public static void closeConnection(Connection connection) throws Exception {
        if (!connection.isClosed() && !connection.getAutoCommit()) connection.commit();
        connection.close();
    }
}