/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.ui;

import edu.uem.sgh.controller.AbstractController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLogin extends AbstractController implements Initializable{
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

    public VBox getForm() {
        return form;
    }

    public ImageView getImg() {
        return img;
    }

    public TextField getTxtEmail() {
        return txtEmail;
    }

    public TextField getTxtPalavraPasse() {
        return txtPalavraPasse;
    }

    public Button getBtnIniciarSessao() {
        return btnIniciarSessao;
    }

    public Button getBtnRecuperarPalavraPasse() {
        return btnRecuperarPalavraPasse;
    }

    public ImageView getClose() {
        return close;
    }

    public ImageView getMinimize() {
        return minimize;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}