/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.sql.Connection;
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
    
    public DatabaseUtilTest() {
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

    /**
     * Test of fileSizeInMegaBytes method, of class DatabaseUtil.
     */
    @Test
    public void testFileSizeInMegaBytes() {
        System.out.println("fileSizeInMegaBytes");
        File file = null;
        double expResult = 0.0;
        double result = DatabaseUtil.fileSizeInMegaBytes(file);
        assertEquals(expResult, result, 0);
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
        DatabaseUtil.initializeLocalDatabase(connection);
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
    public void testJavaClassesFieldSQLiteColumnConvertion() {
        
    }
}
