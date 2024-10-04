/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.lang.reflect.AccessFlag;
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
    
    public static void initializeLocalDatabase(Connection localConnection) throws Exception {
        DatabaseMetaData metaData = localConnection.getMetaData();
        
        if (!LOCAL_DRIVER_NAME.equals(metaData.getDriverName())) return;
        
        ResultSet tablesResultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
        int i = 0;
        
        while (tablesResultSet.next()) {
            i++;
        }
        
        if (i != 0) return;
        
        List<String> tableSchemas = getTableSchemas(getJavaClassesInFolder(Path.SQLITE_SCHEMA_FOLDER_PATH));
        
        try (Statement statement = localConnection.createStatement()) {
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
    
    static List<String> getTableSchemas(List<Class<?>> classes) {
        if (classes == null || classes.isEmpty()) return null;
        
        List<String> tableSchemas = new ArrayList<>();
        
        for (Class<?> clazz : classes) {
            if (isAbstractClass(clazz)) {
                for (Class<?> clazzz : getSubClassesBySuperClass(clazz)) {
                    String tableSchema;
                    
                    try {
                        tableSchema = getTableSchemaCreationQueryByClass(clazzz, clazz);
                    } catch (RuntimeException e) {
                        continue;
                    }
                    
                    tableSchemas.add(tableSchema);
                }
            } else {
                String tableSchema;
                    
                try {
                    tableSchema = getTableSchemaCreationQueryByClass(clazz, null);
                } catch (RuntimeException e) {
                    continue;
                }

                tableSchemas.add(tableSchema);
            }
        }
        
        return tableSchemas;
    }
    
    private static String getTableSchemaCreationQueryByClass(Class<?> clazz, Class<?> superClass) throws RuntimeException {
        String tableSchemaCreationQuery;
        
        if (superClass == null) {
            tableSchemaCreationQuery = "CREATE TABLE IF NOT EXISTS " + getSQLiteTableName(clazz.getSimpleName()) + " (";
        } else {
            tableSchemaCreationQuery = "CREATE TABLE IF NOT EXISTS " + getSQLiteTableName(superClass.getSimpleName()) + "_" + getSQLiteTableName(clazz.getSimpleName()) + " ("; 
        }
                    
        List<Field> fields = getFieldsByClass(clazz);
        int size = fields.size();
        
        if (size == 0) throw new RuntimeException();

        int sizeMinusOne = (size - 1), i;

        for (i = 0 ; i < size ; i++) {
            Field field = fields.get(i);
            String sqliteColumnType = getSQLiteColumnTypeName(field.getType().getSimpleName()), sqliteColumnName;

            if (sqliteColumnType == null) continue;
            
            sqliteColumnName = getSQLiteColumnName(field.getName());
            
            if (i == 0) {
                tableSchemaCreationQuery += (sqliteColumnName + " " + sqliteColumnType);
            } else {
                tableSchemaCreationQuery += (" " + sqliteColumnName + " " + sqliteColumnType);
            }
            
            if (sqliteColumnName.equals("id")) tableSchemaCreationQuery += " PRIMARY KEY";
            if (sqliteColumnType.equals("TEXT")) tableSchemaCreationQuery += " NOT NULL";
            if (i != sizeMinusOne) tableSchemaCreationQuery += ",";
        }
        
        return tableSchemaCreationQuery + ")";
    }
    
    private static List<Class<?>> getSubClassesBySuperClass(Class<?> superClass) {
        List<Class<?>> subClasses = new ArrayList<>();
        
        for (Class<?> clazz : superClass.getNestMembers()) {
            if (clazz.getSuperclass().equals(superClass)) subClasses.add(clazz);
        }
        
        return subClasses;
    }
    
    public static List<Field> getFieldsByClass(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        
        for (Field declaredField : declaredFields) {
            if (Modifier.isPrivate(declaredField.getModifiers())) fields.add(declaredField);
        }
        
        Class<?> superClass = clazz.getSuperclass();
        
        if (!superClass.equals(Object.class)) {
            List<Field> declaredSuperClassFields = getFieldsByClass(superClass);
            
            for (Field declaredField : declaredSuperClassFields) {
                if(Modifier.isPrivate(declaredField.getModifiers())) fields.add(declaredField);
            }
        }
        
        return fields;
    }
    
    public static String getRelativeFolderPath(String absoluteFolderPath) {
        return absoluteFolderPath.replace(Path.BASE_JAVA_SRC_FOLDER_PATH, "").replace("\\", ".");
    }
    
    private static boolean isAbstractClass(Class<?> clazz) {
        if (clazz == null) throw new NullPointerException();
        
        for (AccessFlag accessFlag : clazz.accessFlags()) {
            if (accessFlag.equals(AccessFlag.ABSTRACT)) return true;
        }
        
        return false;
    }
    
    private static String getSQLiteTableName(String className) {
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
    
    public static List<Class<?>> getJavaClassesInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isFile() || !folder.exists()) return null;
        
        String suffix = ".java";
        String[] folderList = folder.list();        
        List<Class<?>> classes = new ArrayList<>();
       
        for (String path : folderList) {
            if (!path.endsWith(suffix)) continue;

            Class<?> clazz;

            try {
                clazz = Class.forName(getRelativeFolderPath(folder.getAbsolutePath()) + "." + path.replace(suffix, ""));
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