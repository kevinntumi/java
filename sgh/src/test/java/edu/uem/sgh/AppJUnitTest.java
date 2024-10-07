/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.uem.sgh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Kevin Ntumi
 */
public class AppJUnitTest extends Application implements ChangeListener<Bounds> {
    private HBox hBox = null;
    private ImageView imageView;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public static void main(String[] args) throws Exception {
        Application.launch(AppJUnitTest.class, args);
    }
    
    @Override
    public void stop() throws Exception {
        super.stop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if (hBox != null) hBox.layoutBoundsProperty().removeListener(this);
        hBox = null;
        imageView = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        root = fxmlLoader.load();
        hBox = getFirstHBoxChild(root);
        imageView = getFirstImageView(root);
        hBox.layoutBoundsProperty().addListener(this);
        stage = primaryStage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public HBox getFirstHBoxChild(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof HBox) return (HBox) node;
            if (node instanceof Parent) return getFirstHBoxChild((Parent) node);
        }
        
        return null;
    }
    
    public ImageView getFirstImageView(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof ImageView) return (ImageView) node;
            if (node instanceof Parent) return getFirstImageView((Parent) node);
        }
        
        return null;
    }

    @Override
    public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
        if (newValue.getHeight() == 0 || imageView.getImage() != null) return;
        
        double dimension = newValue.getHeight();
        Image image;
        
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\img.png"));
            image = new Image(fileInputStream, dimension, dimension, true, true);
        } catch(FileNotFoundException | NullPointerException e) {
            System.err.println(e);
            image = null;
        }
        
        if (image == null) return;
        
        imageView.setImage(image);
        observable.removeListener(this);
    }
}