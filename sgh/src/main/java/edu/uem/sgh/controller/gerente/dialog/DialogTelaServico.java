/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.uem.sgh.controller.gerente.dialog;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.repository.servico_quarto.ServicoQuartoRepository;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
    private TableView<Quarto> tableView;
    private Long idServico = null;
    private ServicoQuartoRepository servicoQuartoRepository;
    private ServicoRepository servicoRepository;
    private Result<List<edu.uem.sgh.model.Quarto>> rsltTwo;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
    private Result<Servico> rsltOne;
    private EventHandler<ActionEvent> eventHandler;
    private boolean firstTimeVisible = true;
    private Button btnOk, btnCarregar;

    @SuppressWarnings("unchecked")
    public DialogTelaServico() {
        try {
            content = new FXMLLoader(Path.getFXMLURL(layoutName, additionalPath)).load();
        } catch (IOException e) {
            return;
        }
        
        initStyle(StageStyle.UNDECORATED);
        ObservableList<Node> children = content.getChildrenUnmodifiable();
        txtCodigo = (Label) findById("txtCodigo", children);
        txtDescricao = (Label) findById("txtDescricao", children);
        txtSituacao = (Label) findById("txtSituacao", children);
        txtDataRegisto = (Label) findById("txtDataRegisto", children);
        txtInseridoPor = (Label) findById("txtInseridoPor", children);
        btnCarregar = (Button) findById("btnCarregar", children);
        tableView = (TableView<Quarto>) findById("tableView", children);
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
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
        
        if (btnCarregar == null)
            return;
        
        if (eventHandler == null) {
            eventHandler = (ActionEvent event) -> {
                if (!event.getSource().equals(btnCarregar)) {
                    return;
                }
                
                carregar();
            };
        }
        
        btnCarregar.setOnAction(eventHandler);
    }
    
    public void removerListeners() {
        if (btnCarregar == null) 
            return;
            
        btnCarregar.setOnAction(null);
    }
    
    public void carregar() {
        if (idServico == null || idServico < 0 || servicoQuartoRepository == null || servicoRepository == null) {
            return;
        }
        
        btnOk.setMouseTransparent(true);
        String loadingPlaceholderText = "A carregar...";
        txtCodigo.setText(loadingPlaceholderText);
        txtDescricao.setText(loadingPlaceholderText);
        txtSituacao.setText(loadingPlaceholderText);
        txtDataRegisto.setText(loadingPlaceholderText);
        txtInseridoPor.setText(loadingPlaceholderText);
        
        rsltOne = servicoRepository.get(idServico);
        
        if (rsltOne == null) {
            btnOk.setMouseTransparent(false);
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
        
        rsltTwo = servicoQuartoRepository.obterQuartosPorServico(idServico);
        
        if (rsltTwo == null) {
            btnOk.setMouseTransparent(false);
            return;
        }
        
        if (rsltTwo instanceof Result.Success) {
            initTabela();
            limparTabela();
            
            Result.Success<List<edu.uem.sgh.model.Quarto>> success = (Result.Success<List<edu.uem.sgh.model.Quarto>>) rsltTwo;
            List<edu.uem.sgh.model.Quarto> quartos = success.getData();
            
            if (quartos.isEmpty()) {
                return;
            }
            
            for (edu.uem.sgh.model.Quarto quarto : quartos) {
                tableView.getItems().add(new Quarto(quarto.getId(), quarto.getDescricao()));
            }
        }
        
        btnOk.setMouseTransparent(false);
    }
    
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<Quarto, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<Quarto, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigoQuarto":
                    propertyValueFactory = new PropertyValueFactory("codigoQuarto");
                        break;
                case "tblColumnDescricaoQuarto":
                    propertyValueFactory = new PropertyValueFactory("descricaoQuarto");
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
    
    private class Quarto {
        private final SimpleLongProperty codigoQuarto;
        private final SimpleStringProperty descricaoQuarto;

        public Quarto(Long codigo, String descricaoQuarto) {
            this.codigoQuarto = new SimpleLongProperty(codigo);
            this.descricaoQuarto = new SimpleStringProperty(descricaoQuarto);
        }

        public long getCodigoQuarto() {
            return codigoQuarto.get();
        }

        public void setCodigo(long codigo) {
            this.codigoQuarto.set(codigo);
        }

        public String getDescricao() {
            return descricaoQuarto.get();
        }

        public void setDescricao(String descricao) {
            this.descricaoQuarto.set(descricao);
        }
    }
}