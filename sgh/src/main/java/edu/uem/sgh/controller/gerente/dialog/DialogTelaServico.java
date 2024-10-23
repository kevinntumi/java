/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.uem.sgh.controller.gerente.dialog;

import edu.uem.sgh.controller.DefaultDialogPane;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.repository.servico_quarto.ServicoQuartoRepository;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Kevin Ntumi
 */
public class DialogTelaServico extends Dialog<Object>{
    private Parent content;
    private final String layoutName = "DialogTelaServico", additionalPath = "\\gerente\\dialog\\";
    private Label txtCodigo, txtDescricao, txtSituacao, txtDataRegisto, txtInseridoPor;
    private TableView<edu.uem.sgh.model.table.ServicoQuarto> tableView;
    private Long idServico = null;
    private ServicoQuartoRepository servicoQuartoRepository;
    private ServicoRepository servicoRepository;
    private Result<List<edu.uem.sgh.model.ServicoQuarto>> rsltTwo;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy,, hh:mm");
    private Result<Servico> rsltOne;
    private EventHandler<ActionEvent> eventHandler;
    private EventHandler<DialogEvent> dialogEventHandler;
    private boolean firstTimeVisible = true;
    private Button btnFechar, btnCarregar;

    @SuppressWarnings("unchecked")
    public DialogTelaServico() {
        try {
            content = new FXMLLoader(Path.getFXMLURL(layoutName, additionalPath)).load();
        } catch (IOException e) {
            return;
        }
        
        initStyle(StageStyle.UNDECORATED);
        setDialogPane(new DefaultDialogPane());
        ObservableList<Node> children = content.getChildrenUnmodifiable();
        txtCodigo = (Label) findById("txtCodigo", children);
        txtDescricao = (Label) findById("txtDescricao", children);
        txtSituacao = (Label) findById("txtSituacao", children);
        txtDataRegisto = (Label) findById("txtDataRegisto", children);
        txtInseridoPor = (Label) findById("txtInseridoPor", children);
        btnCarregar = (Button) findById("btnCarregar", children);
        tableView = (TableView<edu.uem.sgh.model.table.ServicoQuarto>) findById("tableView", children);
        btnFechar = (Button) findById("btnFechar", children);
        getDialogPane().setContent(content);
    }

    public void setIdServico(Long idServico) {
        this.idServico = idServico;
    }

    public void setServicoQuartoRepository(ServicoQuartoRepository servicoQuartoRepository) {
        this.servicoQuartoRepository = servicoQuartoRepository;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    private Node findById(String id, ObservableList<Node> children) {
        if ((id == null || id.isBlank()) || children.isEmpty())
            return null;
        
        for (Node node : children) {
            if (id.equals(node.getId()))
                return node;
            
            Node n = null;
            
            if (node instanceof Parent || n instanceof Pane || n instanceof Region || n instanceof VBox || n instanceof HBox) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            }

            if (n != null)
                return n;
        }
        
        return null;
    }
    
    public void adicionarListeners() {
        if (firstTimeVisible) {
            carregar();
        }
        
        btnFechar.setOnAction(getEventHandler());
        setOnCloseRequest(getDialogEventHandler());
        
        if (btnCarregar == null) {
            return;
        }
        
        btnCarregar.setOnAction(getEventHandler());
    }
    
    public void removerListeners() {
        btnFechar.setOnAction(null);
        setOnCloseRequest(null);
        
        if (btnCarregar == null) {
            return;
        }
            
        btnCarregar.setOnAction(null);
    }
    
    public void carregar() {
        if (idServico == null || idServico < 0 || servicoQuartoRepository == null || servicoRepository == null) {
            return;
        }
        
        btnFechar.setMouseTransparent(true);
        String loadingPlaceholderText = "A carregar...";
        txtCodigo.setText(loadingPlaceholderText);
        txtDescricao.setText(loadingPlaceholderText);
        txtSituacao.setText(loadingPlaceholderText);
        txtDataRegisto.setText(loadingPlaceholderText);
        txtInseridoPor.setText(loadingPlaceholderText);
        
        rsltOne = servicoRepository.get(idServico);
        
        if (rsltOne == null) {
            btnFechar.setMouseTransparent(false);
            return;
        }
        
        if (rsltOne instanceof Result.Success) {
            Result.Success<Servico> success = (Result.Success<Servico>) rsltOne;
            Servico servico = success.getData();
            txtCodigo.setText(servico != null ? servico.getId() + "" : "Vazio");
            txtDescricao.setText(servico != null ? servico.getDescricao()+ "" : "Vazio");
            txtSituacao.setText(servico != null ? servico.getSituacao() + "" : "Vazio");
            txtDataRegisto.setText(servico != null ? simpleDateFormat.format(new Date(servico.getDataRegisto())) : "Vazio");
            txtInseridoPor.setText(servico != null ? servico.getGerente().getNome() + "" : "Vazio");     
        } else {
            loadingPlaceholderText = "Erro ao tentar carregar";
            txtCodigo.setText(loadingPlaceholderText);
            txtDescricao.setText(loadingPlaceholderText);
            txtSituacao.setText(loadingPlaceholderText);
            txtDataRegisto.setText(loadingPlaceholderText);
            txtInseridoPor.setText(loadingPlaceholderText);
        }
        
        rsltTwo = servicoQuartoRepository.obterAssociacoesPorServico(idServico);
        
        if (rsltTwo == null) {
            btnFechar.setMouseTransparent(false);
            return;
        }
        System.out.println(rsltTwo.getValue());
        if (rsltTwo instanceof Result.Success) {
            initTabela();
            limparTabela();
            
            Result.Success<List<edu.uem.sgh.model.ServicoQuarto>> success = (Result.Success<List<edu.uem.sgh.model.ServicoQuarto>>) rsltTwo;
            List<edu.uem.sgh.model.ServicoQuarto> servicoQuartos = success.getData();
            
            initTabela();
            limparTabela();
            
            if (servicoQuartos.isEmpty()) {
                return;
            }
            
            for (edu.uem.sgh.model.ServicoQuarto servicoQuarto : servicoQuartos) {
                tableView.getItems().add(new edu.uem.sgh.model.table.ServicoQuarto(servicoQuarto.getId(), servicoQuarto.getQuarto().getId(), servicoQuarto.getQuarto().getDescricao(), simpleDateFormat.format(new Date(servicoQuarto.getDataAssociacao())), servicoQuarto.getSituacao(), simpleDateFormat.format(new Date(servicoQuarto.getDataSituacao())), servicoQuarto));
            }
        }
        
        btnFechar.setMouseTransparent(false);
    }
    
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.ServicoQuarto, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.ServicoQuarto, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnCodigoQuarto":
                    propertyValueFactory = new PropertyValueFactory("codigoQuarto");
                        break;
                case "tblColumnDescricaoQuarto":
                    propertyValueFactory = new PropertyValueFactory("descricaoQuarto");
                        break;
                case "tblColumnDataAssociacao":
                    propertyValueFactory = new PropertyValueFactory("dataAssociacao");
                        break;
                case "tblColumnDataSituacao":
                    propertyValueFactory = new PropertyValueFactory("dataSituacao");
                        break;
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    private void limparTabela() {
        if (!tableView.getItems().isEmpty()) {
            tableView.getItems().clear();
        }
    }

    public EventHandler<ActionEvent> getEventHandler() {
        if (eventHandler == null) {
            eventHandler = (ActionEvent event) -> {
                if (event.getSource() == null || !(event.getSource().equals(btnCarregar) || event.getSource().equals(btnFechar))) {
                    return;
                }
                
                if (event.getSource().equals(btnCarregar)) 
                    carregar();
                else
                    fecharDialog();
            };
        }
        
        return eventHandler;
    }
    
    public EventHandler<DialogEvent> getDialogEventHandler() {
        if (dialogEventHandler == null) {
            dialogEventHandler = (DialogEvent event) -> {
                if (!(event.getEventType().equals(DialogEvent.DIALOG_CLOSE_REQUEST))) {
                    event.consume();
                }
            };
        }
        
        return dialogEventHandler;
    }

    private void fecharDialog() {
        setResult(ButtonType.CLOSE);
        getDialogEventHandler().handle(new DialogEvent(this, DialogEvent.DIALOG_CLOSE_REQUEST));
    }
}