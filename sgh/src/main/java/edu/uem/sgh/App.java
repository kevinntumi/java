package edu.uem.sgh;

import atlantafx.base.theme.*;
import edu.uem.sgh.connection.DatabaseConnection;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.TelaLogin;
import edu.uem.sgh.controller.TelaMenuPrincipal;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.util.Detector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
    private String[] appThemeStyleSheet;
    private double xOffset = 0, yOffset = 0;
    private List<AbstractController> controllers;
    private DatabaseConnection databaseConnection;
    private AutenticacaoRepository autenticacaoRepository;
    private SimpleObjectProperty<Usuario> usuarioProperty;
    private Task<Result<Usuario>> tarefaBuscarUsuario;
    private Result<Usuario> r;
    private Thread backgroundThread;
    private Stage stage;

    @Override
    public void init() throws Exception {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Application.setUserAgentStylesheet(getTheme(Detector.isDarkMode()));
        
        try {
            getDatabaseConnection().initLocalConnection();
        } catch (Exception e) {
            System.err.println(e);
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

    private String[] getAppThemeStyleSheet() {
        if (appThemeStyleSheet == null) 
            appThemeStyleSheet = new String[]{
                new PrimerLight().getUserAgentStylesheet(),
                new PrimerDark().getUserAgentStylesheet()
            };
            
        return appThemeStyleSheet;
    }
    
    private String getLightAppTheme() {
        return getAppThemeStyleSheet()[0];
    }
    
    private String getDarkAppTheme() {
        return getAppThemeStyleSheet()[1];
    }
    
    private String getTheme(boolean isDarkMode) {
        return (isDarkMode) ? getDarkAppTheme() : getLightAppTheme();
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
            
            switch (imageView.getId()) {
                case "close": Platform.exit();
                    break;
                case "minimize": stage.setIconified(true);
                    break;
            }
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
        System.out.println(observable);
        
        if (observable.equals(getUsuarioProperty())) {
            FXMLLoader fxmlLoader = new FXMLLoader((newValue == null) ? getClass().getResource("TelaLogin.fxml") : getClass().getResource("tela_menu_principal.fxml"));
            AbstractController newController = (newValue == null) ? new TelaLogin() : new TelaMenuPrincipal();
            Scene scene = stage.getScene();

            if (scene == null) {
                fxmlLoader.setController(newController);
                getControllers().add(newController);
                
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    return;
                }
                
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
            
            if (oldValue != null) pastScene = null;
            
            if (pastScene != null) {
                pastController = getController(pastScene.getRoot());
                
                if (pastController != null) {
                    pastController.removerListeners();
                }
            }
            
            if (currentController != null) {
                System.out.println("UIClassID: " + currentController.getUiClassID());

                switch (currentController.getUiClassID()) {
                    case "TelaMenuPrincipal":
                        break;
                    case "TelaLogin":
                        break;
                }
            }
            
            stage.getScene().rootProperty().addListener(this);
            observable.removeListener(this);
            
        } else if (observable.equals(stage.getScene().rootProperty())) {
            
        }
    }
    
    private void resolverDependencias(AbstractController abstractController, boolean add) {
        if (abstractController.getUiClassID() == null) return;
        
        if (add) {
            abstractController.adicionarListeners();
            
            if (abstractController.getUiClassID().equals("TelaLogin")) {
                TelaLogin telaLogin = (TelaLogin) abstractController;
                telaLogin.setParentMouseEventHandler(getMouseEventHandler());
            } else if (abstractController.getUiClassID().equals("TelaMenuPrincipal")) {
                TelaMenuPrincipal telaMenuPrincipal = (TelaMenuPrincipal) abstractController;
                telaMenuPrincipal.setParentMouseEventHandler(getMouseEventHandler());
            }
        } else {
            abstractController.removerListeners();
            
            if (abstractController.getUiClassID().equals("TelaLogin")) {
                TelaLogin telaLogin = (TelaLogin) abstractController;
            } else if (abstractController.getUiClassID().equals("TelaMenuPrincipal")) {
                TelaMenuPrincipal telaMenuPrincipal = (TelaMenuPrincipal) abstractController;
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
        cancelarTarefaBuscarUsuario();
    }
    
    private void cancelarTarefaBuscarUsuario() {
        if (r != null) r = null;
        if (tarefaBuscarUsuario != null && tarefaBuscarUsuario.isRunning()) tarefaBuscarUsuario.cancel(true);
        if (backgroundThread != null) backgroundThread.interrupt();
    }

    public SimpleObjectProperty<Usuario> getUsuarioProperty() {
        if (usuarioProperty == null) usuarioProperty = new SimpleObjectProperty<>();
        return usuarioProperty;
    }

    private void verificarUsuarioAutenticado() {
        tarefaBuscarUsuario = new Task<Result<Usuario>>() {
            @Override
            protected Result<Usuario> call() throws Exception {
                return getAutenticacaoRepository().getCurrentUser();
            };
        };
        
        backgroundThread = new Thread(tarefaBuscarUsuario);
        backgroundThread.start();
        
        try {
            r = tarefaBuscarUsuario.get(1500, TimeUnit.MILLISECONDS);
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
        
        if (!backgroundThread.isInterrupted()) {
            try {
                backgroundThread.interrupt();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        
        getUsuarioProperty().set((r instanceof Result.Error) ? null : ((Result.Success<Usuario>) r).getData());
    }
    
    private AutenticacaoRepository getAutenticacaoRepository() {
        if (autenticacaoRepository == null) autenticacaoRepository = new AutenticacaoRepository(databaseConnection.getRemoteConnection(), databaseConnection.getLocalConnection());
        return autenticacaoRepository;
    }
    
    EventHandler<MouseEvent> getMouseEventHandler() {
        return this;
    }
}