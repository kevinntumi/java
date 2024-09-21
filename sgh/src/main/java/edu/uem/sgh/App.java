package edu.uem.sgh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import atlantafx.base.theme.*;
import edu.uem.sgh.util.Detector;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {
    private String[] appThemeStyleSheet;
    private static Scene scene;
    private double xOffset = 0, yOffset = 0;

    @Override
    public void init() throws Exception {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Application.setUserAgentStylesheet(getTheme(Detector.isDarkMode()));
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaLogin.fxml"));
        Parent root = fxmlLoader.load();
        
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node.getId() == null) continue;
            System.out.println(node.getId());
            if (!(node instanceof Parent)) continue; 
            Parent p = (Parent) node;
            
            for (Node node1 : p.getChildrenUnmodifiable()) {
            if (node1.getId() == null) continue;
            System.out.println("child: " + node1.getId());
            }
        }
        
        scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
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
    
    public String getLightAppTheme() {
        return getAppThemeStyleSheet()[0];
    }
    
    public String getDarkAppTheme() {
        return getAppThemeStyleSheet()[1];
    }
    
    public String getTheme(boolean isDarkMode) {
        return (isDarkMode) ? getDarkAppTheme() : getLightAppTheme();
    }
}