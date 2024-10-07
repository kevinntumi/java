/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.datasource;

import java.sql.Connection;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class AbstractDataSource {
    private final Connection connection;

    public AbstractDataSource(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
