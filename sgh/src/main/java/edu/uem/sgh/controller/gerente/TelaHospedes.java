/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.repository.hospede.HospedeRepository;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaHospedes extends AbstractController implements EventHandler<ActionEvent>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Hospede> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Result<List<Hospede>> rslt;
    private HospedeRepository hospedeRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Alert alert;
    private boolean firstTimeVisible = true;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            carregarHospedes();
        
        btnCarregar.setOnAction(this);
    }

    @Override
    public void removerListeners() {
        btnCarregar.setOnAction(null);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        if (source.equals(btnCarregar)) {
            carregarHospedes();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    public void setHospedeRepository(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }
    
    private void limparTabela() {
        if (tableView == null || tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
    
    private void carregarHospedes() {
        if (hospedeRepository == null) 
            return;
        
        if (firstTimeVisible)
            firstTimeVisible = false;
        
        rslt = hospedeRepository.obterTodos();
        
        if (rslt == null) {
            return;
        }
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Hospede>> error = (Result.Error<List<Hospede>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsg(descricao);
        } else {
            Result.Success<List<Hospede>> success = (Result.Success<List<Hospede>>) rslt;
            List<Hospede> hospedes = success.getData();
            
            initTabela();
            limparTabela();
            
            if (hospedes.isEmpty() || tableView == null)
                return;
                
            for (Hospede hospede : hospedes) {
                edu.uem.sgh.model.table.Hospede h = new edu.uem.sgh.model.table.Hospede(hospede.getId(), hospede.getNome(), dateFormat.format(new Date(hospede.getDataNascimento())), dateFormat.format(new Date(hospede.getDataRegisto())), hospede.getNumDocumentoIdentidade(), hospede.getMorada());
                tableView.getItems().add(h);
            }
        }
    }

    private void initTabela() {
         if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Hospede, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Hospede, ?> tableColumn = columns.get(i);
            
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
                case "tblColumnNumBilheteIdentidade":
                    propertyValueFactory = new PropertyValueFactory("numBilheteIdentidade");
                        break;
                case "tblColumnMorada":
                    propertyValueFactory = new PropertyValueFactory("morada");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }
    
    private void mostrarMsg(String descricao) {
        if (descricao == null) {
            return;
        }
        
        getAlert().setContentText(descricao);
        getAlert().show();
    }
    
    private Alert getAlert() {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
        }
        
        return alert;
    }
}