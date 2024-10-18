/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.funcionario.FuncionarioRepository;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaFuncionarios extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private AnchorPane root;
    
    @FXML
    private VBox vBox;
    
    @FXML
    private Pane pane;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Funcionario> tableView;
    
    @FXML
    private VBox recarregarVbox;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private Button btnRecarregar;
    
    @FXML
    private ProgressIndicator progressIndicator;
    
    private DoubleProperty recarregarVBoxOpacityProperty, vBoxOpacityProperty;
    private ReadOnlyIntegerProperty selectionIndexProperty;
    private Usuario usuario;
    private Result<List<Funcionario>> rslt;
    private Dialog<?> dialog;
    private DialogInserirFuncionario dialogInserirFuncionario;
    private List<DialogPane> dialogPanes;
    private FuncionarioRepository funcionarioRepository;
    private FadeTransition fadeTransition;
    private boolean firstTimeVisible = true;
    private EventHandler<? super KeyEvent> keyEventHandler;
    private NodeEvent nodeEvent = null;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible) {
            init();
        }
        
        selectionIndexProperty.addListener(this);
        
        if (estaVisivel(vBox)) {
            btnAdicionar.setOnAction(this);
            btnCarregar.setOnAction(this);
            btnEditar.setOnAction(this);
        }
        
        recarregarVBoxOpacityProperty.addListener(this);
        vBoxOpacityProperty.addListener(this);
        tableView.setTableMenuButtonVisible(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (!(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY))
                    return;
                
                Node intersectedNode = event.getPickResult().getIntersectedNode();
                System.out.println(intersectedNode);
                
                System.out.println();
            }
        });
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        btnEditar.setOnAction(null);
        btnCarregar.setOnAction(null);
        selectionIndexProperty.removeListener(this);
        vBoxOpacityProperty.removeListener(this);
        recarregarVBoxOpacityProperty.removeListener(this);
        
        if (fadeTransition != null) {
            fadeTransition.setOnFinished(null);
            fadeTransition.setNode(null);
        }
        
        if (nodeEvent != null)
            nodeEvent.setTarget(null);
        
        if (btnRecarregar != null)
            btnRecarregar.setOnAction(null);
        
        if (dialog != null) {
            dialog.dialogPaneProperty().removeListener(this);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnAdicionar) || source.equals(btnEditar) || source.equals(btnCarregar))) {
            if (source.equals(btnRecarregar)) {
                recarregar();
                return;
            }
            
            if (source instanceof FadeTransition && nodeEvent != null) {
                Node target = nodeEvent.getTarget();
                getFadeTransition().setOnFinished(null);
                getFadeTransition().stop();
                
                if (target == null)
                    return;
                
                if (btnRecarregar != null && target.equals(recarregarVbox) && btnRecarregar.getOnAction() == null) {
                    btnRecarregar.setOnAction(this);
                }
                
                if (vBox != null && target.equals(vBox) && btnRecarregar != null) {
                    btnRecarregar.setOnAction(null);
                }
                
                if (progressIndicator != null && target.equals(progressIndicator)) {
                    init();
                }
                
                getFadeTransition().setNode(target);
                getFadeTransition().setFromValue(target.getOpacity());
                getFadeTransition().setToValue(1.0);
                getFadeTransition().playFromStart();
            }
        
            return;
        }
        
        if (source.equals(btnAdicionar))
            adicionarFuncionario();
        else if (source.equals(btnEditar))
            editarFuncionario();
        else
            carregarFuncionarios();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (newValue instanceof Usuario) {
            usuario = (Usuario) newValue;
            return;
        }
        
        if (dialog != null && observable.equals(dialog.dialogPaneProperty())) {
            DialogPane dialogPane = (DialogPane) newValue;
            adicionarBotaoDialog(dialogPane.getClass().getTypeName()); 
            return;
        }
        
        if (observable.equals(vBoxOpacityProperty)) {
            double opacity = (double) newValue;
            
            if (opacity != 1) return;

            btnAdicionar.setOnAction(this);
            btnCarregar.setOnAction(this);
            btnEditar.setOnAction(this);

            if (pane.isVisible()) pane.setVisible(false);
        } else if (observable.equals(recarregarVBoxOpacityProperty)) {
            System.out.println("reca: " + newValue);
            double opacity = (double) newValue;
            
            if (opacity != 1) 
                return;
        
        } else if (observable.equals(selectionIndexProperty)) {
            Integer selectedIndex = (Integer) newValue;
            btnAdicionar.setDisable(temLinhaSelecionada(selectedIndex));
            btnEditar.setDisable(!temLinhaSelecionada(selectedIndex));
            
            if (keyEventHandler != null) {
                tableView.setOnKeyTyped(null);
            }
            
            if (temLinhaSelecionada(selectedIndex)) {
                tableView.setOnKeyReleased(getKeyEventHandler());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recarregarVBoxOpacityProperty = recarregarVbox.opacityProperty();
        vBoxOpacityProperty = vBox.opacityProperty();
        selectionIndexProperty = tableView.getSelectionModel().selectedIndexProperty();
        setUiClassID(getClass().getTypeName());
    }

    private void carregarFuncionarios() {
        mostrarProgresso();
        
        if (funcionarioRepository == null) {
            return;
        }
        
        rslt = funcionarioRepository.obterTodosFuncionarios();
        
        esconderProgresso();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Funcionario>> error = (Result.Error<List<Funcionario>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Funcionario>> success = (Result.Success<List<Funcionario>>) rslt;
            List<Funcionario> funcionarios = success.getData();
            
            initTabela();
            mostrarTabela();
            limparTabela();
            
            if (funcionarios.isEmpty())
                return;
            
            for (Funcionario funcionario : funcionarios) {
                edu.uem.sgh.model.table.Funcionario f = new edu.uem.sgh.model.table.Funcionario(funcionario.getId(), funcionario.getNome(), funcionario.getDataNascimento(), funcionario.getDataRegisto(), funcionario.getMorada(), funcionario.getNumBilheteIdentidade(), funcionario.getNumTelefone(), funcionario.getEmail(), funcionario.getGerente(), FuncionarioSituacao.obterPorValor(funcionario.getFuncionarioSituacao()));
                tableView.getItems().add(f);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty())
            return;
        
        tableView.getItems().clear();
    }

    public List<DialogPane> getDialogPanes() {
        if (dialogPanes == null) dialogPanes = new ArrayList<>();
        return dialogPanes;
    }

    public Dialog<?> getDialog() {
        if (dialog == null) dialog = new Dialog<>();
        return dialog;
    }

    public DialogInserirFuncionario getDialogInserirFuncionario() {
        if (dialogInserirFuncionario == null) dialogInserirFuncionario = new DialogInserirFuncionario();
        return dialogInserirFuncionario;
    }
    
    private void recarregar() {
        if (!estaVisivel(recarregarVbox)) {
            return;
        }
        
        getFadeTransition().setNode(recarregarVbox);
        getFadeTransition().setFromValue(recarregarVbox.getOpacity());
        getFadeTransition().setToValue(0);
        
        if (getFadeTransition().getOnFinished() != null) {
            getFadeTransition().setOnFinished(null);
        }
        
        getFadeTransition().setOnFinished(this);
        
        if (nodeEvent == null) {
            nodeEvent = new NodeEvent(progressIndicator);
        } else {
            nodeEvent.setTarget(progressIndicator);
        }
        
        getFadeTransition().play();
    }

    private void init() {
        if (firstTimeVisible) 
            firstTimeVisible = false;
        
        if (usuario == null || funcionarioRepository == null) 
            return;
        
        rslt = funcionarioRepository.obterTodosFuncionarios();
        
        if (rslt == null)
            return;
        
        esconderProgresso();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Funcionario>> error = (Result.Error<List<Funcionario>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarErro(descricao);
        } else {
            Result.Success<List<Funcionario>> success = (Result.Success<List<Funcionario>>) rslt;
            List<Funcionario> funcionarios = success.getData();
            
            initTabela();
            mostrarTabela();
            limparTabela();
            
            if (funcionarios.isEmpty())
                return;
            
            for (Funcionario funcionario : funcionarios) {
                edu.uem.sgh.model.table.Funcionario f = new edu.uem.sgh.model.table.Funcionario(funcionario.getId(), funcionario.getNome(), funcionario.getDataNascimento(), funcionario.getDataRegisto(), funcionario.getMorada(), funcionario.getNumBilheteIdentidade(), funcionario.getNumTelefone(), funcionario.getEmail(), funcionario.getGerente(), FuncionarioSituacao.obterPorValor(funcionario.getFuncionarioSituacao()));
                for (int i = 0 ; i < 25 ; i++) {
                    tableView.getItems().add(f);
                }
            }
        }
    }

    private void mostrarProgresso() {
        String typeName = DialogPaneProgressBar.class.getTypeName();
        DialogPane dialogPane = encontrarDialogPaneViaTypeName(typeName);
        
        if (dialogPane == null) {
            dialogPane = criarDialogPaneViaTypeName(typeName);
            getDialogPanes().add(dialogPane);
        }
        
        getDialog().setDialogPane(dialogPane);
        
        if (!getDialog().isShowing())
            getDialog().show();
    }

    private void esconderProgresso() {
        DialogPaneProgressBar dialogPaneProgressBar = (DialogPaneProgressBar) encontrarDialogPaneViaTypeName(DialogPaneProgressBar.class.getTypeName());
        
        if (getDialog().isShowing() && (dialogPaneProgressBar != null && getDialog().getDialogPane().equals(dialogPaneProgressBar)))
            getDialog().hide();
    }
    
    private void adicionarFuncionario() {
        if (usuario == null || funcionarioRepository == null)
            return;
        
        if (dialogInserirFuncionario != null) {
            getDialogInserirFuncionario().removerListeners();
        }
        
        getDialogInserirFuncionario().setFuncionarioRepository(funcionarioRepository);
        getDialogInserirFuncionario().setUsuario(usuario);
        getDialogInserirFuncionario().adicionarListeners();
        getDialogInserirFuncionario().show();
    }
    
    private DialogPane encontrarDialogPaneViaTypeName(String typeName) {
        if (typeName == null || typeName.isBlank() || getDialogPanes().isEmpty())
            return null;
        
        for (DialogPane dialogPane : getDialogPanes()) {
            if (dialogPane.getClass().getTypeName().equals(typeName))
                return dialogPane;
        }
        
        return null;
    }
    
    private DialogPane criarDialogPaneViaTypeName(String typeName) {
        if (typeName == null || typeName.isBlank())
            return null;
        
        if (typeName.equals(DialogPaneInserirFuncionario.class.getTypeName()))
            return new DialogPaneInserirFuncionario();
        else if (typeName.equals(DialogPaneEditarFuncionario.class.getTypeName()))
            return new DialogPaneEditarFuncionario();
        else if (typeName.equals(DialogPaneProgressBar.class.getTypeName()))
            return new DialogPaneProgressBar();
        else if (typeName.equals(DialogPaneMostrarMsgErro.class.getTypeName()))
            return new DialogPaneMostrarMsgErro();
        else 
            return null;
    }

    private void editarFuncionario() {
        Integer selectedIndex = selectionIndexProperty.get();
        
        if (temLinhaSelecionada(selectedIndex) || usuario == null || funcionarioRepository == null) 
            return;
        
        String typeName = DialogPaneEditarFuncionario.class.getTypeName();
        DialogPane dialogPane = encontrarDialogPaneViaTypeName(typeName);
        
        if (dialogPane == null) {
            dialogPane = criarDialogPaneViaTypeName(typeName);
            getDialogPanes().add(dialogPane);
        }
        
        getDialog().setDialogPane(dialogPane);
        getDialog().dialogPaneProperty().addListener(this);
        
        if (!getDialog().isShowing())
            getDialog().show();
    }
    
    private void mostrarMsgErro(String titulo, String descricao) {
        String typeName = DialogPaneMostrarMsgErro.class.getTypeName();
        DialogPaneMostrarMsgErro dialogPane = (DialogPaneMostrarMsgErro) encontrarDialogPaneViaTypeName(typeName);
        
        if (dialogPane == null) {
            dialogPane = (DialogPaneMostrarMsgErro) criarDialogPaneViaTypeName(typeName);
            dialogPane.setTitle(titulo);
            dialogPane.setDescription(descricao);
            getDialogPanes().add(dialogPane);
        }
        
        getDialog().setDialogPane(dialogPane);
        
        if (!getDialog().isShowing())
            getDialog().show();
    }
    
    private void mostrarErro(String descricao) {
        if (errorLabel == null) 
            return;

        errorLabel.setText(descricao);
        getFadeTransition().setNode(progressIndicator);
        getFadeTransition().setFromValue(progressIndicator.getOpacity());
        getFadeTransition().setToValue(0);
        
        if (getFadeTransition().getOnFinished() != null) {
            getFadeTransition().setOnFinished(null);
        }
        
        if (nodeEvent == null) {
            nodeEvent = new NodeEvent(recarregarVbox);
        } else {
            nodeEvent.setTarget(recarregarVbox);
        }
        
        getFadeTransition().setOnFinished(this);
        getFadeTransition().play();
    }
    
    private boolean temLinhaSelecionada(Integer selectedIndex) {
        return selectedIndex != -1;
    }

    public void setFuncionarioRepository(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void mostrarTabela() {
        if (estaVisivel(vBox) || !estaVisivel(pane)) {
            return;
        }
        
        getFadeTransition().setNode(pane);
        getFadeTransition().setFromValue(pane.getOpacity());
        getFadeTransition().setToValue(0);
        
        if (getFadeTransition().getOnFinished() != null) {
            getFadeTransition().setOnFinished(null);
        }
        
        if (nodeEvent == null) {
            nodeEvent = new NodeEvent(vBox);
        } else {
            nodeEvent.setTarget(vBox);
        }
        
        getFadeTransition().setOnFinished(this);
        getFadeTransition().play();
    }
    
    private boolean estaVisivel(Node node) {
        return node.getOpacity() != 0;
    }

    public FadeTransition getFadeTransition() {
        if (fadeTransition == null) {
            fadeTransition = new FadeTransition();
            fadeTransition.setAutoReverse(false);
            fadeTransition.setCycleCount(1);
        }
        return fadeTransition;
    }

    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Funcionario, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Funcionario, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnNome":
                    propertyValueFactory = new PropertyValueFactory("nome");
                        break;
                case "tblColumnDataNasc":
                    propertyValueFactory = new PropertyValueFactory("dataNascimento");
                        break;
                case "tblColumnDataReg":
                    propertyValueFactory = new PropertyValueFactory("dataRegisto");
                        break;  
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
                case "tblColumnEmail":
                    propertyValueFactory = new PropertyValueFactory("email");
                        break;
                case "tblColumnMorada":
                    propertyValueFactory = new PropertyValueFactory("morada");
                        break;
                case "tblColumnNumTelefone":
                    propertyValueFactory = new PropertyValueFactory("numTelefone");
                        break;
                case "tblColumnNumBilheteIdentidade":
                    propertyValueFactory = new PropertyValueFactory("numBilheteIdentidade");
                        break;
                case "tblColumnGerente":
                    propertyValueFactory = new PropertyValueFactory("gerente");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    public EventHandler<? super KeyEvent> getKeyEventHandler() {
        if (keyEventHandler == null)
            keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event == null)
                    return;
                
                Object source = event.getSource();
                
                if (source == null || event.getCode() != KeyCode.ESCAPE || !temLinhaSelecionada(selectionIndexProperty.get()))
                    return;
                
                tableView.getSelectionModel().clearSelection();
            }
        };
           
        return keyEventHandler;
    }

    private void adicionarBotaoDialog(String typeName) {
        if (typeName == null || typeName.isBlank() || !(typeName.equals(DialogPaneInserirFuncionario.class.getTypeName()) || typeName.equals(DialogPaneEditarFuncionario.class.getTypeName()))) {
            return;
        }
        
        
    }
    
    private class NodeEvent {
        private Node target;

        public NodeEvent(Node target) {
            this.target = target;
        }

        public Node getTarget() {
            return target;
        }

        public void setTarget(Node target) {
            this.target = target;
        }
    }
}