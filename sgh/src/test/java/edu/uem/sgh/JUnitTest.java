/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh;

import edu.uem.sgh.model.Usuario;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Kevin Ntumi
 */
public class JUnitTest {
    private final static String baseResourcePath = System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\uem\\sgh";
    
    public JUnitTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void findResources() {
        File f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images");
        System.out.println(f);
    }
    
    @Test
    public void findEnumValuesByClass() {
        
    }
    
    @Test
    public void findResourcesPathByUserRole() throws MalformedURLException {
        Usuario.Funcao[] funcoes = Usuario.Funcao.values();
        
        for (Usuario.Funcao funcao : funcoes) {
            File f;
            
            try {
                f = new File(baseResourcePath + "\\" + funcao.toString().toLowerCase());
            } catch (Exception e) {
                f = null;
            }
            
            if (f == null || (f.exists() && !f.isDirectory()) || !f.exists()) continue;
            
            for (File file : f.listFiles()) {
                System.out.println(file);
            }
        }
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