/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.ui;

import javafx.fxml.FXML;
import com.gluonhq.charm.glisten.control.*;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Usuario;
import static edu.uem.sgh.model.Usuario.Funcao.ADMINISTRADOR;
import static edu.uem.sgh.model.Usuario.Funcao.FUNCIONARIO;
import static edu.uem.sgh.model.Usuario.Funcao.GERENTE;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaMenuPrincipal extends AbstractController implements Initializable, ChangeListener<Object>{
    @FXML
    private Avatar fotoPerfil;
    
    @FXML
    private ImageView close;
    
    @FXML
    private ImageView minimize;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private TabPane tabPane;
    
    private AbstractController[] tabContentControllers;
    
    private Usuario usuario;

    public TelaMenuPrincipal() {
        super();
    }

    public Avatar getFotoPerfil() {
        return fotoPerfil;
    }

    public ImageView getClose() {
        return close;
    }

    public ImageView getMinimize() {
        return minimize;
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
            usuario = (Usuario) newValue;
            definirMenu(getTabs(usuario.getFuncao()));
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    public String[] getTabs(Usuario.Funcao funcao) {
        switch (funcao) {
            case FUNCIONARIO: 
                return new String[] {
                    "Serviços", "Hóspedes", "Quartos", "Reservas", "Check-In", "Check-Out", "Relatórios"
                };
            case GERENTE:
                return new String[] {
                    "Funcionários", "Serviços", "Hóspedes", "Quartos", "Reservas", "Check-In", "Check-Out", "Relatórios"
                };
            case ADMINISTRADOR:
                return new String[] {
                    "Funcionários", "Gerentes", "Serviços", "Hóspedes", "Quartos", "Reservas", "Check-In", "Check-Out", "Relatórios"
                };
            default:
                return null;
        }
    }
    
    public void definirMenu(String[] tabs) {
        inicializarTabContentsControllers(tabs.length);
        
        for (int i = 0 ; i < tabs.length ; i++) {
            String tab = tabs[i];
            tabPane.getTabs().add(new Tab(tab, getTabContentByName(tab, i)));
        }
    }
    
    public FXMLLoader getFXMLoader(String name) {
        URL resourcePath;
        
        switch (name) {
            case "Funcionários": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Gerentes": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Serviços": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Hóspedes": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Quartos": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Reservas": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Check-In": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Check-Out": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            case "Relatórios": resourcePath = getClass().getResource("TelaLogin.fxml");
                break;
            default: 
                return null;
        }
        
        return new FXMLLoader(resourcePath);
    }
    
    public Node getTabContentByName(String name, int i) {
        if (name == null || name.isBlank()) return null;
        
        Node node;
        FXMLLoader loader = getFXMLoader(name);
        
        try {
            node = loader.load();
        } catch (IOException e) {
            node = null;
        }
        
        tabContentControllers[i] = loader.getController();
        return node;
    }
    
    public Node getTabContentAt(int index) {
        if (index < 0 || index >= tabContentControllers.length) return null;
        Node node = tabContentControllers[index].getRoot();
        return node;
    }
    
    @Override
    public void setUiClassID(String uiClassID) {
        super.setUiClassID(uiClassID); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getUiClassID() {
        return super.getUiClassID(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getSimpleName());
    }

    private void inicializarTabContentsControllers(int size) {
        if (tabContentControllers != null) return;
        tabContentControllers = new AbstractController[size];
    }
}