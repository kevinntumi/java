/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import edu.uem.sgh.model.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLoginController extends AbstractController {
     @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtPalavraPasse;
    
    @FXML
    private Button btnIniciarSessao;
    
    @FXML
    private Button btnRecuperarPalavraPasse;
    
    @FXML
    private ImageView close;
    
    @FXML
    private ImageView minimize;
    
    @FXML
    private VBox form;
    
    @FXML
    private ImageView img;
    
    private EventHandler<MouseEvent> mouseEventListener;

    public TelaLoginController() {
        super();
    }
    
    @Override
    public void adicionarListeners() {
        minimize.setOnMouseClicked(mouseEventListener);
        close.setOnMouseClicked(mouseEventListener);
    }

    @Override
    public void removerListeners() {
        minimize.setOnMouseClicked(null);
        close.setOnMouseClicked(null);
    }

    @Override
    public Parent getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}