/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import com.gluonhq.charm.glisten.control.*;
import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.model.Usuario.Tipo;
import static edu.uem.sgh.model.Usuario.Tipo.ADMINISTRADOR;
import static edu.uem.sgh.model.Usuario.Tipo.CLIENTE;
import static edu.uem.sgh.model.Usuario.Tipo.FUNCIONARIO;
import static edu.uem.sgh.model.Usuario.Tipo.GERENTE;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.repository.quarto.QuartoRepository;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.Path;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
    private EnumMap<Usuario.Tipo, Map<Class<?>, URL>> resourcePaths;
    private EnumMap<Usuario.Tipo, Map<String, Class<?>>> resourcePathMap;
    
    @Dependency
    private ServicoRepository servicoRepository;
    
    @Dependency
    private AutenticacaoRepository autenticacaoRepository;
    
    @Dependency
    private QuartoRepository quartoRepository;
    
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

    private Set<String> getTabs(Usuario.Tipo tipo) {
        if (tipo == null || !getResourcePathMap().containsKey(tipo)) return null;
        return getResourcePathMap().get(tipo).keySet();
    }

    @SuppressWarnings("unchecked")
    public EnumMap<Usuario.Tipo, Map<String, Class<?>>> getResourcePathMap() {
        if (resourcePathMap == null) {
            resourcePathMap = new EnumMap(Usuario.Tipo.class);
            Tipo[] tipos = Tipo.values();
            
            if (tipos.length == 0) throw new RuntimeException();
            
            for (Tipo tipo : tipos) {
                if (!resourcePathMap.containsKey(tipo)) 
                    resourcePathMap.put(tipo, new HashMap<>());

                Map<String, Class<?>> map = resourcePathMap.get(tipo);
                
                String[] resources = new String[] {
                    "Funcionários", "Gerentes", "Serviços", "Hóspedes", "Quartos", "Reservas", "Check-In", "Check-Out", "Relatórios"
                };
                
                for (String resource : resources) {
                    if (tipo == ADMINISTRADOR) {
                        Class<?> clazz = null;
                        
                        switch (resource) {
                            case "Funcionários":
                                clazz = edu.uem.sgh.controller.administrador.TelaFuncionarios.class;
                                    break;
                            case "Quartos":
                                clazz = edu.uem.sgh.controller.administrador.TelaQuartos.class;
                                    break;
                            case "Gerentes":
                                clazz = edu.uem.sgh.controller.administrador.TelaGerentes.class;
                                    break;
                            case "Serviços":
                                clazz = edu.uem.sgh.controller.administrador.TelaServicos.class;
                                    break;
                            case "Hóspedes":
                                clazz = edu.uem.sgh.controller.administrador.TelaHospedes.class;
                                    break;
                            case "Check-In":
                                clazz = edu.uem.sgh.controller.administrador.TelaCheckIns.class;
                                    break;
                            case "Check-Out":
                                clazz = edu.uem.sgh.controller.administrador.TelaCheckOuts.class;
                                    break;
                            case "Relatórios": 
                                clazz = edu.uem.sgh.controller.administrador.TelaRelatorios.class;
                                    break;
                            case "Reservas": 
                                clazz = edu.uem.sgh.controller.administrador.TelaReservas.class;
                                    break;
                        }
                        
                        if (clazz != null) 
                            map.put(resource, clazz);
                        
                    } else if (tipo == CLIENTE) {
                        Class<?> clazz = null;
                        
                        switch (resource) {
                            case "Reservas":
                                clazz = edu.uem.sgh.controller.cliente.TelaReservas.class;
                                    break;
                            case "Check-In":
                                clazz = edu.uem.sgh.controller.cliente.TelaCheckIns.class;
                                    break;
                            case "Check-Out":
                                clazz = edu.uem.sgh.controller.cliente.TelaCheckOuts.class;
                                    break;
                            case "Relatórios": 
                                clazz = edu.uem.sgh.controller.cliente.TelaRelatorios.class;
                                    break;
                        }
                        
                        if (clazz != null) 
                            map.put(resource, clazz);
                        
                    } else if (tipo == FUNCIONARIO) {
                        Class<?> clazz = null;
                        
                        switch (resource) {
                            case "Serviços":
                                clazz = edu.uem.sgh.controller.funcionario.TelaServicos.class;
                                    break;
                            case "Hóspedes":
                                clazz = edu.uem.sgh.controller.funcionario.TelaHospedes.class;
                                    break;
                            case "Quartos":
                                clazz = edu.uem.sgh.controller.funcionario.TelaQuartos.class;
                                    break;
                            case "Reservas":
                                clazz = edu.uem.sgh.controller.funcionario.TelaReservas.class;
                                    break;
                            case "Check-In":
                                clazz = edu.uem.sgh.controller.funcionario.TelaCheckIns.class;
                                    break;
                            case "Check-Out":
                                clazz = edu.uem.sgh.controller.funcionario.TelaCheckOuts.class;
                                    break;
                            case "Relatórios": 
                                clazz = edu.uem.sgh.controller.funcionario.TelaRelatorios.class;
                                    break;
                        }
                        
                        if (clazz != null) 
                            map.put(resource, clazz);
                        
                    } else if (tipo == GERENTE) {
                        Class<?> clazz = null;
                        
                        switch (resource) {
                            case "Funcionários":
                                clazz = edu.uem.sgh.controller.gerente.TelaFuncionarios.class;
                                    break;
                            case "Serviços":
                                clazz = edu.uem.sgh.controller.gerente.TelaServicos.class;
                                    break;
                            case "Hóspedes":
                                clazz = edu.uem.sgh.controller.gerente.TelaHospedes.class;;
                                    break;
                            case "Quartos":
                                clazz = edu.uem.sgh.controller.gerente.TelaQuartos.class;;
                                    break;
                            case "Reservas":
                                clazz = edu.uem.sgh.controller.gerente.TelaReservas.class;;
                                    break;
                            case "Check-In":
                                clazz = edu.uem.sgh.controller.gerente.TelaCheckIns.class;;
                                    break;
                            case "Check-Out":
                                clazz = edu.uem.sgh.controller.gerente.TelaCheckOuts.class;;
                                    break;
                            case "Relatórios": 
                                clazz = edu.uem.sgh.controller.gerente.TelaRelatorios.class;;
                                    break;
                        }
                        
                        if (clazz != null) 
                            map.put(resource, clazz);
                        
                    }
                }
                    
                resourcePathMap.put(tipo, map);
            }
        }
        
        return resourcePathMap;
    }
    
    @SuppressWarnings("unchecked")
    private EnumMap<Usuario.Tipo, Map<Class<?>, URL>> getResourcePaths() {
        if (resourcePaths == null) {
            resourcePaths = new EnumMap(Usuario.Tipo.class);
            Tipo[] tipos = Tipo.values();
            
            if (tipos.length == 0) throw new RuntimeException();
            
            for (Tipo tipo : tipos) {
                String tipoStr = tipo.toString().toLowerCase(), resourcePathStr = Path.getResourcePathByUserType(tipo, tipoStr);
                
                if (resourcePathStr == null) 
                    continue;
                
                Map<Class<?>, URL> resourcePath = Path.getResourcesPathURL(resourcePathStr, tipoStr);
                resourcePaths.put(tipo, resourcePath);
            }
        }
        
        return resourcePaths;
    }

    public void setParentMouseEventHandler(EventHandler<MouseEvent> parentMouseEventHandler) {
        this.parentMouseEventHandler = parentMouseEventHandler;
    }
    
    private void definirMenu(Set<String> tabs) {
        int size = tabs.size(), i = 0;
        inicializarTabContentsControllers(size);
        
        for (String tabName : tabs) {
            Node tabContent;
            
            try {
                tabContent = createTabContentByName(tabName, i);
            } catch (Exception e) {
                continue;
            }
            
            i++;
            tabPane.getTabs().add(new Tab(tabName, tabContent));
        }
    }
    
    private URL getResourcePath(String name) {
        if (!getResourcePaths().containsKey(usuario.getTipo()) || !getResourcePathMap().get(usuario.getTipo()).containsKey(name)) throw new RuntimeException();
        return getResourcePaths().get(usuario.getTipo()).get(getResourcePathMap().get(usuario.getTipo()).get(name));
    }
    
    private FXMLLoader getFXMLoader(String name) {
        URL resourcePath = getResourcePath(name);
        
        if (resourcePath == null)
            return null;
        else
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