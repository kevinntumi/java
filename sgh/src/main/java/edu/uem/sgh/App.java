package edu.uem.sgh;

import atlantafx.base.theme.*;
import edu.uem.sgh.connection.DatabaseConnection;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.util.Detector;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RunnableScheduledFuture;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
    private List<? extends AbstractController> controllers;
    private DatabaseConnection databaseConnection;
    private AutenticacaoRepository autenticacaoRepository;
    private SimpleObjectProperty<Usuario> usuarioProperty;
    private Task<Result<Usuario>> tarefaBuscarUsuario;
    private ReadOnlyDoubleProperty progressoTarefaBuscarUsuario;
    private Thread backgroundThread;
    private Stage stage;

    @Override
    public void init() throws Exception {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Application.setUserAgentStylesheet(getTheme(Detector.isDarkMode()));
       
        try {
            getDatabaseConnection().initLocalConnection();
        } catch (Exception e) {
            Platform.runLater(() -> {
                new Alert(AlertType.ERROR, "",ButtonType.APPLY).show();
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        getUsuarioProperty().addListener(this);
        verificarUsuarioAutenticado();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaLogin.fxml"));
        stage = primaryStage;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(fxmlLoader.load()));
        addMouseEventListener(stage.getScene().getRoot());
        stage.getScene().rootProperty().addListener(this);
        stage.show();
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
            removeMouseEventListener(abstractController.getRoot());
        }
        
        controllers.clear();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (oldValue != null && oldValue instanceof Parent) {
            Parent parent = (Parent) oldValue;
            removeMouseEventListener(parent);
        }
        
        if (newValue == null) return;
        
        if (newValue instanceof Parent) {
            Parent parent = (Parent) newValue;          
            addMouseEventListener(parent);
            
            switch (parent.getId()) {
                case "":
                    break;  
            }
            
            return;
        }
        
        if (newValue instanceof Usuario) {
            Usuario usuario = (Usuario) newValue;
            
            if (usuario == null) {
                
            }
        }
        
        System.out.println("tsoka " + newValue);
        
        if (observable.equals(progressoTarefaBuscarUsuario)) {
            System.out.println("tsoka " + newValue);
            
            observable.removeListener(this);
        }
    }
    
    private void removeMouseEventListener(Parent parent) {
        if (parent.getOnMousePressed() != null) parent.setOnMousePressed(null);
        if (parent.getOnMouseDragged() != null) parent.setOnMouseDragged(null);
    }
    
    private void addMouseEventListener(Parent parent) {
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
        if (tarefaBuscarUsuario != null && tarefaBuscarUsuario.isRunning()) tarefaBuscarUsuario.cancel(true);
        if (backgroundThread != null) backgroundThread.interrupt();
        if (progressoTarefaBuscarUsuario != null) progressoTarefaBuscarUsuario.removeListener(this); 
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
        
        progressoTarefaBuscarUsuario = tarefaBuscarUsuario.progressProperty();
        progressoTarefaBuscarUsuario.addListener(this);
        backgroundThread = new Thread(tarefaBuscarUsuario);
        backgroundThread.start();
    }
    
    private AutenticacaoRepository getAutenticacaoRepository() {
        if (autenticacaoRepository == null) autenticacaoRepository = new AutenticacaoRepository(databaseConnection.getRemoteConnection(), databaseConnection.getLocalConnection());
        return autenticacaoRepository;
    }
}