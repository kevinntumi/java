/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import com.gluonhq.charm.glisten.control.LifecycleEvent;
import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.model.DialogDetails;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.util.LoginValidator;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class TelaLogin extends AbstractController implements Initializable, EventHandler<Event>, ChangeListener<Object>{
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
    
    private SimpleObjectProperty<DialogDetails> dialogDetailsProperty;
    private Task<Result<Usuario>> tarefaFazerLogin, tarefaBuscarUsuario;
    private Thread bgThreadOne, bgThreadTwo;
    private Result<Usuario> rsltThreadOne, rsltThreadTwo;
    private String email, palavraPasse;
    private final int TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS = 5, TENTATIVAS_MAXIMAS_INTERRUPCAO_TAREFAS = 5;
    private AbstractController abstractController = null;
    
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
        removerTodasReferencias();
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(Event event) {
        EventType<? extends Event> eventType = event.getEventType();
        Object source = event.getSource();
        
        if (eventType.equals(LifecycleEvent.CLOSE_REQUEST)) {
            if (abstractController == null || !source.equals(abstractController.getRoot()) || !(abstractController instanceof DialogController)) return;
            removerTodasReferencias();            
        } else if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {
            if (source.equals(btnIniciarSessao)) 
                iniciarSessao();
            else if (source.equals(btnRecuperarPalavraPasse)) 
                recuperarPalavraPasse();
        }
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

    public SimpleObjectProperty<DialogDetails> getDialogDetailsProperty() {
        if (dialogDetailsProperty == null) dialogDetailsProperty = new SimpleObjectProperty<>();
        return dialogDetailsProperty;
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
    
    public void removerTodasReferencias() {
        if (abstractController == null || !(abstractController instanceof DialogController)) return;
            
        DialogController dialogController = (DialogController) abstractController;
        dialogController.removerListeners();
        dialogController.setLifecycleEventHandler(null);
        
        if (dialogDetailsProperty != null) getDialogDetailsProperty().removeListener(dialogController);
    }
    
    private void observarMudancasTxtEmail(String newValue) {
        email = newValue;
        btnIniciarSessao.setDisable(!(LoginValidator.isPasswordValid(txtPalavraPasse.getText()) && LoginValidator.isEmailValid(email)));
        btnRecuperarPalavraPasse.setDisable(!LoginValidator.isEmailValid(email));
    }

    private void observarMudancasTxtPalavraPasse(String newValue) {
        palavraPasse = newValue;
        btnIniciarSessao.setDisable(!(LoginValidator.isPasswordValid(palavraPasse) && LoginValidator.isEmailValid(txtEmail.getText())));
    }

    private void iniciarSessao() {
        if (tarefaFazerLogin == null) {
            tarefaFazerLogin = new Task<Result<Usuario>>() {
                @Override
                protected Result<Usuario> call() throws Exception {
                    return autenticacaoRepository.logIn(1, palavraPasse);
                }
            };
        }
        
        Thread.State state = null;
        
        if (bgThreadOne != null) {
            state = bgThreadOne.getState();
            
            if (state != Thread.State.TERMINATED){
                interromperThreadRecursivamente(0, bgThreadOne);
            }
        }
        
        if (bgThreadOne == null || state == Thread.State.TERMINATED) bgThreadOne = new Thread(tarefaFazerLogin);
        
        try {
            bgThreadOne.start();
        } catch (Exception e) {
            return;
        }
     
        //mostrarProgressBar
        try {
            rsltThreadOne = tarefaFazerLogin.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rsltThreadOne = new Result.Error<>(e);
        }
        
        interromperThreadRecursivamente(0, bgThreadOne);
        //naoMostrarProgressBar
        System.out.println(rsltThreadOne.getValue());
        if (rsltThreadOne instanceof Result.Error) {
            Result.Error<Usuario> error = (Result.Error<Usuario>) rsltThreadOne;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsgErro("Ocorreu algo inesperado", descricao);
            System.err.println(error.getException());
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rsltThreadOne;
            Usuario usuario = success.getData();
            
            if (usuario == null) {
                mostrarMsgErro("Ocorreu algo inesperado", "Usuário ou palavra-passe inválido(a).");
            } else {
                usuarioProperty.set(usuario);
                System.out.println("yup");
            }
        }
    }
    
    private void recuperarPalavraPasse() {
        if (tarefaBuscarUsuario == null) {
            tarefaBuscarUsuario = new Task<Result<Usuario>>() {
                @Override
                protected Result<Usuario> call() throws Exception {
                    return autenticacaoRepository.getUserById(90);
                }
            };
        }
        
        Thread.State state = null;
        
        if (bgThreadTwo != null) {
            state = bgThreadTwo.getState();
            
            if (state != Thread.State.TERMINATED) 
                interromperThreadRecursivamente(0, bgThreadTwo);
        }
        
        if (bgThreadTwo == null || state == Thread.State.TERMINATED) bgThreadTwo = new Thread(tarefaBuscarUsuario);
        
        try {
            bgThreadTwo.start();
        } catch (Exception e) {
            return;
        }
        
        try {
            rsltThreadTwo = tarefaBuscarUsuario.get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | TimeoutException | ExecutionException e) {
            rsltThreadTwo = new Result.Error<>(e);
        }
        
        interromperThreadRecursivamente(0, bgThreadTwo);
        
        if (rsltThreadTwo instanceof Result.Error) {
            
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rsltThreadTwo;
        }
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
        if (tentativa < 0 || tentativa > TENTATIVAS_MAXIMAS_INTERRUPCAO_THREADS || thread == null || thread.getState() == Thread.State.TERMINATED) return;
        interromperThread(thread);
        interromperThreadRecursivamente(tentativa + 1, thread);
    }

    private void mostrarMsgErro(String title, String description) {
        if (abstractController == null) {
            FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("error_dialog"));
            Parent content;

            try {
                content = fXMLLoader.load();
            }catch(IOException e) {
                content = null;
            }
            
            if (content == null) return;
            
            abstractController = fXMLLoader.getController();
            //adicionar content a root
        }
        
        //mostrar content
        
        if (!(abstractController instanceof DialogController)) return;
        
        DialogController dialogController = (DialogController) abstractController;
        getDialogDetailsProperty().removeListener(dialogController);
        
        DialogDetails dialogDetails = getDialogDetailsProperty().get();
        
        if (dialogDetails == null) {
            dialogDetails = new DialogDetails(title, description);
        } else {
            dialogDetails.setTitle(title);
            dialogDetails.setDescription(description);
        }
        
        getDialogDetailsProperty().addListener(dialogController);
        getDialogDetailsProperty().set(dialogDetails);
        dialogController.setLifecycleEventHandler(getEventHandler());
        dialogController.adicionarListeners();
    }
    
    EventHandler<Event> getEventHandler() {
        return this;
    }
}