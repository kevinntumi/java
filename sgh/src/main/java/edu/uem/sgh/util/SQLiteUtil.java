/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class SQLiteUtil {
    public static List<String> getTableSchemas(List<Class<?>> classes) {
        if (classes == null || classes.isEmpty()) return null;
        
        List<String> tableSchemas = new ArrayList<>();
        
        for (Class<?> clazz : classes) {
            if (ClassUtil.isAbstractClass(clazz)) {
                for (Class<?> clazzz : ClassUtil.getSubClassesBySuperClass(clazz)) {
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
    
    static String getTableSchemaCreationQueryByClass(Class<?> clazz, Class<?> superClass) throws RuntimeException {
        String tableSchemaCreationQuery;
        
        if (superClass == null) {
            tableSchemaCreationQuery = "CREATE TABLE IF NOT EXISTS " + getSQLiteTableName(clazz.getSimpleName()) + " (";
        } else {
            tableSchemaCreationQuery = "CREATE TABLE IF NOT EXISTS " + getSQLiteTableName(superClass.getSimpleName()) + "_" + getSQLiteTableName(clazz.getSimpleName()) + " ("; 
        }
                    
        List<Field> fields = ClassUtil.getFieldsByClass(clazz);
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
}