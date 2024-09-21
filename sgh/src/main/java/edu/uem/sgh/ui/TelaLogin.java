/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLogin{
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtPalavraPasse;
    
    @FXML
    private Button btnIniciarSessao;
    
    @FXML
    private TextField txtRecuperarPalavraPasse;
    
    @FXML
    private ImageView close;
    
    @FXML
    private ImageView minimize;

    public TextField getTxtEmail() {
        return txtEmail;
    }

    public TextField getTxtPalavraPasse() {
        return txtPalavraPasse;
    }

    public Button getBtnIniciarSessao() {
        return btnIniciarSessao;
    }

    public TextField getTxtRecuperarPalavraPasse() {
        return txtRecuperarPalavraPasse;
    }

    public ImageView getClose() {
        return close;
    }

    public ImageView getMinimize() {
        return minimize;
    }
}