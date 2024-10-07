/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh.connection;

import edu.uem.sgh.datasource.LocalAutenticacaoDataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Kevin Ntumi
 */
public class ConnectionTypeJUnitTest {
    @Test
    public void testAnnotation(){
       Class<LocalAutenticacaoDataSource> clazz = LocalAutenticacaoDataSource.class;
       
       Constructor[] constructors = clazz.getDeclaredConstructors();
       
       for (Constructor constructor : constructors) {
           Parameter[] parameters = constructor.getParameters();
           
           for (Parameter parameter : parameters) {
               Class<?> typeClass = parameter.getType();
               
               if (typeClass.equals(java.sql.Connection.class)) {
                   System.out.println("suca");
               }
               
               System.out.println(parameter + " kk  " + parameter.getType().getTypeName());
           }
       }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}  
}