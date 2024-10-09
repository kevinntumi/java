/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.cliente;

/**
 *
 * @author PAIN
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class TelaClienteController {

    @FXML
    private Button btnEfectuarReserva;

    @FXML
    private ComboBox<?> cbxTipoQuarto;

    @FXML
    private DatePicker dataCheckinReserva;

    @FXML
    private DatePicker dataCheckoutReserva;

    @FXML
    private DatePicker dataDeNascimento;

    @FXML
    private Tab tabCadastrar1;

    @FXML
    private Tab tabCadastrar11;

    @FXML
    private TextField txtBI;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtResponsavelReserva;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtUsername;

}
