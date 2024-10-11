/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Quarto;
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
public class TelaQuartos extends AbstractController implements ChangeListener<Object>, EventHandler<ActionEvent>, Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private TableView tableView;
    
    private Usuario usuario;
    private Task<Result<List<Quarto>>> tarefaBuscarCheckOuts;
    private Result<List<Quarto>> rslt;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    private Thread bgThread;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnAction(this);
        btnCarregar.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
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
        
        if (!(source.equals(btnAdicionar) || source.equals(btnCarregar))) 
            return;
        
        if (source.equals(btnAdicionar))
            adicionarNovoQuarto();
        else
            carregarQuartos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    private void adicionarNovoQuarto() {
        
    }

    private void carregarQuartos() {
        if (tarefaBuscarCheckOuts == null) {
            tarefaBuscarCheckOuts = new Task<Result<List<Quarto>>>() {
                @Override
                protected Result<List<Quarto>> call() throws Exception {
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
            Result.Error<List<Quarto>> error = (Result.Error<List<Quarto>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Quarto>> success = (Result.Success<List<Quarto>>) rslt;
            List<Quarto> quartos = success.getData();
            
            limparTabela();
            
            if (quartos.isEmpty())
                return;
                
            for (Quarto quarto : quartos) {
                System.out.println(quarto);
            }
        }
    }   
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
    
    private void interromperTodasThreads() {
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
    }
    
    private void interromperTodasTarefas() {
        TarefaUtil.interromperTarefaRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS, tarefaBuscarCheckOuts);
    }
}