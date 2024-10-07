/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.quarto;

import java.sql.Connection;

/**
 *
 * @author Kevin Ntumi
 */
public class LocalQuartoDataSource {
    private final Connection connection;

    public LocalQuartoDataSource(Connection connection) {
        this.connection = connection;
    }
    
    
}