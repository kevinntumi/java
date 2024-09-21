package edu.uem.sgh;

import atlantafx.base.theme.*;
import edu.uem.sgh.ui.TelaLogin;
import edu.uem.sgh.util.Detector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application implements ChangeListener<Bounds>, EventHandler<MouseEvent> {
    private String[] appThemeStyleSheet;
    private double xOffset = 0, yOffset = 0;
    private TelaLogin telaLogin;
    private Stage stage;
    private Parent root;

    @Override
    public void init() throws Exception {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Application.setUserAgentStylesheet(getTheme(Detector.isDarkMode()));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaLogin.fxml"));
        telaLogin = new TelaLogin();
        fxmlLoader.setController(telaLogin);
        root = fxmlLoader.load();
        telaLogin.getForm().layoutBoundsProperty().addListener(this);
        root.setOnMousePressed(this);
        root.setOnMouseDragged(this);
        stage = primaryStage;
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        telaLogin.getLblRecuperarPalavraPasse().setTooltip(new Tooltip("Clique aqui para recuperar a palavra-passe"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        telaLogin.getForm().layoutBoundsProperty().removeListener(this);
        root.setOnMousePressed(null);
        root.setOnMouseDragged(null);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) { 
        double heightInParent = stage.getScene().getRoot().getBoundsInParent().getHeight();
        Image image;
        
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\img.png"));
            image = new Image(fileInputStream, heightInParent, heightInParent, false, true);
        } catch(FileNotFoundException | NullPointerException e) {
            image = null;
        }
        
        if (image == null) return;
        
        telaLogin.getImg().setImage(image);
        stage.setWidth(image.getWidth() + telaLogin.getForm().getWidth());
        stage.getScene().getRoot().layout();
        observable.removeListener(this);
    }

    private String[] getAppThemeStyleSheet() {
        if (appThemeStyleSheet == null) 
            appThemeStyleSheet = new String[]{
                new PrimerLight().getUserAgentStylesheet(),
                new PrimerDark().getUserAgentStylesheet()
            };
            
        return appThemeStyleSheet;
    }
    
    public String getLightAppTheme() {
        return getAppThemeStyleSheet()[0];
    }
    
    public String getDarkAppTheme() {
        return getAppThemeStyleSheet()[1];
    }
    
    public String getTheme(boolean isDarkMode) {
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
        }
    }
}