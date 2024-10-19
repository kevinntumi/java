/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import com.gluonhq.charm.glisten.control.LifecycleEvent;
import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.util.LoginValidator;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaLogin extends AbstractController implements Initializable, EventHandler<Event>, ChangeListener<Object>{
    @FXML
    private TextField txtId;
    
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
    private StackPane root;
    
    @FXML
    private AnchorPane dialog;
    
    @Dependency
    private AutenticacaoRepository autenticacaoRepository;
    
    @Dependency
    private EventHandler<MouseEvent> parentMouseEventHandler;
    
    @Dependency
    private SimpleObjectProperty<Usuario> usuarioProperty;
    private Result<Usuario> rsltThreadOne, rsltThreadTwo;
    private String palavraPasse;
    private Long id;
    private AbstractController abstractController = null;
    
    @Override
    public void adicionarListeners() {
        txtPalavraPasse.textProperty().addListener(this);
        txtId.textProperty().addListener(this);
        btnIniciarSessao.setOnMouseClicked(this);
        btnRecuperarPalavraPasse.setOnMouseClicked(this);
        getCloseButton().setOnMouseClicked(parentMouseEventHandler);
        getMinimizeButton().setOnMouseClicked(parentMouseEventHandler);
    }

    @Override
    public void removerListeners() {
        txtPalavraPasse.textProperty().removeListener(this);
        txtId.textProperty().removeListener(this);
        btnIniciarSessao.setOnMouseClicked(null);
        btnRecuperarPalavraPasse.setOnMouseClicked(null);
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
        
        if (eventType.equals(LifecycleEvent.CLOSE_REQUEST)) {
            if (abstractController != null && source.equals(abstractController.getRoot()))
                esconderMsgErro(eventType);
        } else if (eventType.equals(MouseEvent.MOUSE_CLICKED)) {
            if (source.equals(btnIniciarSessao)) 
                iniciarSessao();
            else if (source.equals(btnRecuperarPalavraPasse)) 
                recuperarPalavraPasse();
        }
    }
    
    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (abstractController != null && observable.equals(abstractController.getRoot().visibleProperty())) {
            observarVisibilidade(newValue);
            return;
        }
        
        if (observable.equals(txtId.textProperty()))
            observarMudancasTxtId((String) newValue);
        else if (observable.equals(txtPalavraPasse.textProperty()))
            observarMudancasTxtPalavraPasse((String) newValue);
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
    
    
    
    private void observarMudancasTxtId(String newValue) {
        Exception e = null;
        try {
            id = Long.valueOf(newValue);
        } catch (NumberFormatException ex) {
            e = ex;
        }
        
        boolean disableButton;
        
        if (e == null) {
            disableButton = !(LoginValidator.isPasswordValid(txtPalavraPasse.getText()));
        } else {
            disableButton = true;
        }
        
        btnIniciarSessao.setDisable(disableButton);
        btnRecuperarPalavraPasse.setDisable(e != null);
    }

    private void observarMudancasTxtPalavraPasse(String newValue) {
        palavraPasse = newValue;
        
        Exception e = null;
        
        try {
            id = Long.valueOf(txtId.getText());
        } catch (NumberFormatException ex) {
            e = ex;
        }
        
        btnIniciarSessao.setDisable(!(LoginValidator.isPasswordValid(palavraPasse) && e == null));
    }

    private void iniciarSessao() {
        mostrarProgressBar();
        
        rsltThreadOne = autenticacaoRepository.logIn(id, palavraPasse);
        
        esconderProgressBar();
        
        if (rsltThreadOne == null)
            return;
        
        String descricao = null;
   
        if (rsltThreadOne instanceof Result.Success) {
            Usuario usuario = (Usuario) rsltThreadOne.getValue();
            
            if (usuario == null)
                descricao = "Usuário ou palavra-passe inválido(a).";
            else
                usuarioProperty.set(usuario);
        } else {
            Result.Error<Usuario> error = (Result.Error<Usuario>) rsltThreadOne;
            descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
        }
        
        if (descricao != null)
            mostrarMsgErro("Ocorreu algo inesperado", descricao);
    }
    
    private void recuperarPalavraPasse() {
        mostrarProgressBar();
        
        rsltThreadTwo = autenticacaoRepository.getUserById(id);
        
        if (rsltThreadTwo == null)
            return;
        
        esconderProgressBar();
        
        String descricao = null;
        
        if (rsltThreadTwo instanceof Result.Error) {
            Result.Error<Usuario> error = (Result.Error<Usuario>) rsltThreadTwo;
            descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
        } else {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rsltThreadTwo;
            Usuario usuario = success.getData();
            
            if (usuario == null)
                descricao = "Usuário ou palavra-passe inválido(a).";
            else
                System.err.println("");
        }
        
        if (descricao != null)
            mostrarMsgErro("Ocorreu algo inesperado", descricao);
    }
    
    private void mostrarMsgErro(String title, String description) {
        Parent content;
        
        if (abstractController == null) {
            FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("error_dialog"));

            try {
                content = fXMLLoader.load();
            }catch(IOException e) {
                content = null;
            }
            
            if (content == null) return;
            
            content.setVisible(false);
            abstractController = fXMLLoader.getController();
            abstractController.getRoot().visibleProperty().addListener(this);
            root.getChildren().add(content);
        } else {
            content = abstractController.getRoot();
        }
        
        if (content != null && !content.isVisible()) {
            content.setVisible(true);
        }
    }
    
    EventHandler<Event> getEventHandler() {
        return this;
    }
    
    private Parent encontrarComponenteUIPorTypeNameEId(ObservableList<Node> children, String typeName, String id) {
        for (Node node : children) {
            String nodeTypeName = node.getClass().getTypeName(), nodeId = node.getId();
            
            if (nodeTypeName.equals(typeName) && nodeId.equals(id) && node instanceof Parent)
                return (Parent) node;
        }
        
        return null;
    }
    
    private Node encontrarComponenteUIPorTypeName(ObservableList<Node> children, String typeName) {
        for (Node node : children) {
            String nodeTypeName = node.getClass().getTypeName();
            
            if (nodeTypeName.equals(typeName))
                return node;
        }
        
        return null;
    }

    private void mostrarProgressBar() {
        alterarEstadoVisibilidadeDialog(true);
        
        Node progressIndicator = encontrarComponenteUIPorTypeName(root.getChildren(), ProgressIndicator.class.getTypeName());
        
        if (progressIndicator == null) {
            progressIndicator = new ProgressIndicator();
            root.getChildren().add(progressIndicator);
        }
        
        progressIndicator.setVisible(true);
    }
    
    private void alterarEstadoVisibilidadeDialog(boolean mostrar) {
        dialog.setVisible(mostrar);
    }

    private void esconderProgressBar() {
        Node progressIndicator = encontrarComponenteUIPorTypeName(root.getChildren(), ProgressIndicator.class.getTypeName());
        
        if (progressIndicator != null) {
            progressIndicator.setVisible(false);
        }
    }
    
    private void esconderMsgErro(EventType<? extends Event> eventType) {
        if (abstractController == null)
            return;
        
        ObservableList<Node> children = root.getChildren();

        for (Node node : children) {
            if (node.equals(abstractController.getRoot())){
                node.setVisible(false);
                break;
            }
        }
        
        alterarEstadoVisibilidadeDialog(false);
    }

    private void observarVisibilidade(Object newValue) {
        
    }
}