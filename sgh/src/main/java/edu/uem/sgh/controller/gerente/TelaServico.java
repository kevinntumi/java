/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import java.sql.Connection;
import javafx.scene.Parent;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaServico extends AbstractController{
    private Connection remoteConnection, localConnection;

    public void setRemoteConnection(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }

    public void setLocalConnection(Connection localConnection) {
        this.localConnection = localConnection;
    }

    @Override
    public void adicionarListeners() {
    }

    @Override
    public void removerListeners() {
    }

    @Override
    public Parent getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}