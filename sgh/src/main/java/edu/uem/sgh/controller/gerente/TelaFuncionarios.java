/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.gerente.dialog.DialogGerirSituacao;
import edu.uem.sgh.helper.FuncionarioSituacao;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.repository.funcionario.FuncionarioRepository;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaFuncionarios extends AbstractController implements EventHandler<ActionEvent>, ChangeListener<Object>, Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Funcionario> tableView;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnCarregar;

    private ReadOnlyIntegerProperty selectionIndexProperty;
    private Usuario usuario;
    private Result<List<Funcionario>> rslt;
    private DialogInserirFuncionario dialogInserirFuncionario;
    private FuncionarioRepository funcionarioRepository;
    private boolean firstTimeVisible = true;
    private SimpleDateFormat dataNascimentoFormat = new SimpleDateFormat("dd.MM.yyyy"), dataRegistoFormat = new SimpleDateFormat("dd.MM.yyyy, HH:SS");
    private EventHandler<? super KeyEvent> keyEventHandler;
    private AutenticacaoRepository autenticacaoRepository;
    private EventHandler<MouseEvent> mouseEventHandler;
    private Funcionario selectedFuncionario = null;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible) {
            carregarFuncionarios();
        }
        
        selectionIndexProperty.addListener(this);
        btnAdicionar.setOnAction(this);
        btnCarregar.setOnAction(this);
        btnEditar.setOnAction(this);
        tableView.setOnKeyReleased(getKeyEventHandler());
        tableView.setOnMouseClicked(getMouseEventHandler());
        tableView.setOnMouseMoved(getMouseEventHandler());
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        btnEditar.setOnAction(null);
        btnCarregar.setOnAction(null);
        selectionIndexProperty.removeListener(this);
        tableView.setOnKeyReleased(null);
        tableView.setOnMouseMoved(getMouseEventHandler());
        tableView.setOnMouseMoved(null);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (!(source.equals(btnAdicionar) || source.equals(btnEditar) || source.equals(btnCarregar))) {        
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
        if (observable.equals(selectionIndexProperty)) {
            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectionIndexProperty = tableView.getSelectionModel().selectedIndexProperty();
        setUiClassID(getClass().getTypeName());
    }

    private void carregarFuncionarios() {
        if (funcionarioRepository == null) {
            return;
        }
        
        if (firstTimeVisible) 
            firstTimeVisible = false;
        
        rslt = funcionarioRepository.obterTodosFuncionarios();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Funcionario>> error = (Result.Error<List<Funcionario>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarErro(descricao);
        } else {
            Result.Success<List<Funcionario>> success = (Result.Success<List<Funcionario>>) rslt;
            List<Funcionario> funcionarios = success.getData();
            
            initTabela();
            limparTabela();
            
            if (funcionarios.isEmpty())
                return;
            
            for (Funcionario funcionario : funcionarios) {
                edu.uem.sgh.model.table.Funcionario f = new edu.uem.sgh.model.table.Funcionario(funcionario.getId(), funcionario.getNome(), dataNascimentoFormat.format(new Date(funcionario.getDataNascimento())), dataRegistoFormat.format(funcionario.getDataRegisto()), funcionario.getMorada(), funcionario.getNumBilheteIdentidade(), funcionario.getNumTelefone(), funcionario.getEmail(), funcionario.getGerente(), FuncionarioSituacao.obterPorValor(funcionario.getFuncionarioSituacao()));
                tableView.getItems().add(f);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty())
            return;
        
        tableView.getItems().clear();
    }

    public DialogInserirFuncionario getDialogInserirFuncionario() {
        if (dialogInserirFuncionario == null) dialogInserirFuncionario = new DialogInserirFuncionario();
        return dialogInserirFuncionario;
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
   
    private void editarFuncionario() {
        Integer selectedIndex = selectionIndexProperty.get();
        
        if (!temLinhaSelecionada(selectedIndex) || usuario == null || funcionarioRepository == null || selectedFuncionario == null)  {
            return;
        }
        
        DialogEditarFuncionario dialogEditarFuncionario = new DialogEditarFuncionario();
        dialogEditarFuncionario.setFuncionario(selectedFuncionario);
        dialogEditarFuncionario.setFuncionarioRepository(funcionarioRepository);
        dialogEditarFuncionario.setUsuario(usuario);
        dialogEditarFuncionario.adicionarListeners();
        dialogEditarFuncionario.show();
    }
  
    private void mostrarErro(String descricao) {
        
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

    public EventHandler<MouseEvent> getMouseEventHandler() {
        if (mouseEventHandler == null) {
            mouseEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getEventType() == MouseEvent.MOUSE_MOVED || (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && tableView.getSelectionModel().getSelectedIndex() != -1)){
                        Node intersectedNode = event.getPickResult().getIntersectedNode(), parent = intersectedNode.getParent();
                        
                        if (parent instanceof TableColumnHeader || (intersectedNode instanceof Text && parent instanceof Label)) {
                            return;
                        }

                        String parentStr = (parent == null) ? null : parent.toString();

                        if (!(intersectedNode instanceof Text && parentStr != null && parentStr.startsWith("TableColumn"))) {
                            return;
                        }

                        int start = parentStr.indexOf("id="), end = parentStr.indexOf(",");

                        if (start == -1 || end == -1 || end < start) {
                            return;
                        }

                        String id = "";
                        start += 3;

                        for (int i = start ; i < end ; i++) {
                            id += parentStr.charAt(i);
                        }

                        if (!(id.equals("tblColumnCodigo"))){
                            return;
                        }

                        Text text = (Text) intersectedNode;
                        Long idLng;

                        try {
                            idLng = Long.valueOf(text.getText());
                        } catch (NumberFormatException e) {
                            return;
                        }

                        if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
                            if (selectedFuncionario == null) {
                                selectedFuncionario = new Funcionario();
                            }
                            
                            ObservableList<edu.uem.sgh.model.table.Funcionario> funcionarios = tableView.getItems();
                            edu.uem.sgh.model.table.Funcionario f = null;

                            for (edu.uem.sgh.model.table.Funcionario funcionario : funcionarios) {
                                if (funcionario.getCodigo().equals(idLng)) {
                                    f = funcionario;
                                    break;
                                }
                            }
                            
                            if (f == null) {
                                return;
                            }
                            
                            Date dateNasc, dateReg;
                            
                            try {
                                dateNasc = dataNascimentoFormat.parse(f.getDataNascimento());
                            } catch (ParseException e) {
                                dateNasc = null;
                            }
                            
                            try {
                                dateReg = dataRegistoFormat.parse(f.getDataRegisto());
                            } catch (ParseException e) {
                                dateReg = null;
                            }
                            
                            if (dateNasc == null || dateReg == null){
                                return;
                            }
                            
                            selectedFuncionario.setId(f.getCodigo());
                            selectedFuncionario.setMorada(f.getMorada());
                            selectedFuncionario.setEmail(f.getEmail());
                            selectedFuncionario.setNome(f.getNome());
                            selectedFuncionario.setNumBilheteIdentidade(f.getNumBilheteIdentidade());
                            selectedFuncionario.setNumTelefone(f.getNumTelefone());
                            selectedFuncionario.setDataNascimento(dateNasc.getTime());
                            selectedFuncionario.setDataRegisto(dateReg.getTime());
                            selectedFuncionario.setGerente(f.getGerente());
                            
                        } else {
                            abrirTelaFuncionario(idLng);
                        }
                    }
                }
            };
        }
        
        return mouseEventHandler;
    }

    public void setAutenticacaoRepository(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }
    
    private void abrirTelaFuncionario(Long idLng) {
        if (idLng == null)
            return;
        
        DialogGerirSituacao dialogGerirSituacao = new DialogGerirSituacao();
        dialogGerirSituacao.setFuncionario(selectedFuncionario);
        dialogGerirSituacao.setAutenticacaoRepository(autenticacaoRepository);
        dialogGerirSituacao.setFuncionarioRepository(funcionarioRepository);
        dialogGerirSituacao.setUsuario(usuario);
        dialogGerirSituacao.adicionarListeners();
        dialogGerirSituacao.show();
    }
}