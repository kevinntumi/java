package edu.uem.sgh;

import atlantafx.base.theme.*;
import edu.uem.sgh.connection.DatabaseConnection;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.TelaLogin;
import edu.uem.sgh.controller.TelaMenuPrincipal;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application implements EventHandler<MouseEvent>, ChangeListener<Object> {
    private double xOffset = 0, yOffset = 0;
    private List<AbstractController> controllers;
    private DatabaseConnection databaseConnection;
    private AutenticacaoRepository autenticacaoRepository;
    private SimpleObjectProperty<Usuario> usuarioProperty;
    private Result<Usuario> r;
    private final String closeButtonId = "close", minimizeButtonId = "minimize";
    private Stage stage;

    @Override
    public void init() throws Exception {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        try {
            getDatabaseConnection().initLocalConnection();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        try {
            getDatabaseConnection().initRemoteConnection();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        if (getDatabaseConnection().areAllConnectionsUnnavailable()) {
            Platform.runLater(() -> {
                new Alert(AlertType.ERROR, "",ButtonType.APPLY).show();
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        getUsuarioProperty().addListener(this);
        
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setIdTipo(0);
        usuario.setDataInicio(System.currentTimeMillis());
        usuario.setDataRegisto(System.currentTimeMillis());
        usuario.setTipo(Usuario.Tipo.GERENTE);
        getUsuarioProperty().set(usuario);
        
        //verificarUsuarioAutenticado();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        shutDown();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (!(event.getSource() instanceof ImageView)) return;
            
            ImageView imageView = (ImageView) event.getSource();
            
            if (!(closeButtonId.equals(imageView.getId()) || minimizeButtonId.equals(imageView.getId()))) return;
            
            if (closeButtonId.equals(imageView.getId()))
                Platform.exit();
            else
                stage.setIconified(true);
        }
    }

    private void removerListeners() {
        for (AbstractController abstractController : controllers) {
            abstractController.removerListeners();
            removeMousePressedAndDraggedListener(abstractController.getRoot());
        }
        
        controllers.clear();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable.equals(getUsuarioProperty())) {
            FXMLLoader fxmlLoader;
            Scene scene = stage.getScene();
            AbstractController newController = null;
            Usuario usuario = (Usuario) newValue;
            
            if (Usuario.isVazio(usuario)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("TelaLogin.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("tela_menu_principal.fxml"));
            }
            
            if (scene == null) {
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    return;
                }
                
                newController = fxmlLoader.getController();
                fxmlLoader.setController(fxmlLoader);
                getControllers().add(newController);
                addMousePressedAndDraggedListener(scene.getRoot());
            }
            
            if (stage.getScene() == null) {
                stage.sceneProperty().addListener(this);
                stage.setScene(scene);
            } else {
                removeMousePressedAndDraggedListener(scene.getRoot());
                
                AbstractController current = getController(scene.getRoot());
                
                if (current != null) getControllers().remove(current);
                
                if (containsController(newController)) getControllers().add(newController);
                
                stage.getScene().rootProperty().removeListener(this);
                stage.getScene().rootProperty().addListener(this);
                
                try {
                    stage.getScene().setRoot(fxmlLoader.load());
                } catch (IOException ex) {
                    return;
                }
            }
            
            if (!stage.isShowing()) stage.show();  
            
        } else if (observable.equals(stage.sceneProperty())) {
            Scene pastScene = null, currentScene = (Scene) newValue;
            AbstractController pastController, currentController = getController(currentScene.getRoot());
            
            if (oldValue != null) pastScene = (Scene) oldValue;
            
            if (pastScene != null) {
                pastController = getController(pastScene.getRoot());
                
                if (pastController != null){
                    resolverDependencias(pastController, false);
                }
            }
            
            if (currentController != null) {
                resolverDependencias(currentController, true);
            }
            
            stage.getScene().rootProperty().addListener(this);
            observable.removeListener(this);
            
        } else if (observable.equals(stage.getScene().rootProperty())) {
            AbstractController pastController, currentController = getController((Parent) newValue);
            System.out.println("fuck");
            
            if (oldValue != null) {
                pastController = getController((Parent) oldValue);
                
                if (pastController != null) {
                    resolverDependencias(pastController, false);
                }
            }
            
            if (currentController != null) {
                resolverDependencias(currentController, true);
            }
        }
    }
    
    private void resolverDependencias(AbstractController abstractController, boolean add) {
        if (add) {
            if (abstractController.getUiClassID().equals(TelaLogin.class.getTypeName())) {
                TelaLogin telaLogin = (TelaLogin) abstractController;
                telaLogin.setParentMouseEventHandler(getMouseEventHandler());
                telaLogin.setUsuarioProperty(getUsuarioProperty());
                telaLogin.setAutenticacaoRepository(getAutenticacaoRepository());
            } else if (abstractController.getUiClassID().equals(TelaMenuPrincipal.class.getTypeName())) {
                TelaMenuPrincipal telaMenuPrincipal = (TelaMenuPrincipal) abstractController;
                telaMenuPrincipal.setParentMouseEventHandler(getMouseEventHandler());
                telaMenuPrincipal.setLocalConnection(getDatabaseConnection().getLocalConnection());
                telaMenuPrincipal.setRemoteConnection(getDatabaseConnection().getRemoteConnection());
                telaMenuPrincipal.setAutenticacaoRepository(getAutenticacaoRepository());
                telaMenuPrincipal.changed(getUsuarioProperty(), getUsuarioProperty().get(), getUsuarioProperty().get());
            }
            
            abstractController.adicionarListeners();
        } else {
            abstractController.removerListeners();
            
            if (abstractController.getUiClassID().equals(TelaLogin.class.getTypeName())) {
                TelaLogin telaLogin = (TelaLogin) abstractController;
                telaLogin.setParentMouseEventHandler(null);
                telaLogin.setUsuarioProperty(null);
                telaLogin.setAutenticacaoRepository(null);
            } else if (abstractController.getUiClassID().equals(TelaMenuPrincipal.class.getTypeName())) {
                TelaMenuPrincipal telaMenuPrincipal = (TelaMenuPrincipal) abstractController;
                telaMenuPrincipal.setParentMouseEventHandler(null);
                telaMenuPrincipal.setAutenticacaoRepository(null);
                telaMenuPrincipal.setLocalConnection(null);
                telaMenuPrincipal.setRemoteConnection(null);
                getUsuarioProperty().removeListener(telaMenuPrincipal);
            }   
        }
    }

    public List<AbstractController> getControllers() {
        if (controllers == null) controllers = new ArrayList<>();
        return controllers;
    }
    
    private AbstractController getController(Parent root) {
        for (AbstractController abstractController : getControllers()) {
            if (root.equals(abstractController.getRoot())) return abstractController;
        }
        
        return null;
    }
    
    private boolean containsController(AbstractController controllerToLookFor) {
        for (AbstractController abstractController : getControllers()) {
            if (abstractController.equals(controllerToLookFor)) return true;
        }
        
        return false;
    }
 
    private void removeMousePressedAndDraggedListener(Parent parent) {
        if (parent.getOnMousePressed() != null) parent.setOnMousePressed(null);
        if (parent.getOnMouseDragged() != null) parent.setOnMouseDragged(null);
    }
    
    private void addMousePressedAndDraggedListener(Parent parent) {
        if (parent.getOnMousePressed() == null) parent.setOnMousePressed(this);
        if (parent.getOnMouseDragged() == null) parent.setOnMouseDragged(this);
    }

    private DatabaseConnection getDatabaseConnection() {
        if (databaseConnection == null) databaseConnection = new DatabaseConnection();
        return databaseConnection;
    }

    private void shutDown() {
        removerListeners();
        getDatabaseConnection().closeAllConnections();
        getUsuarioProperty().removeListener(this);
    }

    public SimpleObjectProperty<Usuario> getUsuarioProperty() {
        if (usuarioProperty == null) usuarioProperty = new SimpleObjectProperty<>();
        return usuarioProperty;
    }

    private void verificarUsuarioAutenticado() {
        r = getAutenticacaoRepository().getCurrentUser();
        
        Result.Success<Usuario> success;
        
        try {
            success = (Result.Success<Usuario>) r;
        } catch (Exception e) {
            success = null;
        }
        
        if (success != null && success.getData() != null)
            getUsuarioProperty().set(success.getData());
    }
    
    private AutenticacaoRepository getAutenticacaoRepository() {
        if (autenticacaoRepository == null) autenticacaoRepository = new AutenticacaoRepository(databaseConnection.getRemoteConnection(), databaseConnection.getLocalConnection());
        return autenticacaoRepository;
    }
    
    EventHandler<MouseEvent> getMouseEventHandler() {
        return this;
    }
}