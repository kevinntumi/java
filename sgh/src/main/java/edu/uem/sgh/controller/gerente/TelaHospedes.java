/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
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
public class TelaHospedes extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private TableView<Hospede> tableView;
    
    @FXML
    private Button btnCarregarHospedes;
    
    private Usuario usuario;
    private Task<Result<List<Hospede>>> tarefaBuscarHospedes;
    private Result<List<Hospede>> rslt;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    private Thread bgThread;

    @Override
    public void adicionarListeners() {
        btnCarregarHospedes.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnCarregarHospedes.setOnAction(null);
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
        
        if (!source.equals(btnCarregarHospedes))
            return;
        
        if (tarefaBuscarHospedes == null) {
            tarefaBuscarHospedes = new Task<Result<List<Hospede>>>() {
                @Override
                protected Result<List<Hospede>> call() throws Exception {
                    return null;
                }
            };
        }
        
        Thread.State state = null;
        
        if (bgThread != null) {
            state = bgThread.getState();
            
            if (state != Thread.State.TERMINATED){
                ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
            }
        }
        
        if (bgThread == null || state == Thread.State.TERMINATED) bgThread = new Thread(tarefaBuscarHospedes);
        
        try {
            bgThread.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rslt = tarefaBuscarHospedes.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rslt = new Result.Error<>(e);
        }
        
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Hospede>> error = (Result.Error<List<Hospede>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Hospede>> success = (Result.Success<List<Hospede>>) rslt;
            List<Hospede> hospedes = success.getData();
            
            limparTabela();
            
            if (hospedes.isEmpty())
                return;
                
            for (Hospede hospede : hospedes) {
                
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!(newValue instanceof Usuario))
            return;
            
        usuario = (Usuario) newValue;
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
    
    private void interromperTodasThreads() {
        
        //interromperThreadRecursivamente(0, bgThread);
    }
    
    private void interromperTodasTarefas() {
        if (tarefaBuscarHospedes != null) 
            interromperTarefaRecursivamente(0, tarefaBuscarHospedes);
    }
    
    private void interromperTarefa(Task<?> tarefa) {
        if (tarefa.isRunning()) 
            tarefa.cancel(true);
    }
 
    private void interromperTarefaRecursivamente(int tentativa, Task<?> tarefa) {
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS || !tarefa.isRunning()) return;
        interromperTarefa(tarefa);
        interromperTarefaRecursivamente(tentativa + 1, tarefa);
    }
}