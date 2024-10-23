/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import edu.uem.sgh.model.Usuario.Tipo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kevin Ntumi
 */
public class Path {
    public static final String BASE_FOLDER_PATH = System.getProperty("user.dir");
    public static final String BASE_SRC_FOLDER_PATH = BASE_FOLDER_PATH + "\\src";
    public static final String BASE_JAVA_SRC_FOLDER_PATH = BASE_SRC_FOLDER_PATH + "\\main\\java\\";
    public static final String BASE_RESOURCE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources";
    public static final String SQLITE_SCHEMA_FOLDER_PATH = BASE_JAVA_SRC_FOLDER_PATH + "\\edu\\uem\\sgh\\schema";
    public static final String REMOTE_DATABASE_NAME = "hotel";
    public static final String REMOTE_DATABASE_PORT = "3306";
    public static final String REMOTE_DATABASE_BASE_ENDPOINT = "localhost:" + REMOTE_DATABASE_PORT;
    public static final String REMOTE_DATABASE_INITIAL_URL = "jdbc:mysql://";
    public static final String REMOTE_DATABASE_URL = REMOTE_DATABASE_INITIAL_URL + REMOTE_DATABASE_BASE_ENDPOINT + "/" + REMOTE_DATABASE_NAME;
    public static final String REMOTE_DATABASE_USERNAME = "root";
    public static final String REMOTE_DATABASE_PASSWORD = "kevinntumi";
    public static final String LOCAL_DATABASE_NAME = "local.db";
    public static final String LOCAL_DATABASE_INITIAL_PATH = "jdbc:sqlite:";
    public static final String LOCAL_DATABASE_PATH =  LOCAL_DATABASE_INITIAL_PATH + System.getProperty("user.dir") + "\\" + LOCAL_DATABASE_NAME;
    
    public static String getRelativeFolderPath(String absoluteFolderPath) {
        return absoluteFolderPath.replace(BASE_JAVA_SRC_FOLDER_PATH, "").replace("\\", ".");
    }
    
    public static URL getStylesheetResourceURL(String stylesheetName, String additionalPath) {
        File f = new File(BASE_RESOURCE_PATH + "\\edu\\uem\\sgh\\" + ((additionalPath != null) ? additionalPath : "") + stylesheetName + ".css");
        URL url = null;

        try {
            if (f.exists() && f.isFile()) url = f.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
        
        return url;
    }
    
    public static URL getFXMLURL(String fxmlLayout, String additionalPath) {
        File f = new File(BASE_RESOURCE_PATH + "\\edu\\uem\\sgh\\" + ((additionalPath != null) ? additionalPath : "") + fxmlLayout + ".fxml");
        URL url = null;
        
        try {
            if (f.exists() && f.isFile()) url = f.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
        
        return url;
    }
    
    public static URL getFXMLURL(String fxmlLayout) {
        File f = new File(BASE_RESOURCE_PATH + "\\edu\\uem\\sgh\\" + fxmlLayout + ".fxml");
        URL url = null;
        
        try {
            if (f.exists() && f.isFile()) url = f.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
        
        return url;
    }
    
    public static String getResourcePathByUserType(Tipo tipo, String tipoStr) {
        if (tipo == null) return null;
        return BASE_RESOURCE_PATH + "\\edu\\uem\\sgh\\" + tipoStr;
    }
    
    public static String getResourceName(String str) {
        if (str == null || str.isBlank()) return null;
        
        String resourceName = "";
        char onlySpecialCharAllowed = '_';
        int n = (str.length() - 1);
        
        for (int i = 0 ; i <= n ; i++) {
            char ch = str.charAt(i);
            
            if (i == 0 && Character.isLetter(ch))
                resourceName += Character.toUpperCase(ch);
            
            if (i != 0) {
                if (!(Character.isLetter(ch) || ch == onlySpecialCharAllowed)){
                    return null;
                }
                
                if (!Character.isLetter(ch))
                    continue;
                
                char previousChar = str.charAt(i - 1);
            
                if (previousChar == onlySpecialCharAllowed)
                    resourceName += Character.toUpperCase(ch);
                else
                    resourceName += ch;
            }
        }
        
        return resourceName;
    }
    
    public static Map<Class<?>,URL> getResourcesPathURL(String path, String tipoUsuarioStr) {
        Map<Class<?>, URL> resourcesPath = new HashMap<>();
        File f;
        
        try {
            f = new File(path);
        } catch (Exception e) {
            return resourcesPath;
        }
        
        if (!f.exists() || (f.exists() && !f.isDirectory()))
            return resourcesPath;
        
        File[] files = f.listFiles();
        
        if (files.length == 0)
            return resourcesPath;
        
        String extension = ".fxml";
        
        for (File file : files) {
            if (!file.isFile())
                continue;
            
            String fileName = file.getAbsolutePath().replace(path + "\\", "");
            
            if (!fileName.endsWith(extension))
                continue;
            
            URL url;
            
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                continue;
            }
            
            Class<?> controllerClass;
            
            String 
                extensionStrippedFileName = fileName.replace(extension, ""),
                    controllerName = getResourceName(extensionStrippedFileName),
                        typeFileName = "edu.uem.sgh.controller." + tipoUsuarioStr + "." + controllerName;
            
            switch (extensionStrippedFileName) {
                case "tela_funcionario":
                case "tela_servico":
                case "tela_quarto":
                case "tela_hospede":
                case "tela_gerente":
                case "tela_check_in":
                case "tela_check_out":
                case "tela_reserva":
                case "tela_servicos":
                case "tela_funcionarios":
                case "tela_relatorios":
                case "tela_quartos":
                case "tela_hospedes":
                case "tela_gerentes":
                case "tela_check_ins":
                case "tela_check_outs":
                case "tela_reservas": 
                    controllerClass = ClassUtil.getClassByTypeName(typeFileName);
                        break;
                default:
                    controllerClass = null;
                        break;
            }
            
            if (controllerClass != null)
                resourcesPath.put(controllerClass, url);
        }
        
        return resourcesPath;
    }
}