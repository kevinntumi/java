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
import java.util.Set;
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
public class App extends Application implements EventHandler<MouseEvent> {
    private double xOffset = 0, yOffset = 0;
    private List<AbstractController> controllers;
    private DatabaseConnection databaseConnection;
    private AutenticacaoRepository autenticacaoRepository;
    private SimpleObjectProperty<Usuario> usuarioProperty;
    private Result<Usuario> rslt;
    private final String closeButtonId = "close", minimizeButtonId = "minimize";
    private Set<String> allowedClasses;
    private ChangeListener<Object> changeListener;
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
            new Alert(AlertType.ERROR, "",ButtonType.APPLY).show();
        }
        
        changeListener = (ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            if (!(observable.equals(getUsuarioProperty()) || observable.equals(stage.sceneProperty()))) {
                return;
            }
            
            if (observable.equals(getUsuarioProperty()))
                observarUsuarioProperty(newValue);
            else
                observarSceneProperty(oldValue, newValue);
        };
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.sceneProperty().addListener(changeListener);
        getUsuarioProperty().addListener(changeListener);
        verificarUsuarioAutenticado();
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
            if (!(event.getSource() instanceof ImageView)) {
                return;
            }
            
            ImageView imageView = (ImageView) event.getSource();
            
            if (!(closeButtonId.equals(imageView.getId()) || minimizeButtonId.equals(imageView.getId()))) {
                return;
            }
            
            if (closeButtonId.equals(imageView.getId()))
                Platform.exit();
            else
                stage.setIconified(true);
        }
    }

    private void removerListeners() {
        for (AbstractController abstractController : controllers) {
            resolverDependencias(abstractController, false);
            removeMousePressedAndDraggedListener(abstractController.getRoot());
        }
        
        controllers.clear();
    }
    
    private void resolverDependencias(AbstractController abstractController, boolean add) {
        if (abstractController == null || !(getAllowedClasses().contains(abstractController.getUiClassID()))) {
            return;
        }
        
        if (!add) {
            abstractController.removerListeners();
        }
        
        if (abstractController.getUiClassID().equals(TelaLogin.class.getTypeName()))
            resolverDependenciasTelaLogin((TelaLogin) abstractController, add);
        else if (abstractController.getUiClassID().equals(TelaMenuPrincipal.class.getTypeName())) 
            resolverDependenciasTelaMenuPrincipal((TelaMenuPrincipal) abstractController, add);
        
        if (add) {
            abstractController.adicionarListeners();
        }
    }
    
    private void resolverDependenciasTelaMenuPrincipal(TelaMenuPrincipal telaMenuPrincipal, boolean add) {
        if (telaMenuPrincipal == null) {
            return;
        }
   
        telaMenuPrincipal.setParentMouseEventHandler((add) ? getMouseEventHandler() : null);
        telaMenuPrincipal.setAutenticacaoRepository((add) ? getAutenticacaoRepository() : null);
        telaMenuPrincipal.setRemoteConnection((add) ? getDatabaseConnection().getRemoteConnection() : null);
        telaMenuPrincipal.setLocalConnection((add) ? getDatabaseConnection().getLocalConnection() : null);
        
        if (add) {
            telaMenuPrincipal.changed(getUsuarioProperty(), null, getUsuarioProperty().get());
        }
    }
    
    private void resolverDependenciasTelaLogin(TelaLogin telaLogin, boolean add) {
        if (telaLogin == null) {
            return;
        }
        
        telaLogin.setParentMouseEventHandler((add) ? getMouseEventHandler() : null);
        telaLogin.setUsuarioProperty((add) ? getUsuarioProperty() : null);
        telaLogin.setAutenticacaoRepository((add) ? getAutenticacaoRepository() : null);
    }

    public List<AbstractController> getControllers() {
        if (controllers == null) {
            controllers = new ArrayList<>();
        }
        
        return controllers;
    }
    
    private AbstractController getController(Parent root) {
        for (AbstractController abstractController : getControllers()) {
            if (root.equals(abstractController.getRoot())) {
                return abstractController;
            }
        }
        
        return null;
    }
 
    private void removeMousePressedAndDraggedListener(Parent parent) {
        if (parent.getOnMousePressed() != null) {
            parent.setOnMousePressed(null);
        }
        
        if (parent.getOnMouseDragged() != null) {
            parent.setOnMouseDragged(null);
        }
    }
    
    private void addMousePressedAndDraggedListener(Parent parent) {
        if (parent.getOnMousePressed() == null) {
            parent.setOnMousePressed(this);
        }
        
        if (parent.getOnMouseDragged() == null) {
            parent.setOnMouseDragged(this);
        }
    }

    private DatabaseConnection getDatabaseConnection() {
        if (databaseConnection == null) {
            databaseConnection = new DatabaseConnection();
        }
        
        return databaseConnection;
    }

    private void shutDown() {
        removerListeners();
        getDatabaseConnection().closeAllConnections();
        getUsuarioProperty().removeListener(changeListener);
        stage.sceneProperty().removeListener(changeListener);
    }
 
    private Set<String> getAllowedClasses() {
        if (allowedClasses == null) {
            allowedClasses = Set.of(TelaLogin.class.getTypeName(), TelaMenuPrincipal.class.getTypeName());
        }
        
        return allowedClasses;
    }

    public SimpleObjectProperty<Usuario> getUsuarioProperty() {
        if (usuarioProperty == null) {
            usuarioProperty = new SimpleObjectProperty<>();
        }
        
        return usuarioProperty;
    }

    private void verificarUsuarioAutenticado() {
        rslt = getAutenticacaoRepository().getCurrentUser();
        
        if (rslt == null) {
            return;
        }
        
        Usuario usuario = null;
        
        if (rslt instanceof Result.Success) {
            Result.Success<Usuario> success = (Result.Success<Usuario>) rslt;
            usuario = success.getData();
        }
        
        if (usuario != null)
            getUsuarioProperty().set(usuario); 
        else
            observarUsuario(usuario);
    }
    
    private AutenticacaoRepository getAutenticacaoRepository() {
        if (autenticacaoRepository == null) {
            autenticacaoRepository = new AutenticacaoRepository(databaseConnection.getRemoteConnection(), databaseConnection.getLocalConnection());
        }
        
        return autenticacaoRepository;
    }
    
    private EventHandler<MouseEvent> getMouseEventHandler() {
        return this;
    }

    private void observarUsuario(Usuario usuario) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource((usuario == null) ? "tela_login.fxml" : "tela_menu_principal.fxml"));
        AbstractController controller = (usuario == null) ? new TelaLogin() : new TelaMenuPrincipal();
        fxmlLoader.setController(controller);
        Scene toBeScene;
        
        try {
            toBeScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            return;
        }
        
        getControllers().add(controller);
        stage.setScene(toBeScene);
        
        if (!stage.isShowing()) {
            stage.show();
        }
    }
    
    private void observarUsuarioProperty(Object newValue) {
        Usuario usuario = null;

        if (newValue != null) {
            try {
                usuario = (Usuario) newValue;
            } catch (Exception e){
                usuario = null;
            }
        }

        observarUsuario(usuario);
    }

    private void observarSceneProperty(Object oldValue, Object newValue) {
        Scene oldScene;
        
        if (oldValue != null) {
            try {
                oldScene = (Scene) oldValue;
            } catch (Exception e) {
                oldScene = null;
            }
            
            if (oldScene != null) {
                AbstractController abstractController = getController(oldScene.getRoot());
            
                if (abstractController != null) {
                    resolverDependencias(abstractController, false);
                    getControllers().remove(abstractController);
                }

                removeMousePressedAndDraggedListener(oldScene.getRoot());
            }
        }
        
        Scene newScene = null;

        if (newValue != null) {
            try {
                newScene = (Scene) newValue;
            } catch (Exception e) {
                newScene = null;
            }
        }

        if (newScene == null) {
            return;
        }
        
        AbstractController abstractController = getController(newScene.getRoot());
            
        if (abstractController != null) {
            resolverDependencias(abstractController, true);
        }
        
        addMousePressedAndDraggedListener(newScene.getRoot());
    }
}