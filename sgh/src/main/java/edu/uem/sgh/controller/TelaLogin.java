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
import javafx.beans.property.ReadOnlyDoubleProperty;
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
    
    private Task<Result<Usuario>> tarefaFazerLogin, tarefaBuscarUsuario;
    private ReadOnlyDoubleProperty progressoTarefaLogin, progressoTarefaRecuperarPalavraPasse;
    private String email, palavraPasse;
    
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
        
        if (progressoTarefaLogin != null) progressoTarefaLogin.removeListener(this);
        if (progressoTarefaRecuperarPalavraPasse != null) progressoTarefaRecuperarPalavraPasse.removeListener(this);
        if (tarefaFazerLogin != null) tarefaFazerLogin = null;
        if (tarefaBuscarUsuario != null) tarefaBuscarUsuario = null;
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
    
    public ImageView getCloseButton() {
        return close;
    }

    public ImageView getMinimizeButton() {
        return minimize;
    }
    
    public void setAutenticacaoRepository(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    public void setParentMouseEventHandler(EventHandler<MouseEvent> parentMouseEventHandler) {
        this.parentMouseEventHandler = parentMouseEventHandler;
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
        
        progressoTarefaLogin = tarefaFazerLogin.progressProperty();
        progressoTarefaLogin.addListener(this);
    }

    private void recuperarPalavraPasse() {
        tarefaBuscarUsuario = new Task<Result<Usuario>>() {
            @Override
            protected Result<Usuario> call() throws Exception {
                return autenticacaoRepository.getUserByEmail(email);
            }
        };
        
        progressoTarefaRecuperarPalavraPasse = tarefaBuscarUsuario.progressProperty();
        progressoTarefaRecuperarPalavraPasse.addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable.equals(txtEmail.textProperty())) {
            observarMudancasTxtEmail((String) newValue);
        } else if (observable.equals(txtPalavraPasse.textProperty())) {
            observarMudancasTxtPalavraPasse((String) newValue);
        } else if (observable.equals(progressoTarefaLogin)) {
            observarProgressoTarefaLogin((Double) newValue);
        } else if (observable.equals(progressoTarefaRecuperarPalavraPasse)) {
            observarProgressoTarefaRecuperarPalavraPasse((Double) newValue);
        }
    }

    private void observarProgressoTarefaLogin(Double progresso) {
        System.out.println("sksk " + progresso);
    }

    private void observarProgressoTarefaRecuperarPalavraPasse(Double progresso) {
        System.out.println("sksk " + progresso);
    }
}