/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh.model;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PAIN
 */
public class FuncionarioTest {
    
    public FuncionarioTest() {
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
     * Test of getCargo method, of class Funcionario.
     */
    @Test
    public void testGetCargo() {
        System.out.println("getCargo");
        Funcionario instance = new Funcionario();
        String expResult = "";
        String result = instance.getCargo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCargo method, of class Funcionario.
     */
    @Test
    public void testSetCargo() {
        System.out.println("setCargo");
        String cargo = "";
        Funcionario instance = new Funcionario();
        instance.setCargo(cargo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataRegisto method, of class Funcionario.
     */
    @Test
    public void testSetDataRegisto() {
        System.out.println("setDataRegisto");
        Date dataRegisto = null;
        Funcionario instance = new Funcionario();
        instance.setDataRegisto(dataRegisto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Funcionario.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Funcionario instance = new Funcionario();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Funcionario.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        Funcionario instance = new Funcionario();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataRegisto method, of class Funcionario.
     */
    @Test
    public void testGetDataRegisto() {
        System.out.println("getDataRegisto");
        Funcionario instance = new Funcionario();
        Date expResult = null;
        Date result = instance.getDataRegisto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
