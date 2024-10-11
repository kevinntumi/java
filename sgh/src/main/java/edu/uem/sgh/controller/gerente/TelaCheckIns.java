/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.Result;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
public class TelaCheckIns extends AbstractController implements EventHandler<ActionEvent>, Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Task<Result<List<CheckIn>>> tarefaBuscarCheckIns;
    private Result<List<CheckIn>> rslt;
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
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnCarregar)))
            return;
        
        if (tarefaBuscarCheckIns == null) {
            tarefaBuscarCheckIns = new Task<Result<List<CheckIn>>>() {
                @Override
                protected Result<List<CheckIn>> call() throws Exception {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };
        }
        
        Thread.State state = null;
        
        if (bgThread != null) {
            state = bgThread.getState();
            
            if (state != Thread.State.TERMINATED){
                interromperThreadRecursivamente(0, bgThread);
            }
        }
        
        if (bgThread == null || state == Thread.State.TERMINATED) bgThread = new Thread(tarefaBuscarCheckIns);
        
        try {
            bgThread.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rslt = tarefaBuscarCheckIns.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rslt = new Result.Error<>(e);
        }
        
        interromperThreadRecursivamente(0, bgThread);
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<CheckIn>> error = (Result.Error<List<CheckIn>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<CheckIn>> success = (Result.Success<List<CheckIn>>) rslt;
            List<CheckIn> checkIns = success.getData();
            
            if (checkIns.isEmpty()) {
                
            } else {
                
                limparTabela();
                
                for (CheckIn checkIn : checkIns) {
                    
                }
            }
        }
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
    
    private void interromperThread(Thread thread) {
        if (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    private void interromperTodasThreads() {
        interromperThreadRecursivamente(0, bgThread);
    }
    
    private void interromperTodasTarefas() {
        if (tarefaBuscarCheckIns != null) 
            interromperTarefaRecursivamente(0, tarefaBuscarCheckIns);
    }
    
    private void interromperTarefa(Task<?> tarefa) {
        if (tarefa.isRunning()) tarefa.cancel(true);
    }
 
    private void interromperThreadRecursivamente(int tentativa, Thread thread) {
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS || thread == null || thread.getState() == Thread.State.TERMINATED) return;
        interromperThread(thread);
        interromperThreadRecursivamente(tentativa + 1, thread);
    }
    
    private void interromperTarefaRecursivamente(int tentativa, Task<?> tarefa) {
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS || !tarefa.isRunning()) return;
        interromperTarefa(tarefa);
        interromperTarefaRecursivamente(tentativa + 1, tarefa);
    }
}