/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh;

import edu.uem.sgh.connection.DatabaseConnection;
import edu.uem.sgh.util.DatabaseUtil;
import java.io.File;
import java.io.IOException;
import java.net.URI;
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
    public void testLocalSQLiteDatabaseConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        
    }
    
    @Test 
    public void findResourcesPathByUserRole() {
        
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