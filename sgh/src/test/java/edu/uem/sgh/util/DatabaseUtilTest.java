/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
public class DatabaseUtilTest {
    private static final String BASE_FOLDER_PATH = System.getProperty("user.dir");
    private static final String BASE_SRC_FOLDER_PATH = BASE_FOLDER_PATH + "\\src";
    private static final String BASE_JAVA_SRC_FOLDER_PATH = BASE_SRC_FOLDER_PATH + "\\main\\java\\";

    /**
     * Test of fileSizeInMegaBytes method, of class DatabaseUtil.
     */
    @Test
    public void testFileSizeInMegaBytes() {
        System.out.println("fileSizeInMegaBytes");
        File file = null;
        double expResult = 0.0;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initializeLocalDatabase method, of class DatabaseUtil.
     */
    @Test
    public void testInitializeLocalDatabase() throws Exception {
        System.out.println("initializeLocalDatabase");
        Connection connection = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class DatabaseUtil.
     */
    @Test
    public void testCloseConnection() throws Exception {
        System.out.println("closeConnection");
        Connection connection = null;
        DatabaseUtil.closeConnection(connection);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void findAllClasses() throws ClassNotFoundException {
        File folder = new File(BASE_JAVA_SRC_FOLDER_PATH+ "\\edu\\uem\\sgh\\model");
        
        if (folder.exists() && folder.isFile() || !folder.exists()) throw new RuntimeException();
        
        for (String path : folder.list()) {
            if (!path.endsWith(".java")) continue;
            
            Class<?> clazz = Class.forName("edu.uem.sgh." + folder.getName() + "." + path.replace(".java", ""));
            
            System.out.println(clazz);
            
            System.out.println(Modifier.isAbstract(clazz.getModifiers()));
            
            System.out.println();
        }
    }
    
    @Test
    public void getAllClasses() throws ClassNotFoundException {
        File folder = new File(BASE_JAVA_SRC_FOLDER_PATH+ "\\edu\\uem\\sgh\\schema");
        
        if (folder.exists() && folder.isFile() || !folder.exists()) throw new RuntimeException();
        
        for (String path : folder.list()) {
            if (!path.endsWith(".java")) continue;
            
            Class<?> clazz = Class.forName("edu.uem.sgh." + folder.getName() + "." + path.replace(".java", ""));
            
            System.out.println(clazz);
                
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isPrivate(field.getModifiers())) continue;
                System.out.println(field.getName());
            }
            
            System.out.println();
        }
    }
    
    
    
    @Test
    public void testJavaClassesFieldSQLiteColumnConvertion() {
        String primaryFolderPath = BASE_SRC_FOLDER_PATH + "\\main\\java\\edu\\uem\\sgh\\schema";
        List<Class> classes = getJavaClassesInFolder(primaryFolderPath);

        if (classes == null) return;
        
        for (Class<?> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                for (Class<?> clazzz : getSubClassesBySuperClass(clazz)) {
                   // String tableQuery = getTableSchemaCreationQueryByClass(clazzz, clazz);
                    
                }
            } else {
              //  String tableQuery = getTableSchemaCreationQueryByClass(clazz, null);
                
            }
            
            System.out.println();
        }
    }
    
    private static String getTableSchemaCreationQueryByClass(Class<?> clazz, Class<?> superClass) {
        String tableSchemaCreationQuery;
        
        if (superClass == null) {
            tableSchemaCreationQuery = "CREATE TABLE " + getSQLiteTableName(clazz.getSimpleName()) + " (";
        } else {
            tableSchemaCreationQuery = "CREATE TABLE " + getSQLiteTableName(superClass.getSimpleName()) + "_" + getSQLiteTableName(clazz.getSimpleName()) + " ("; 
        }
                    
        List<Field> fields = getFieldsByClass(clazz);
        int size = fields.size();

        if (size == 0) return (tableSchemaCreationQuery + ")");

        int sizeMinusOne = (size - 1), i;

        for (i = 0 ; i < size ; i++) {
            Field field = fields.get(i);
            String sqliteColumnType = getSQLiteColumnTypeName(field.getType().getSimpleName()), sqliteColumnName;

            if (sqliteColumnType == null) continue;

            sqliteColumnName = getSQLiteColumnName(field.getName());
            
            if (i == 0) {
                tableSchemaCreationQuery = (sqliteColumnName + " " + sqliteColumnType);
            } else {
                tableSchemaCreationQuery = (" " + sqliteColumnName + " " + sqliteColumnType);
            }
            
            if (sqliteColumnName.equals("id")) tableSchemaCreationQuery += " PRIMARY KEY";
            if (sqliteColumnType.equals("TEXT")) tableSchemaCreationQuery += " NOT NULL";
            if (i != sizeMinusOne) tableSchemaCreationQuery += ",";
        }
        
        return tableSchemaCreationQuery + ")";
    }
    
    private List<Class<?>> getSubClassesBySuperClass(Class<?> superClass) {
        List<Class<?>> subClasses = new ArrayList<>();
        
        for (Class<?> clazz : superClass.getNestMembers()) {
            if (clazz.getSuperclass().equals(superClass)) subClasses.add(clazz);
        }
        
        return subClasses;
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
    
    public List<Class> getJavaClassesInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isFile() || !folder.exists()) return null;
        
        String suffix = ".java";
        String[] folderList = folder.list();        
        List<Class> classes = new ArrayList<>();
       
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
    
    @Test 
    public void testRelativeFolderPath() {
        String absoluteFolderPath = "C:\\Users\\Kevin Ntumi\\Documents\\NetBeansProjects\\java1\\New folder\\java\\sgh\\src\\main\\java\\edu\\uem\\sgh\\schema";
        System.out.println(getRelativeFolderPath(absoluteFolderPath));
    }
    
    public String getRelativeFolderPath(String absoluteFolderPath) {
        return absoluteFolderPath.replace(BASE_JAVA_SRC_FOLDER_PATH, "").replace("\\", ".");
    }
}