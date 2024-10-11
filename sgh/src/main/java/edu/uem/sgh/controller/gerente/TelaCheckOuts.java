/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
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
public class TelaCheckOuts extends AbstractController implements ChangeListener<Object>, EventHandler<ActionEvent>, Initializable{
    @FXML
    private VBox root;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private TableView tableView;
    
    private Usuario usuario;
    private Task<Result<List<?>>> tarefaBuscarCheckOuts;
    private Result<List<?>> rslt;
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
        
        if (!source.equals(btnCarregar))
            return;
        
        if (tarefaBuscarCheckOuts == null) {
            tarefaBuscarCheckOuts = new Task<Result<List<?>>>() {
                @Override
                protected Result<List<?>> call() throws Exception {
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
        
        if (bgThread == null || state == Thread.State.TERMINATED) bgThread = new Thread(tarefaBuscarCheckOuts);
        
        try {
            bgThread.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rslt = tarefaBuscarCheckOuts.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rslt = new Result.Error<>(e);
        }
        
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<?>> error = (Result.Error<List<?>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<?>> success = (Result.Success<List<?>>) rslt;
            List<?> checkOuts = success.getData();
            
            limparTabela();
            
            if (checkOuts.isEmpty())
                return;
                
            for (Object object : checkOuts) {
                
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!(newValue instanceof Usuario))
            return;
        
        usuario = (Usuario) newValue;
    }    
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }

    private void interromperTodasThreads() {
        if (bgThread == null)
            return;
        
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
    }

    private void interromperTodasTarefas() {
        if (tarefaBuscarCheckOuts == null)
            return;
        
        TarefaUtil.interromperTarefaRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS, tarefaBuscarCheckOuts);
    }
}