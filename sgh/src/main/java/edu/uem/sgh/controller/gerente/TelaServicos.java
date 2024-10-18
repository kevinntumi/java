/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

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
    private TableView<edu.uem.sgh.model.table.Servico> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Usuario usuario;
    private Result<List<Servico>> rslt;
    private ServicoRepository servicoRepository;
    private List<Object> dialogs;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
    private boolean firstTimeVisible = true;
    private Alert errorAlert;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            init();
        
        btnAdicionar.setOnMouseClicked(this);
        btnCarregar.setOnMouseClicked(this);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnMouseClicked(null);
        btnCarregar.setOnMouseClicked(null);
        
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
        
        if (eventType == MouseEvent.MOUSE_CLICKED) {
            if (!(source.equals(btnAdicionar) || source.equals(btnCarregar))) {
                if (dialogs != null) {
                    DialogInserirServico dialogInserirServico = null;
                    
                    for (Object object : dialogs) {
                        if (!(object instanceof DialogInserirServico))
                            return; 
                        
                        dialogInserirServico = (DialogInserirServico) object;
                        
                        //if (source.equals(dialogInserirServico.getCloseButton())){
                          //  resolverDependencias(dialogInserirServico.getClass().getTypeName(),false);
                            //dialogInserirServico.showingProperty().removeListener(this);
                            //dialogInserirServico.close();
                        //}
                        
                        break;
                    }
                    
                    if (dialogInserirServico != null) {
                        dialogs.remove(dialogInserirServico);
                    }
                }
                
                return;
            }

            if (source.equals(btnAdicionar))
                adicionarNovoServico();
            else
                carregarServicos();
        } else if (eventType == ActionEvent.ANY) {
            if (dialogs == null)
                return;
            
            DialogInserirServico dialogInserirServico = null;
            
            for (Object object : dialogs) {
                if (!(object instanceof DialogInserirServico))
                    return;
                
                dialogInserirServico = (DialogInserirServico) object;
                System.out.println("ss");
                if (!source.equals(dialogInserirServico.getDialogPane().getContent())) 
                    continue;
                
                resolverDependencias(dialogInserirServico.getClass().getTypeName(),false);
                dialogInserirServico.showingProperty().removeListener(this);
                dialogInserirServico.close();
                break;
            }
            
            if (dialogInserirServico != null) {
                dialogs.remove(dialogInserirServico);
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        DialogInserirServico dialogInserirServico = encontrarDialog();
            
        if (dialogInserirServico != null && observable.equals(dialogInserirServico.showingProperty())) {
            boolean isShowing = (boolean) newValue;
            System.out.println("x");
            resolverDependencias(dialogInserirServico.getClass().getTypeName(), isShowing);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Object> getDialogs() {
        if (dialogs == null) dialogs = new ArrayList<>();
        return dialogs;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    private DialogInserirServico encontrarDialog() {
        if (dialogs == null)
            return null;
        
        for (Object object : dialogs) {
            if (object instanceof DialogInserirServico)
                return (DialogInserirServico) object;
        }
        
        return null;
    }
    
    private void adicionarNovoServico() {
        DialogInserirServico dialogInserirServico = encontrarDialog();
        
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
            DialogInserirServico dialogInserirServico = encontrarDialog();
            
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
    
    private EventHandler<Event> getEventHandler() {
        return this;
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
}