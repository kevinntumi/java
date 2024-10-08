/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.util.LoginValidator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLogin extends AbstractController implements Initializable, EventHandler<MouseEvent>, ChangeListener<Object>{
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtPalavraPasse;
    
    @FXML
    private Button btnIniciarSessao;
    
    @FXML
    private Button btnRecuperarPalavraPasse;
    
    @FXML
    private ImageView close;
    
    @FXML
    private ImageView minimize;
    
    @FXML
    private AnchorPane root;
    
    @Dependency
    private AutenticacaoRepository autenticacaoRepository;
    
    @Dependency
    private EventHandler<MouseEvent> parentMouseEventHandler;
    
    @Dependency
    private SimpleObjectProperty<Usuario> usuarioProperty;
    
    private Task<Result<Usuario>> tarefaFazerLogin, tarefaBuscarUsuario;
    private Thread bgThreadOne, bgThreadTwo;
    private Result<Usuario> rsltThreadOne, rsltThreadTwo;
    private String email, palavraPasse;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    
    @Override
    public void adicionarListeners() {
        txtPalavraPasse.textProperty().addListener(this);
        txtEmail.textProperty().addListener(this);
        btnIniciarSessao.setOnMouseClicked(this);
        btnRecuperarPalavraPasse.setOnMouseClicked(this);
        getCloseButton().setOnMouseClicked(parentMouseEventHandler);
        getMinimizeButton().setOnMouseClicked(parentMouseEventHandler);
    }

    @Override
    public void removerListeners() {
        txtPalavraPasse.textProperty().removeListener(this);
        txtEmail.textProperty().removeListener(this);
        btnIniciarSessao.setOnMouseClicked(null);
        btnRecuperarPalavraPasse.setOnMouseClicked(null);
        getCloseButton().setOnMouseClicked(null);
        getMinimizeButton().setOnMouseClicked(null);
        
        interromperTodasThreads();
        interromperTodasTarefas();
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(MouseEvent event) {
        if (!event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) return;
        
        Object source = event.getSource();
        
        if (source.equals(btnIniciarSessao)) 
            iniciarSessao();
        else if (source.equals(btnRecuperarPalavraPasse)) 
            recuperarPalavraPasse();
    }
    
    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable.equals(txtEmail.textProperty()))
            observarMudancasTxtEmail((String) newValue);
        else if (observable.equals(txtPalavraPasse.textProperty()))
            observarMudancasTxtPalavraPasse((String) newValue);
    }

    @Override
    public void setUiClassID(String uiClassID) {
        super.setUiClassID(uiClassID); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getUiClassID() {
        return super.getUiClassID(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getSimpleName());
    }
    
    private ImageView getCloseButton() {
        return close;
    }

    private ImageView getMinimizeButton() {
        return minimize;
    }
    
    public void setAutenticacaoRepository(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    public void setParentMouseEventHandler(EventHandler<MouseEvent> parentMouseEventHandler) {
        this.parentMouseEventHandler = parentMouseEventHandler;
    }

    public void setUsuarioProperty(SimpleObjectProperty<Usuario> usuarioProperty) {
        this.usuarioProperty = usuarioProperty;
    }
    
    private void observarMudancasTxtEmail(String newValue) {
        email = newValue;
        btnIniciarSessao.setDisable(!LoginValidator.isEmailValid(newValue) || !LoginValidator.isPasswordValid(txtPalavraPasse.getText()));
        btnRecuperarPalavraPasse.setDisable(!LoginValidator.isEmailValid(email));
    }

    private void observarMudancasTxtPalavraPasse(String newValue) {
        palavraPasse = newValue;
        btnIniciarSessao.setDisable(!LoginValidator.isPasswordValid(newValue) || !LoginValidator.isEmailValid(txtEmail.getText()));
    }

    private void iniciarSessao() {
        tarefaFazerLogin = new Task<Result<Usuario>>() {
            @Override
            protected Result<Usuario> call() throws Exception {
                return autenticacaoRepository.logIn(email, palavraPasse);
            }
        };
        
        Thread.State state = null;
        
        if (bgThreadOne != null) {
            state = bgThreadOne.getState();
            
            if (state != Thread.State.TERMINATED) 
                interromperThreadRecursivamente(0, bgThreadOne);
        }
        
        if (bgThreadOne == null || state == Thread.State.TERMINATED) bgThreadOne = new Thread(tarefaFazerLogin);
        
        try {
            bgThreadOne.start();
        } catch (Exception e) {
            return;
        }
        
        obterResultadoThread(bgThreadOne, tarefaFazerLogin, rsltThreadOne);
        
        if (rsltThreadOne instanceof Result.Error) {
            
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rsltThreadOne;
            usuarioProperty.set(success.getData());
        }
    }
    
    private void recuperarPalavraPasse() {
        inicializarThread(bgThreadTwo, tarefaBuscarUsuario);
        
        try {
            bgThreadTwo.start();
        } catch (Exception e) {
            return;
        }
        
        obterResultadoThread(bgThreadTwo, tarefaBuscarUsuario, rsltThreadTwo);
        
        if (rsltThreadTwo instanceof Result.Error) {
            
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rsltThreadTwo;
        }
    }
    
    @SuppressWarnings("All")
    private void inicializarThread(Thread thread, Task<Result<Usuario>> tarefaRelacionada) {
        Thread bgThread = thread;
        
        if (tarefaRelacionada == null) {
            tarefaRelacionada = new Task<Result<Usuario>>() {
                @Override
                protected Result<Usuario> call() throws Exception {
                    return (bgThread.equals(bgThreadOne)) ? autenticacaoRepository.logIn(email, palavraPasse) : autenticacaoRepository.getUserByEmail(email);
                }
            };
        }
        
        Thread.State state = null;
        
        if (thread != null) {
            state = thread.getState();
            
            if (state != Thread.State.TERMINATED) 
                interromperThreadRecursivamente(0, thread);
        }
        
        if (thread == null || state == Thread.State.TERMINATED) thread = new Thread(tarefaRelacionada);
    }
    
    private void obterResultadoThread(Thread thread, Task<Result<Usuario>> tarefaRelacionada, Result<Usuario> result){
        try {
            result = tarefaRelacionada.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            switch (e.getClass().getSimpleName()) {
                case "InterruptedException": 
                    break;
                case "CancellationException":
                    break;
                case "TimeoutException":
                    break;
                case "ExecutionException":
                    break;
            }
        }
        
        interromperThreadRecursivamente(0, thread);
    }
    
    private void interromperTodasTarefas() {
        if (tarefaFazerLogin != null) 
            interromperTarefaRecursivamente(0, tarefaFazerLogin);
        
        if (tarefaBuscarUsuario != null) 
            interromperTarefaRecursivamente(0, tarefaBuscarUsuario);
    }
    
    private void interrromperTarefa(Task<? extends Object> tarefa) {
        if (tarefa.isRunning()) tarefa.cancel(true);
    }
    
    private void interromperTarefaRecursivamente(int tentativa, Task<?> tarefa) {
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS || !tarefa.isRunning()) return;
        interrromperTarefa(tarefa);
        interromperTarefaRecursivamente(tentativa + 1, tarefa);
    }
    
    private void interromperTodasThreads() {
        interromperThreadRecursivamente(0, bgThreadOne);
        interromperThreadRecursivamente(0, bgThreadTwo);
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
    
    private void interromperThreadRecursivamente(int tentativa, Thread thread) {
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS || thread.getState() == Thread.State.TERMINATED) return;
        interromperThread(thread);
        interromperThreadRecursivamente(tentativa + 1, thread);
    }
}