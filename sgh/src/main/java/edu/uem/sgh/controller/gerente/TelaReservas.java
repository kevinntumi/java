/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.Reserva;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.repository.reserva.ReservaRepository;
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
public class TelaReservas extends AbstractController implements Initializable{
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Reserva> tableView;
    
    private ReservaRepository reservaRepository;
    private Result<List<Reserva>> rslt;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, hh:mm");
    private EventHandler<ActionEvent> buttonEventHandler;
    private boolean firstTimeVisible = true;
    private Alert alert;
            
    @Override
    public void adicionarListeners() {
        if (firstTimeVisible)
            carregarCheckOuts();
        
        btnCarregar.setOnAction(getButtonEventHandler());
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
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    private void limparTabela() {
        if (tableView == null || tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    private EventHandler<ActionEvent> getButtonEventHandler() {
        if (buttonEventHandler == null) {
            buttonEventHandler = (ActionEvent event) -> {
                Object source = event.getSource();
                
                if (source.equals(btnCarregar)) {
                    carregarCheckOuts();
                }
            };
        }
        
        return buttonEventHandler;
    }

    private void carregarCheckOuts() {
        if (firstTimeVisible) {
            firstTimeVisible = false;
        }
        
        if (reservaRepository == null) {
            return;
        }
        
        rslt = reservaRepository.obterTodasReservas();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Reserva>> error = (Result.Error<List<Reserva>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsg(descricao);
        } else {
            Result.Success<List<Reserva>> success = (Result.Success<List<Reserva>>) rslt;
            List<Reserva> reservas = success.getData();
            
            initTabela();
            limparTabela();
            
            if (tableView == null || reservas.isEmpty())
                return;
                
            for (Reserva reserva : reservas) {
                edu.uem.sgh.model.table.Reserva rsrv = new edu.uem.sgh.model.table.Reserva(reserva.getId(), reserva.getCliente(), dateFormat.format(new Date(reserva.getDataReserva())), dateFormat.format(new Date(reserva.getDataCheckIn())), dateFormat.format(new Date(reserva.getDataCheckOut())), reserva.getPagamento().getValorPago(), reserva.getPagamento().getValorTotal(), reserva.getFuncionario(), reserva.getSituacao(), dateFormat.format(new Date(reserva.getDataSituacao())));
                tableView.getItems().add(rsrv);
            }
        }
    }
    
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Reserva, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Reserva, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnCliente":
                    propertyValueFactory = new PropertyValueFactory("cliente");
                        break;
                case "tblColumnDataReserva":
                    propertyValueFactory = new PropertyValueFactory("dataReserva");
                        break;
                case "tblColumnDataCheckIn":
                    propertyValueFactory = new PropertyValueFactory("dataCheckIn");
                        break; 
                case "tblColumnDataSituacao":
                    propertyValueFactory = new PropertyValueFactory("dataSituacao");
                        break;
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
                case "tblColumnDataCheckOut":
                    propertyValueFactory = new PropertyValueFactory("dataCheckOut");
                        break;
                case "tblColumnValorTotal":
                    propertyValueFactory = new PropertyValueFactory("valorTotal");
                        break;
                case "tblColumnValorPago":
                    propertyValueFactory = new PropertyValueFactory("valorPago");
                        break;
                case "tblColumnResponsavel":
                    propertyValueFactory = new PropertyValueFactory("responsavel");
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