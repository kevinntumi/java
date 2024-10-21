/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.model.CheckIn;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.table.CheckInReserva;
import edu.uem.sgh.repository.check_in_reserva.CheckInReservaRepository;
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
public class TelaCheckIns extends AbstractController implements EventHandler<ActionEvent>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.CheckInReserva> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Result<List<CheckIn.Reserva>> rslt;
    private CheckInReservaRepository checkInReservaRepository;
    private boolean firstTimeVisible = true;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, hh:ss");
    private Alert alert;
    
    @Override
    public void adicionarListeners() {
        if (firstTimeVisible) {
            init();
        }
        
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
        if (event == null || event.getSource() == null) {
            return;
        }
        
        Object source = event.getSource();
        
        if (source.equals(btnCarregar)) {
            carregarCheckIns();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }
    
    private void limparTabela() {
        if (tableView == null || tableView.getItems().isEmpty()) {
            return;
        }
        
        tableView.getItems().clear();
    }
   
    private void carregarCheckIns() {
        if (checkInReservaRepository == null) {
            return;
        }
         
        rslt = checkInReservaRepository.obterTodos();
        
        if (rslt == null) {
            return;
        }
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<CheckIn.Reserva>> error = (Result.Error<List<CheckIn.Reserva>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsg(descricao);
        } else {
            Result.Success<List<CheckIn.Reserva>> success = (Result.Success<List<CheckIn.Reserva>>) rslt;
            List<CheckIn.Reserva> checkInsReservas = success.getData();
            
            initTabela();
            limparTabela();
            
            if (tableView == null || checkInsReservas.isEmpty()) {
                return;
            }
             
            for (CheckIn.Reserva checkIn : checkInsReservas) {
                edu.uem.sgh.model.table.CheckInReserva checkInReserva = new CheckInReserva();
                checkInReserva.setCodigo(checkIn.getId());
                checkInReserva.setDataCheckIn(dateFormat.format(new Date(checkIn.getDataCheckIn())));
                checkInReserva.setCodigoReserva(checkIn.getReserva().getId());
                checkInReserva.setDataEsperadaCheckIn(dateFormat.format(new Date(checkIn.getReserva().getDataCheckIn())));
                checkInReserva.setDataEsperadaCheckOut(dateFormat.format(new Date(checkIn.getReserva().getDataCheckOut())));
                checkInReserva.setDataReserva(dateFormat.format(new Date(checkIn.getReserva().getDataReserva())));
                checkInReserva.setResponsavel(checkIn.getFuncionario().getNome());
                checkInReserva.setValorPago(checkIn.getReserva().getPagamento().getValorPago());
                checkInReserva.setValorTotal(checkIn.getReserva().getPagamento().getValorTotal());
                checkInReserva.setCliente(checkIn.getReserva().getCliente().getNome());
                tableView.getItems().add(checkInReserva);
            }
        }
    }
    
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.CheckInReserva, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.CheckInReserva, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigo":
                    propertyValueFactory = new PropertyValueFactory<>("codigo");
                        break;
                case "tblColumnCodigoReserva":
                    propertyValueFactory = new PropertyValueFactory<>("codigoReserva");
                        break;
                case "tblColumnCliente":
                    propertyValueFactory = new PropertyValueFactory<>("cliente");
                        break;
                case "tblColumnDataReserva":
                    propertyValueFactory = new PropertyValueFactory<>("dataReserva");
                        break;
                case "tblColumnDataCheckIn":
                    propertyValueFactory = new PropertyValueFactory<>("dataCheckIn");
                        break;  
                case "tblColumnDataEsperadaCheckOut":
                    propertyValueFactory = new PropertyValueFactory<>("dataEsperadaCheckOut");
                        break;
                case "tblColumnValorTotal":
                    propertyValueFactory = new PropertyValueFactory<>("valorTotal");
                        break;
                case "tblColumnValorPago":
                    propertyValueFactory = new PropertyValueFactory<>("valorPago");
                        break;
                case "tblColumnResponsavel":
                    propertyValueFactory = new PropertyValueFactory<>("responsavel");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    public void setCheckInReservaRepository(edu.uem.sgh.repository.check_in_reserva.CheckInReservaRepository checkInReservaRepository) {
        this.checkInReservaRepository = checkInReservaRepository;
    }

    private void init() {
        if (firstTimeVisible)
            firstTimeVisible = false;
        
        carregarCheckIns();
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