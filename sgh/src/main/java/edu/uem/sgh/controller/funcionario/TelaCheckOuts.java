/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.funcionario;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.CheckOut;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.repository.check_out_reserva.CheckOutReservaRepository;
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
public class TelaCheckOuts extends AbstractController implements Initializable{
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.CheckOut> tableView;
    
    private CheckOutReservaRepository checkOutReservaRepository;
    private Result<List<CheckOut.Reserva>> rslt;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, hh:ss");
    private EventHandler<ActionEvent> buttonEventHandler;
    private boolean firstTimeVisible = true;
    private Alert alert;
            
    @Override
    public void adicionarListeners() {
        if (firstTimeVisible) {
            carregarCheckOuts();
        }
        
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

    public void setCheckOutReservaRepository(CheckOutReservaRepository checkOutReservaRepository) {
        this.checkOutReservaRepository = checkOutReservaRepository;
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
        
        if (checkOutReservaRepository == null) {
            return;
        }
        
        rslt = checkOutReservaRepository.obterTodos();
        
        if (rslt == null) {
            return;
        }
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<CheckOut.Reserva>> error = (Result.Error<List<CheckOut.Reserva>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsg(descricao);
        } else {
            Result.Success<List<CheckOut.Reserva>> success = (Result.Success<List<CheckOut.Reserva>>) rslt;
            List<CheckOut.Reserva> checkOuts = success.getData();
            
            initTabela();
            limparTabela();
            
            if (tableView == null || checkOuts.isEmpty()) {
                return;
            }
                
            for (CheckOut.Reserva checkOutReserva : checkOuts) {
                edu.uem.sgh.model.table.CheckOut checkOut = new edu.uem.sgh.model.table.CheckOut();
                checkOut.setCodigo(checkOutReserva.getId());
                checkOut.setCodigoCheckIn(checkOutReserva.getCheckIn().getId());
                checkOut.setCodigoReserva(checkOutReserva.getCheckIn().getReserva().getId());
                checkOut.setCliente(checkOutReserva.getCheckIn().getReserva().getCliente());
                checkOut.setDataReserva(dateFormat.format(new Date(checkOutReserva.getCheckIn().getReserva().getDataReserva())));
                checkOut.setDataCheckIn(dateFormat.format(new Date(checkOutReserva.getCheckIn().getDataCheckIn())));
                checkOut.setDataEsperadaCheckIn(dateFormat.format(new Date(checkOutReserva.getCheckIn().getReserva().getDataCheckIn())));
                checkOut.setDataEsperadaCheckOut(dateFormat.format(new Date(checkOutReserva.getCheckIn().getReserva().getDataCheckOut())));
                checkOut.setDataCheckOut(dateFormat.format(new Date(checkOutReserva.getDataCheckOut())));
                checkOut.setValorTotal(checkOutReserva.getCheckIn().getReserva().getPagamento().getValorTotal());
                checkOut.setValorPagoReserva(checkOutReserva.getCheckIn().getReserva().getPagamento().getValorPago());
                checkOut.setValorPagoCheckOut(checkOutReserva.getValorPago());
                checkOut.setResponsavel(checkOutReserva.getResponsavel());
                tableView.getItems().add(checkOut);
            }
        }
    }
    
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.CheckOut, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.CheckOut, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnCodigoCheckIn":
                    propertyValueFactory = new PropertyValueFactory("codigoCheckIn");
                        break;
                case "tblColumnCodigoReserva":
                    propertyValueFactory = new PropertyValueFactory("codigoReserva");
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
                case "tblColumnDataEsperadaCheckIn":
                    propertyValueFactory = new PropertyValueFactory("dataEsperadaCheckIn");
                        break;
                case "tblColumnDataEsperadaCheckOut":
                    propertyValueFactory = new PropertyValueFactory("dataEsperadaCheckOut");
                        break;
                case "tblColumnDataCheckOut":
                    propertyValueFactory = new PropertyValueFactory("dataCheckOut");
                        break;
                case "tblColumnValorTotal":
                    propertyValueFactory = new PropertyValueFactory("valorTotal");
                        break;
                case "tblColumnValorPagoCheckOut":
                    propertyValueFactory = new PropertyValueFactory("valorPagoCheckOut");
                        break;
                case "tblColumnValorPagoReserva":
                    propertyValueFactory = new PropertyValueFactory("valorPagoReserva");
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