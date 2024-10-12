/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.uem.sgh.controller;

import com.gluonhq.charm.glisten.control.LifecycleEvent;
import edu.uem.sgh.model.DialogDetails;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Kevin Ntumi
 */
public class DialogController extends AbstractController implements Initializable, EventHandler<ActionEvent>, ChangeListener<DialogDetails> {
    @FXML
    private VBox dialog;
    
    @FXML
    private Label title;
    
    @FXML
    private Label description;
    
    @FXML
    private Button btnOK;

    private EventHandler<Event> lifecycleEventHandler;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUiClassID(getClass().getSimpleName());
    }    

    @Override
    public void adicionarListeners() {
        btnOK.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnOK.setOnAction(null);
    }

    @Override
    public Parent getRoot() {
        return dialog;
    }    

    @Override
    public void changed(ObservableValue<? extends DialogDetails> observable, DialogDetails oldValue, DialogDetails newValue) {
        title.setText(newValue.getTitle());
        description.setText(newValue.getDescription());
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Object eventSource = actionEvent.getSource();
        
        if (!(eventSource.equals(btnOK))) return;
        
        LifecycleEvent e = new LifecycleEvent(dialog, LifecycleEvent.CLOSE_REQUEST);
        lifecycleEventHandler.handle(e);
    }

    public void setLifecycleEventHandler(EventHandler<Event> lifecycleEventHandler) {
        this.lifecycleEventHandler = lifecycleEventHandler;
    }
}