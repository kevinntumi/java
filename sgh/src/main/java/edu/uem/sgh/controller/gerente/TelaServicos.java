/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
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
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaServicos extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable{
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private TableView<Servico> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Usuario usuario;
    private Task<Result<List<Servico>>> tarefaBuscarServicos;
    private Result<List<Servico>> rslt;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    private ServicoRepository servicoRepository;
    private int totalTimesVisible;
    private Thread bgThread;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnAction(this);
        btnCarregar.setOnAction(this);
        root.visibleProperty().addListener(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
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
        
        if (!(source.equals(btnAdicionar) || source.equals(btnCarregar)))
            return;
        
        if (source.equals(btnAdicionar))
            adicionarNovoServico();
        else
            carregarServicos();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!(newValue instanceof Usuario)){
            if (observable.equals(root.visibleProperty())) {
                if (totalTimesVisible == 0) {
                    carregarServicos();
                    totalTimesVisible++;
                }
                
                observable.removeListener(this);
            }
            
            return;
        }
        
        usuario = (Usuario) newValue;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }
    
    private void adicionarNovoServico() {
        
    }

    private void carregarServicos() {
        if (tarefaBuscarServicos == null) {
            tarefaBuscarServicos = new Task<Result<List<Servico>>>() {
                @Override
                protected Result<List<Servico>> call() throws Exception {
                    return servicoRepository.getAll();
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
        
        if (bgThread == null || state == Thread.State.TERMINATED) bgThread = new Thread(tarefaBuscarServicos);
        
        try {
            bgThread.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rslt = tarefaBuscarServicos.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rslt = new Result.Error<>(e);
        }
        
        ThreadUtil.interromperThreadRecursivamente(0, TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS, bgThread);
        //naoMostrarProgressBar
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Servico>> error = (Result.Error<List<Servico>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            //mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Servico>> success = (Result.Success<List<Servico>>) rslt;
            List<Servico> servicos = success.getData();
            
            limparTabela();
            
            if (servicos.isEmpty())
                return;
                
            for (Servico servico : servicos) {
                tableView.getItems().add(servico);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
}