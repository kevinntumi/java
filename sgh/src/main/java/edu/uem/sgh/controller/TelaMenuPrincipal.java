/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import com.gluonhq.charm.glisten.control.*;
import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.model.Usuario;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaMenuPrincipal extends AbstractController implements Initializable, ChangeListener<Object>, EventHandler<MouseEvent>{
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
    private EnumMap<Usuario.Tipo, Map<String, URI>> resourcePaths;
    
    @Dependency
    private EventHandler<MouseEvent> parentMouseEventHandler;

    public TelaMenuPrincipal() {
        super();
    }

    @Override
    public void adicionarListeners() {
        getCloseButton().setOnMouseClicked(parentMouseEventHandler);
        getMinimizeButton().setOnMouseClicked(parentMouseEventHandler);
    }

    @Override
    public void removerListeners() {
        getCloseButton().setOnMouseClicked(null);
        getMinimizeButton().setOnMouseClicked(null);
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (newValue == null) return; 
            
        if (newValue instanceof Usuario) {
            usuario = (Usuario) newValue;
            definirMenu(getTabs(usuario.getTipo()));
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }
    
    private ImageView getCloseButton() {
        return close;
    }

    private ImageView getMinimizeButton() {
        return minimize;
    }

    private String[] getTabs(Usuario.Tipo tipo) {
        switch (tipo) {
            case CLIENTE:
                return new String[] {
                    "Reservas", "Check-In", "Check-Out", "Relatórios"
                };
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

    private EnumMap<Usuario.Tipo, Map<String, URI>> getResourcePaths() {
        if (resourcePaths == null) {
            resourcePaths = new EnumMap(Usuario.Tipo.class);
            
            
            
        }
        
        return resourcePaths;
    }

    public void setParentMouseEventHandler(EventHandler<MouseEvent> parentMouseEventHandler) {
        this.parentMouseEventHandler = parentMouseEventHandler;
    }
    
    private void definirMenu(String[] tabs) {
        inicializarTabContentsControllers(tabs.length);
        
        for (int i = 0 ; i < tabs.length ; i++) {
            String tabName = tabs[i];
            Node tabContent;
            
            try {
                tabContent = createTabContentByName(tabName, i);
            } catch (Exception e) {
                continue;
            }
            
            tabPane.getTabs().add(new Tab(tabName, tabContent));
        }
    }
    
    private URI getResourcePath(String name) {
        if (!getResourcePaths().containsKey(usuario.getTipo()) || !getResourcePaths().get(usuario.getTipo()).containsKey(name)) throw new RuntimeException();
        return getResourcePaths().get(usuario.getTipo()).get(name);
    }
    
    private FXMLLoader getFXMLoader(String name) {
        URL resourcePath;
        
        try {
            resourcePath = getResourcePath(name).toURL();
        } catch (MalformedURLException | RuntimeException exception) {
            return null;
        }
        
        return new FXMLLoader(resourcePath);
    }
    
    private Node createTabContentByName(String name, int i) throws Exception {
        if (name == null || name.isBlank()) throw new RuntimeException();
        
        FXMLLoader loader = getFXMLoader(name);
        
        if (loader == null) throw new RuntimeException();
        
        Node node = loader.load();
        tabContentControllers[i] = loader.getController();
        return node;
    }
    
    private Node getTabContentAt(int index) {
        if (index < 0 || index >= tabContentControllers.length) return null;
        Node node = tabContentControllers[index].getRoot();
        return node;
    }
    
    @Override
    protected void setUiClassID(String uiClassID) {
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

    @Override
    public void handle(MouseEvent event) {
        
    }
}