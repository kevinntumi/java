/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.gerente.dialog.DialogEditarServico;
import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaServicos extends AbstractController implements EventHandler<Event>, ChangeListener<Object>, Initializable{
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Servico> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Usuario usuario;
    private Result<List<Servico>> rslt;
    private edu.uem.sgh.schema.Servico selectedServico = null;
    private ServicoRepository servicoRepository;
    private List<Dialog<?>> dialogs;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
    private boolean firstTimeVisible = true;
    private EventHandler<MouseEvent> rowEventHandler;
    private Map<TableRow<edu.uem.sgh.model.table.Servico>, Integer> rowIndexes;
    private ObservableList<String> tableColumnStyleClass, tableRowStyleClass;
    private Alert errorAlert;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            init();
        
        btnAdicionar.setOnMouseClicked(this);
        btnCarregar.setOnMouseClicked(this);
        btnEditar.setOnMouseClicked(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnMouseClicked(null);
        btnCarregar.setOnMouseClicked(null);
        btnEditar.setOnMouseClicked(null);
        
        if (errorAlert != null) 
            errorAlert.close();
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(Event event) {
        Object source = event.getSource(), eventType = event.getEventType();
        
        if (!(source.equals(btnAdicionar) || source.equals(btnCarregar) || source.equals(btnEditar))) {
            return;
        }
          
        if (source.equals(btnAdicionar))
            adicionarNovoServico();
        else if (source.equals(btnCarregar))
            carregarServicos();
        else
            editarServico();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable instanceof ReadOnlyBooleanProperty) {
            if (dialogs != null) {
                for (Dialog<?> d : dialogs) {
                    resolverDependencias(d.getClass().getTypeName(), d.isShowing());
                }
            }            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Dialog<?>> getDialogs() {
        if (dialogs == null) dialogs = new ArrayList<>();
        return dialogs;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    private Dialog<?> encontrarDialog(Class<?> clazz) {
        if (dialogs == null)
            return null;
        
        for (Dialog<?> dialog : dialogs) {
            if (dialog.getClass().equals(clazz))
                return dialog;
        }
        
        return null;
    }
    
    private void adicionarNovoServico() {
        DialogInserirServico dialogInserirServico = (DialogInserirServico) encontrarDialog(DialogInserirServico.class);
        
        if (dialogInserirServico == null) {
            dialogInserirServico = new DialogInserirServico();
            getDialogs().add(dialogInserirServico);
        } else {
            dialogInserirServico.showingProperty().removeListener(this);
        }
     
        dialogInserirServico.showingProperty().addListener(this);
        dialogInserirServico.show();
    }
    
    private Dialog<?> encontrarDialogVisivel() {
        if (dialogs == null || dialogs.isEmpty())
            return null;
        
        for (Dialog<?> dialog : dialogs) {
            if (dialog.isShowing())
                return dialog;
        }
        
        return null;
    }
    
    private void resolverDependencias(String uiClassId, boolean add) {
        if (uiClassId == null || uiClassId.isBlank())
            return;
        
        if (uiClassId.equals(DialogInserirServico.class.getTypeName())) {
            DialogInserirServico dialogInserirServico = (DialogInserirServico) encontrarDialog(DialogInserirServico.class);
            
            if (dialogInserirServico == null) {
                return;
            }
            
            if (add) {
                dialogInserirServico.setServicoRepository(servicoRepository);
                dialogInserirServico.setUsuario(usuario);
                dialogInserirServico.adicionarListeners();
            } else {
                dialogInserirServico.removerListeners();
                dialogInserirServico.setUsuario(null);
                dialogInserirServico.setServicoRepository(null);
            }
        } else if (uiClassId.equals(DialogEditarServico.class.getTypeName())) {
            DialogEditarServico dialogEditarServico = (DialogEditarServico) encontrarDialog(DialogEditarServico.class);
            
            if (dialogEditarServico == null) {
                return;
            }
            
            if (add) {
                dialogEditarServico.setServicoRepository(servicoRepository);
                dialogEditarServico.setUsuario(usuario);
                dialogEditarServico.setServico(selectedServico);
                dialogEditarServico.adicionarListeners();
            } else {
                dialogEditarServico.removerListeners();
                dialogEditarServico.setUsuario(null);
                dialogEditarServico.setServico(null);
                dialogEditarServico.setServicoRepository(null);
            }
        }
    }
    
    private void carregarServicos() {
        if (servicoRepository == null)
            return;
        
        rslt = servicoRepository.getAll();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Servico>> error = (Result.Error<List<Servico>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsgErro(descricao);
        } else {
            Result.Success<List<Servico>> success = (Result.Success<List<Servico>>) rslt;
            List<Servico> servicos = success.getData();
            
            initTabela();
            limparTabela();
            
            if (servicos.isEmpty())
                return;
            
            for (Servico servico : servicos) {
                edu.uem.sgh.model.table.Servico s = new edu.uem.sgh.model.table.Servico(servico.getId(), servico.getDescricao(), simpleDateFormat.format(new Date(servico.getDataRegisto())), ServicoSituacao.obterPorValor(servico.getSituacao()), servico.getGerente().getNome());
                tableView.getItems().add(s);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
    
    private Alert getErrorAlert() {
        if (errorAlert == null) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setGraphic(null);
            errorAlert.setHeaderText(null);
        }
        
        return errorAlert;
    }
    
    private void mostrarMsgErro(String descricao) {
        getErrorAlert().setContentText(descricao);
        getErrorAlert().show();
    }

    private void init() {
        if (firstTimeVisible) {
            firstTimeVisible = false;
        }
        
        carregarServicos();
    }

    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        if (tableView.getRowFactory() == null) {
            tableView.setRowFactory(new Callback<TableView<edu.uem.sgh.model.table.Servico>, TableRow<edu.uem.sgh.model.table.Servico>>() {  
                private TableRow<edu.uem.sgh.model.table.Servico> currentRow = null;
                
                private ChangeListener<Number> changeListener = new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if (currentRow == null)
                            return;
                        
                        getRowIndexes().put(currentRow, newValue.intValue());
                    }
                };
                        
                @Override
                public TableRow<edu.uem.sgh.model.table.Servico> call(TableView<edu.uem.sgh.model.table.Servico> param) {
                    TableRow<edu.uem.sgh.model.table.Servico> row = new TableRow<>();
                
                    if (row.getOnMouseClicked() == null) {
                        row.setOnMouseClicked(getRowEventHandler());
                    }
                    
                    if (tableRowStyleClass == null) {
                        tableRowStyleClass = row.getStyleClass();
                    }

                    currentRow = row;
                    row.indexProperty().addListener(changeListener);
                    return row;
                }
            });
        }
        
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (!(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && tableView.getSelectionModel().getSelectedIndex() != -1)){
                    return;
                }
                
                Node intersectedNode = event.getPickResult().getIntersectedNode(), parent = intersectedNode.getParent();
                
                if (parent instanceof TableColumnHeader || (intersectedNode instanceof Text && parent instanceof Label)) {
                    return;
                }
                
                System.out.println("node: " + intersectedNode);
                System.out.println("parent: " + intersectedNode.getParent());
                
                
                
            }
        });
      
        tableView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {             
                Node intersectedNode = event.getPickResult().getIntersectedNode(); 
                Parent parent = intersectedNode.getParent();
                
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
                
                if (id.isBlank() || !(id.startsWith("tblColumn"))){
                    return;
                }
                
                System.out.println("fuck " + id);
                
                if (id.equals("tblColumnCodigo")) {
                    Text text = (Text) intersectedNode;
                }
            }
        });
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Servico, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Servico, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            if (tableColumnStyleClass == null) {
                tableColumnStyleClass = tableColumn.getStyleClass();
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnDescricao":
                    propertyValueFactory = new PropertyValueFactory("descricao");
                        break;
                case "tblColumnDataRegisto":
                    propertyValueFactory = new PropertyValueFactory("dataRegisto");
                        break;  
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
                case "tblColumnInseridoPor":
                    propertyValueFactory = new PropertyValueFactory("inseridoPor");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    private void editarServico() {
        DialogEditarServico dialogEditarServico = (DialogEditarServico) encontrarDialog(DialogEditarServico.class);
        
        if (dialogEditarServico == null) {
            dialogEditarServico = new DialogEditarServico();
            getDialogs().add(dialogEditarServico);
        } else {
            dialogEditarServico.showingProperty().removeListener(this);
        }
     
        dialogEditarServico.showingProperty().addListener(this);
        dialogEditarServico.show();
    }

    public EventHandler<MouseEvent> getRowEventHandler() {
        if (rowEventHandler == null) {
            rowEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Object source = event.getSource();
                    
                    if (!(source instanceof TableRow)) {
                        return;
                    }
                    
                    @SuppressWarnings("unchecked")
                    TableRow<edu.uem.sgh.schema.Servico> row = (TableRow<edu.uem.sgh.schema.Servico>) source;
                    int index = getRowIndexes().getOrDefault(row, -1);
                    
                    if (index == -1) {
                        return;
                    }
                    
                    edu.uem.sgh.model.table.Servico servico = tableView.getItems().get(index);
                    
                    
                    System.out.println(servico.getCodigo() + " " + getRowIndexes().size());
                }
            };
        }
        
        return rowEventHandler;
    }

    public Map<TableRow<edu.uem.sgh.model.table.Servico>, Integer> getRowIndexes() {
        if (rowIndexes == null) rowIndexes = new HashMap<>();
        return rowIndexes;
    }
}