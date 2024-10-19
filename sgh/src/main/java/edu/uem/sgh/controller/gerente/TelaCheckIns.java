/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.check_in_reserva.CheckInReservaRepository;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaCheckIns extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private TableView<CheckIn> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Result<List<CheckIn.Reserva>> rslt;
    private CheckInReservaRepository checkInReservaRepository;
    private boolean firstTimeVisible = true;
    private Usuario usuario;
    
    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            init();
        
        btnCarregar.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnCarregar.setOnAction(null);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnCarregar)))
            return;
        
        init();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
   
    private void carregarCheckIns() {
        if (checkInReservaRepository == null)
            return;
        
        rslt = checkInReservaRepository.obterTodos();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<CheckIn.Reserva>> error = (Result.Error<List<CheckIn.Reserva>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<CheckIn.Reserva>> success = (Result.Success<List<CheckIn.Reserva>>) rslt;
            List<CheckIn.Reserva> checkInsReservas = success.getData();
            
            limparTabela();
            
            if (checkInsReservas.isEmpty())
                return;
              
            for (CheckIn checkIn : checkInsReservas) {
                System.out.println(checkIn);
            }
        }
    }

    public void setCheckInReservaRepository(edu.uem.sgh.repository.check_in_reserva.CheckInReservaRepository checkInReservaRepository) {
        this.checkInReservaRepository = checkInReservaRepository;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
       
    }

    private void init() {
        if (firstTimeVisible)
            firstTimeVisible = false;
        
        carregarCheckIns();
    }
}