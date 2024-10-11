/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh;

import edu.uem.sgh.connection.DatabaseConnection;
import edu.uem.sgh.util.DatabaseUtil;
import edu.uem.sgh.util.TipoUsuarioModel;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Kevin Ntumi
 */
public class JUnitTest {
    private final static String baseResourcePath = System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\uem\\sgh";
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void findResourcesFXML() throws MalformedURLException {
        File f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\uem\\sgh\\error_dialog.fxml");
        if (f.exists() && f.isFile()) System.out.println(f.toURI().toURL());
    }

    @Test
    public void findResources() {
        File f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images");
        System.out.println(f);
    }
    
    @Test
    public void testRemoteDatabaseConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
    }
     
    @Test
    public void testLocalSQLiteDatabaseConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection(); 
        
    }
    
    @Test
    public void testResourcePathFileName() {
        String str = "tela_servico";
        System.out.println(getControllerName(str));
    }
    
    public String getControllerName(String str) {
        if (str == null || str.isBlank()) return null;
        
        String controllerName = "";
        char onlySpecialCharAllowed = '_';
        int n = (str.length() - 1);
        
        for (int i = 0 ; i <= n ; i++) {
            char ch = str.charAt(i);
            
            if (i == 0 && Character.isLetter(ch))
                controllerName += Character.toUpperCase(ch);
            
            if (i != 0) {
                if (!(Character.isLetter(ch) || ch == onlySpecialCharAllowed)){
                    return null;
                }
                
                if (!Character.isLetter(ch))
                    continue;
                
                char previousChar = str.charAt(i - 1);
            
                if (previousChar == onlySpecialCharAllowed)
                    controllerName += Character.toUpperCase(ch);
                else
                    controllerName += ch;
            }
        }
        
        return controllerName;
    }
    
    @Test 
    public void findResourcesPathByUserRole() {
        TipoUsuarioModel[] tipoUsuarios = TipoUsuarioModel.values();
        
        for (TipoUsuarioModel tipoUsuario : tipoUsuarios) {
            String tipoUsuarioStr = tipoUsuario.toString().toLowerCase();
            String path = baseResourcePath + "\\" + tipoUsuarioStr;
            
            if (!existsFolder(path)) return;
            
            Map<Class<?>,URL> urls = getResourcesPathURL(path, tipoUsuarioStr);
        }
    }
    
    public boolean existsFolder(String path) {
        try {
            File f = new File(path);
            return f.exists() && f.isDirectory();
        } catch (Exception e) {
            return false;
        }
    }
    
    public Map<Class<?>, URL> getResourcesPathURL(String path, String tipoUsuarioStr) {
        Map<Class<?>, URL> resourcesPath = new HashMap<>();
        File f;
        
        try {
            f = new File(path);
        } catch (Exception e) {
            return resourcesPath;
        }
        
        if (!(f.exists() && f.isDirectory()))
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
            
            String 
                    extensionStrippedFileName = fileName.replace(extension, ""),
                    controllerName = getControllerName(extensionStrippedFileName),
                        typeName = "edu.uem.sgh.controller." + tipoUsuarioStr + "." + controllerName;
            
            Class<?> controllerClass = null;
            System.out.println(fileName);
            switch (extensionStrippedFileName) {
                case "tela_funcionario":
                case "tela_servico":
                case "tela_quarto":
                case "tela_gerente":
                case "tela_check_in":
                case "tela_check_out":
                case "tela_reserva":
                case "tela_servicos":
                case "tela_funcionarios":
                case "tela_quartos":
                case "tela_gerentes":
                case "tela_check_ins":
                case "tela_check_outs":
                case "tela_reservas":
                    System.out.println("fileName: " + fileName + ", typeName: " + typeName);
                    controllerClass = getClassByTypeName(typeName);
                        break;
            }
            
            if (controllerClass != null)
                resourcesPath.put(controllerClass, url);
        }
        
        return resourcesPath;
    }
    
    public static Class<?> getClassByTypeName(String typeName) {
        Class<?> clazz;

        try {
            clazz = Class.forName(typeName);
        } catch (ClassNotFoundException ex) {
            clazz = null;
        }
        
        return clazz;
    }
    
    @Test
    public void testJavaClassesFieldSQLiteColumnConvertion() {
        DatabaseUtil databaseUtil;
        
    }
    
    @Test
    public void testResourcePath() throws Exception {
        Parent root;
        
        try {
            root = FXMLLoader.load(new URI(baseResourcePath + "\\TelaLogin.fxml").toURL());
        } catch (IOException e) {
            root = null;
        }

        org.junit.jupiter.api.Assertions.assertNotNull(root);
    }
}