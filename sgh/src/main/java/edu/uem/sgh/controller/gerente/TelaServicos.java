/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.gerente.dialog.DialogEditarServico;
import edu.uem.sgh.controller.gerente.dialog.DialogTelaServico;
import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.repository.servico_quarto.ServicoQuartoRepository;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    private ServicoQuartoRepository servicoQuartoRepository;
    private List<Dialog<?>> dialogs;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
    private boolean firstTimeVisible = true;
    private Alert errorAlert;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            init();
        
        btnAdicionar.setOnMouseClicked(this);
        btnCarregar.setOnMouseClicked(this);
        btnEditar.setOnMouseClicked(this);
        tableView.setOnMouseClicked(this);
        tableView.setOnMouseMoved(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnMouseClicked(null);
        btnCarregar.setOnMouseClicked(null);
        btnEditar.setOnMouseClicked(null);
        tableView.setOnMouseClicked(null);
        tableView.setOnMouseMoved(null);
        
        if (errorAlert != null) 
            errorAlert.close();
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(Event event) {
        Object source = event.getSource();
        
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (!(source.equals(btnAdicionar) || source.equals(btnCarregar) || source.equals(btnEditar) || source.equals(tableView))) {
                return;
            }

            if (source.equals(btnAdicionar))
                adicionarNovoServico();
            else if (source.equals(btnCarregar))
                carregarServicos();
            else if (source.equals(tableView))
                cliqueNaTabela((MouseEvent) event);
            else
                editarServico();
        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if (source.equals(tableView))
                cliqueNaTabela((MouseEvent) event);
        }
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (observable instanceof ReadOnlyBooleanProperty) {
            if (dialogs != null) {
                List<Dialog<?>> dialogsToRemove = null;
                
                for (Dialog<?> d : dialogs) {
                    if (!d.isShowing()) {
                        if (dialogsToRemove == null){
                            dialogsToRemove = new ArrayList<>();
                        }
                        
                        dialogsToRemove.add(d);
                    }
                    
                    resolverDependencias(d.getClass().getTypeName(), d.isShowing());
                }
                
                if (dialogsToRemove == null) {
                    return;
                }
                
                for (Dialog<?> d : dialogsToRemove) {
                    dialogs.remove(d);
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

    public void setServicoQuartoRepository(ServicoQuartoRepository servicoQuartoRepository) {
        this.servicoQuartoRepository = servicoQuartoRepository;
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
        } else if (uiClassId.contains(DialogTelaServico.class.getTypeName())) {
            DialogTelaServico dialogTelaServico = (DialogTelaServico) encontrarDialog(DialogTelaServico.class);
            
            if (dialogTelaServico == null) {
                return;
            }
            
            if (add) {
                dialogTelaServico.setServicoQuartoRepository(servicoQuartoRepository);
                dialogTelaServico.setServicoRepository(servicoRepository);
                dialogTelaServico.adicionarListeners();
            } else {
                dialogTelaServico.removerListeners();
                dialogTelaServico.setServicoQuartoRepository(null);
                dialogTelaServico.setServicoRepository(null);
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
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Servico, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Servico, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
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

    private void cliqueNaTabela(MouseEvent event) {
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
                if (selectedServico == null) {
                    selectedServico = new edu.uem.sgh.schema.Servico();
                }
                
                ObservableList<edu.uem.sgh.model.table.Servico> servicos = tableView.getItems();
                edu.uem.sgh.model.table.Servico s = null;
                
                for (edu.uem.sgh.model.table.Servico servico : servicos) {
                    if (!(servico.getCodigo() == idLng)) {
                        continue;
                    }
                    
                    s = servico;
                    break;
                }
                
                selectedServico.setId(s != null ? s.getCodigo(): null);
                selectedServico.setDescricao(s != null ? s.getDescricao() : null);
                selectedServico.setSituacao(s != null ? s.getSituacao(): null);
                
            } else {
                abrirTelaServico(idLng);
            }
        }
    }
    
    private void abrirTelaServico(Long id) {
        DialogTelaServico dialogTelaServico = (DialogTelaServico) encontrarDialog(DialogTelaServico.class);
        
        if (dialogTelaServico == null) {
            dialogTelaServico = new DialogTelaServico();
            getDialogs().add(dialogTelaServico);
        } else {
            dialogTelaServico.showingProperty().removeListener(this);
        }
     
        dialogTelaServico.setIdServico(id);
        dialogTelaServico.showingProperty().addListener(this);
        dialogTelaServico.show();
    }
}