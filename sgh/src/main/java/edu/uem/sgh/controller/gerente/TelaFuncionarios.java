/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyIntegerProperty;
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
public class TelaFuncionarios extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>,Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnDemitir;
    
    @FXML
    private Button btnRecontratar;
    
    @FXML
    private Button btnCarregar;
    
    private ReadOnlyIntegerProperty selectionMode;
    private Usuario usuario;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnAction(this);
        btnEditar.setOnAction(this);
        btnDemitir.setOnAction(this);
        btnRecontratar.setOnAction(this);
        btnCarregar.setOnAction(this);
        selectionMode.addListener(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        btnEditar.setOnAction(null);
        btnDemitir.setOnAction(null);
        btnRecontratar.setOnAction(null);
        btnCarregar.setOnAction(null);
        selectionMode.removeListener(this);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnAdicionar) || source.equals(btnEditar) || source.equals(btnRecontratar) || source.equals(btnDemitir) || source.equals(btnCarregar))) 
            return;
        
        if (source.equals(btnAdicionar))
            adicionarFuncionario();
        else if (source.equals(btnEditar))
            editarFuncionario();
        else if (source.equals(btnRecontratar))
            recontratarFuncionario();
        else if (source.equals(btnDemitir))
            demitirFuncionario();
        else
            carregarFuncionarios();
    }

    private void adicionarFuncionario() {
        if (usuario == null)
            return;
    }

    private void editarFuncionario() {
        Integer selectedIndex = selectionMode.get();
        
        if (temLinhaSelecionada(selectedIndex) || usuario == null) 
            return;
        
        
    }

    private void recontratarFuncionario() {
        Integer selectedIndex = selectionMode.get();
        
        if (temLinhaSelecionada(selectedIndex) || usuario == null) 
            return;
        
        
    }

    private void demitirFuncionario() {
        Integer selectedIndex = selectionMode.get();
        
        if (temLinhaSelecionada(selectedIndex) || usuario == null) 
            return;
        
        
    }
    
    private boolean temLinhaSelecionada(Integer selectedIndex) {
        return selectedIndex != -1;
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!observable.equals(selectionMode)) 
            return;
        
        Integer selectedIndex = (Integer) newValue;
        
        btnAdicionar.setDisable(temLinhaSelecionada(selectedIndex));
        btnEditar.setDisable(temLinhaSelecionada(selectedIndex));
        btnRecontratar.setDisable(temLinhaSelecionada(selectedIndex));
        btnDemitir.setDisable(temLinhaSelecionada(selectedIndex));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectionMode = tableView.getSelectionModel().selectedIndexProperty();
        setUiClassID(getClass().getSimpleName());
    }

    private void carregarFuncionarios() {
        
    }
}