/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.repository.hospede.HospedeRepository;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaHospedes extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private TableView<Hospede> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Result<List<Hospede>> rslt;
    private HospedeRepository hospedeRepository;
    private int totalTimesVisible;

    @Override
    public void adicionarListeners() {
        alterarVisibilidadeRoot(true);
        btnCarregar.setOnAction(this);
        root.visibleProperty().addListener(this);
    }

    @Override
    public void removerListeners() {
        alterarVisibilidadeRoot(false);
        btnCarregar.setOnAction(null);
        root.visibleProperty().removeListener(this);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!source.equals(btnCarregar))
            return;
        
        carregarHospedes();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable.equals(root.visibleProperty())) {
            if (totalTimesVisible == 0) {
                carregarHospedes();
                totalTimesVisible++;
            }

            observable.removeListener(this);
        } 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    public void setHospedeRepository(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
    
    private void alterarVisibilidadeRoot(boolean visivel) {
        System.out.println("visibilidade: " + visivel);
        if (visivel) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
        
        if (!visivel && root.getOpacity() == 1.0) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), root);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        }
    }
    
    private void carregarHospedes() {
        if (hospedeRepository == null) 
            return;
        
        //mostrarProgressBar();
        
        rslt = hospedeRepository.obterTodos();
        
        //esconderProgressBar();
        
        if (rslt == null) {
            return;
        }
        
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Hospede>> error = (Result.Error<List<Hospede>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
        } else {
            Result.Success<List<Hospede>> success = (Result.Success<List<Hospede>>) rslt;
            List<Hospede> hospedes = success.getData();
            
            limparTabela();
            
            if (hospedes.isEmpty())
                return;
                
            for (Hospede hospede : hospedes) {
                tableView.getItems().add(hospede);
            }
        }
    }
}