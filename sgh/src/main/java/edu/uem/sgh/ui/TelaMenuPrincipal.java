/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import com.gluonhq.charm.glisten.control.*;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Usuario;
import static edu.uem.sgh.model.Usuario.Funcao.FUNCIONARIO;
import static edu.uem.sgh.model.Usuario.Funcao.GERENTE;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaMenuPrincipal extends AbstractController implements Initializable, ChangeListener<Object>{
    @FXML
    private VBox topBar;
    
    @FXML
    private Avatar userPhoto;
    
    @FXML
    private Text userName;
    
    @FXML
    private Text userRole;
    
    @FXML
    private ListView sideBarMenu;
    
    @FXML
    private StackPane stackPane;

    public VBox getTopBar() {
        return topBar;
    }

    public Avatar getUserPhoto() {
        return userPhoto;
    }

    public Text getUserName() {
        return userName;
    }

    public Text getUserRole() {
        return userRole;
    }

    public ListView getSideBarMenu() {
        return sideBarMenu;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getSideBarMenu().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getSideBarMenu().getItems().set(0, "Tsoka");
    }

    @Override
    public void adicionarListeners() {
        
    }

    @Override
    public void removerListeners() {
        
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (newValue == null) return; 
            
        if (newValue instanceof Usuario) {
            Usuario usuario = (Usuario) newValue;
   
            switch (usuario.getFuncao()) {
                case FUNCIONARIO: 
                    break;
                case GERENTE:
                    break;
                case ADMINISTRADOR:
                    break;
            }
        }
    }

    @Override
    public Parent getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}