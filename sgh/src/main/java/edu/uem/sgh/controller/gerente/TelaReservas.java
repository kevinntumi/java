/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Reserva;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.util.TarefaUtil;
import edu.uem.sgh.util.ThreadUtil;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaReservas extends AbstractController implements ChangeListener<Object>, EventHandler<ActionEvent>, Initializable{
    @FXML
    private VBox root;
    
    @FXML
    private TableView<Hospede> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Usuario usuario;
    private Task<Result<List<Reserva>>> tarefaBuscarReservas;
    private Result<List<Reserva>> rslt;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    private Thread bgThread;

    @Override
    public void adicionarListeners() {
        btnCarregar.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnCarregar.setOnAction(null);
        interromperTodasThreads();
        interromperTodasTarefas();
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!(newValue instanceof Usuario))
            return;
        
        usuario = (Usuario) newValue;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnCarregar)))
            return;
        
        carregarReservas();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    private void interromperTodasThreads() {
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
    }

    private void interromperTodasTarefas() {
        TarefaUtil.interromperTarefaRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS, tarefaBuscarReservas);
    }

    private void carregarReservas() {
        if (tarefaBuscarReservas == null) {
            tarefaBuscarReservas = new Task<Result<List<Reserva>>>() {
                @Override
                protected Result<List<Reserva>> call() throws Exception {
                    return null;
                }
            };
        }
        
        Thread.State state = null;
        
        if (bgThread != null) {
            state = bgThread.getState();
            
            if (state != Thread.State.TERMINATED)
                ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
            
        }
        
        if (bgThread == null || state == Thread.State.TERMINATED) bgThread = new Thread(tarefaBuscarReservas);
        
        try {
            bgThread.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rslt = tarefaBuscarReservas.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rslt = new Result.Error<>(e);
        }
        
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Reserva>> error = (Result.Error<List<Reserva>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Reserva>> success = (Result.Success<List<Reserva>>) rslt;
            List<Reserva> reservas = success.getData();
            
            limparTabela();
            
            if (reservas.isEmpty())
                return;
                
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
}