/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Hospede;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaServicos extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable{
    @FXML
    private VBox root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private TableView<Hospede> tableView;
    
    @FXML
    private Button btnCarregar;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnAction(this);
        btnCarregar.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        btnCarregar.setOnAction(null);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnAdicionar) || source.equals(btnCarregar)))
            return;
        
        if (source.equals(btnAdicionar))
            adicionarNovoServico();
        else
            carregarServicos();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }
    
    private void adicionarNovoServico() {
        
    }

    private void carregarServicos() {
        
    }
}