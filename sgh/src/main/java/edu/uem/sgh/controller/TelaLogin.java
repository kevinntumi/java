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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLogin extends AbstractController implements Initializable, EventHandler<Event>, ChangeListener<Object>{
    @FXML
    private TextField txtCodigoUsuario;
    
    @FXML
    private TextField txtPalavraPasse;
    
    @FXML
    private Button btnIniciarSessao;
  
    @FXML
    private ImageView close;
    
    @FXML
    private ImageView minimize;
    
    @FXML
    private CheckBox cbManterSessaoIniciada;
    
    @FXML
    private VBox root;
    
    @Dependency
    private AutenticacaoRepository autenticacaoRepository;
    
    @Dependency
    private EventHandler<MouseEvent> parentMouseEventHandler;
    
    @Dependency
    private SimpleObjectProperty<Usuario> usuarioProperty;
    
    private Result<Usuario> rslt;
    private String palavraPasse = null;
    private Long codigoUsuario = null;
    private Alert alert;
    
    @Override
    public void adicionarListeners() {
        txtPalavraPasse.textProperty().addListener(this);
        txtCodigoUsuario.textProperty().addListener(this);
        btnIniciarSessao.setOnMouseClicked(this);
        getCloseButton().setOnMouseClicked(parentMouseEventHandler);
        getMinimizeButton().setOnMouseClicked(parentMouseEventHandler);
    }

    @Override
    public void removerListeners() {
        txtPalavraPasse.textProperty().removeListener(this);
        txtCodigoUsuario.textProperty().removeListener(this);
        btnIniciarSessao.setOnMouseClicked(null);
        getCloseButton().setOnMouseClicked(null);
        getMinimizeButton().setOnMouseClicked(null);
    }
    
    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(Event event) {
        EventType<? extends Event> eventType = event.getEventType();
        Object source = event.getSource();
        
        if (eventType.equals(MouseEvent.MOUSE_CLICKED) && source.equals(btnIniciarSessao)) {
            iniciarSessao();
        }
    }
    
    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable.equals(txtCodigoUsuario.textProperty()))
            observarMudancasTxtCodigoUsuario(newValue);
        else if (observable.equals(txtPalavraPasse.textProperty()))
            observarMudancasTxtPalavraPasse(newValue);
    }

    @Override
    public String getUiClassID() {
        return super.getUiClassID(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
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

    private void observarMudancasTxtPalavraPasse(Object newValue) {
        if (newValue == null) {
            palavraPasse = null;
        } else {
            try {
                palavraPasse = String.valueOf(newValue);
            } catch (Exception e) {
                palavraPasse = null;
            }
        }
        
        btnIniciarSessao.setDisable(!(LoginValidator.isPasswordValid(palavraPasse) && LoginValidator.isCodigoUsuarioValid(codigoUsuario)));
    }

    private void iniciarSessao() {
        if (autenticacaoRepository == null) {
            return;
        }
        
        rslt = autenticacaoRepository.logIn(codigoUsuario, palavraPasse, cbManterSessaoIniciada.isSelected());
        
        if (rslt == null) {
            return;
        }
        
        if (rslt instanceof Result.Error) {
            Result.Error<Usuario> error = (Result.Error<Usuario>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsgErro(descricao);
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rslt;
            Usuario usuario = success.getData();
            
            if (usuario == null)
                mostrarMsgErro("Usuário ou palavra-passe inválido(a).");
            else
                usuarioProperty.set(usuario);
        }
    }

    private void mostrarMsgErro(String description) {
        getAlert().setContentText(description);
        getAlert().show();
    }
   
    private void observarMudancasTxtCodigoUsuario(Object newValue) {
        if (newValue == null) {
            codigoUsuario = null;
        } else {
            try {
                codigoUsuario = Long.valueOf(newValue.toString());
            } catch (NumberFormatException e) {
                codigoUsuario = null;
            }
        }
        
        btnIniciarSessao.setDisable(!(LoginValidator.isPasswordValid(palavraPasse) && LoginValidator.isCodigoUsuarioValid(codigoUsuario)));
    }
    
     private Alert getAlert() {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
        }
        
        return alert;
    }
}