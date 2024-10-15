/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaFuncionarios extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private TableView<Funcionario> tableView;
    
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
    private Result<List<Funcionario>> rslt;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnAction(this);
       // btnEditar.setOnAction(this);
       // btnDemitir.setOnAction(this);
        //btnRecontratar.setOnAction(this);
        btnCarregar.setOnAction(this);
        selectionMode.addListener(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        //btnEditar.setOnAction(null);
        //btnDemitir.setOnAction(null);
        //btnRecontratar.setOnAction(null);
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
        if (!observable.equals(selectionMode)) {
            if (newValue instanceof Usuario)
                    usuario = (Usuario) newValue;
            
            return;
        }

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
        if (rslt == null) {
            return;
        }
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Funcionario>> error = (Result.Error<List<Funcionario>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
            System.err.println(error.getException());
        } else {
            Result.Success<List<Funcionario>> success = (Result.Success<List<Funcionario>>) rslt;
            List<Funcionario> funcionarios = success.getData();
            
            limparTabela();
            
            if (funcionarios.isEmpty())
                return;
            
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty())
            return;
        
        tableView.getItems().clear();
    }
}