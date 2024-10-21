/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.gerente.dialog.DialogEditarQuarto;
import edu.uem.sgh.controller.gerente.dialog.DialogInserirQuarto;
import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.quarto.QuartoRepository;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaQuartos extends AbstractController implements EventHandler<ActionEvent>, Initializable {
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnCarregar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Quarto> tableView;
    
    private QuartoRepository quartoRepository;
    private boolean firstTimeVisible = true;
    private Usuario usuario;
    private Result<List<Quarto>> rslt;
    private EventHandler<Event> eventHandler;
    private Quarto selectedQuarto;
    private Alert alert;
    private ReadOnlyIntegerProperty selectionIndexProperty;

    @Override
    public void adicionarListeners() {
        if (firstTimeVisible) {
            carregarQuartos();
        }
        
        btnAdicionar.setOnAction(this);
        btnCarregar.setOnAction(this);
        btnEditar.setOnAction(this);
        tableView.setOnKeyReleased(getEventHandler());
        tableView.setOnMouseClicked(getEventHandler());
        tableView.setOnMouseMoved(getEventHandler());
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnAction(null);
        btnCarregar.setOnAction(null);
        btnEditar.setOnAction(null);
        tableView.setOnKeyReleased(null);
        tableView.setOnMouseClicked(null);
        tableView.setOnMouseMoved(null);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
    
        if (source.equals(btnAdicionar))
            adicionarNovoQuarto();
        else if (source.equals(btnCarregar))
            carregarQuartos();
        else if (source.equals(btnEditar))
            editarQuarto();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectionIndexProperty = tableView.getSelectionModel().selectedIndexProperty();
        setUiClassID(getClass().getTypeName());
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setQuartoRepository(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }
    
    private void adicionarNovoQuarto() {
        if (quartoRepository == null || usuario == null) 
            return;
        
        DialogInserirQuarto dialogInserirQuarto = new DialogInserirQuarto();
        dialogInserirQuarto.setQuartoRepository(quartoRepository);
        dialogInserirQuarto.setUsuario(usuario);
        dialogInserirQuarto.adicionarListeners();
        dialogInserirQuarto.show();
    }

    private void carregarQuartos() {
        if (quartoRepository == null) {
            return;
        }
        
        if (firstTimeVisible)
            firstTimeVisible = false;
        
        rslt = quartoRepository.getAll();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Quarto>> error = (Result.Error<List<Quarto>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsg(descricao);
        } else {
            Result.Success<List<Quarto>> success = (Result.Success<List<Quarto>>) rslt;
            List<Quarto> quartos = success.getData();
            
            initTabela();
            limparTabela();
            
            if (quartos.isEmpty())
                return;
                
            for (Quarto quarto : quartos) {
                if (tableView == null)
                    continue;
                
                edu.uem.sgh.model.table.Quarto q = new edu.uem.sgh.model.table.Quarto(quarto.getId(), quarto.getPreco(), quarto.getCapacidade(), quarto.getDescricao(), quarto.getSituacao(), quarto.getFoto());
                tableView.getItems().add(q);
            }
        }
    }   
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }

    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Quarto, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Quarto, ?> tableColumn = columns.get(i);
            
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
                case "tblColumnCapacidade":
                    propertyValueFactory = new PropertyValueFactory("capacidade");
                        break;
                case "tblColumnTemFoto":
                    propertyValueFactory = new PropertyValueFactory("temFoto");
                        break;  
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
                case "tblColumnPreco":
                    propertyValueFactory = new PropertyValueFactory("preco");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    public EventHandler<Event> getEventHandler() {
        if (eventHandler == null) {
            eventHandler = (Event event) -> {
                if (event instanceof MouseEvent)
                    observarMouseEvent((MouseEvent) event);
                else if (event instanceof KeyEvent)
                    observarKeyEvent((KeyEvent) event);
            };
        }
        
        return eventHandler;
    }
    
    private void observarMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED || (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY && tableView.getSelectionModel().getSelectedIndex() != -1)){
            Node intersectedNode = mouseEvent.getPickResult().getIntersectedNode(), parent = intersectedNode.getParent();

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

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
                if (selectedQuarto == null) {
                    selectedQuarto = new Quarto();
                }

                ObservableList<edu.uem.sgh.model.table.Quarto> funcionarios = tableView.getItems();
                edu.uem.sgh.model.table.Quarto q = null;

                for (edu.uem.sgh.model.table.Quarto quarto : funcionarios) {
                    if (quarto.getCodigo().equals(idLng)) {
                        q = quarto;
                        break;
                    }
                }

                if (q == null) {
                    return;
                }

                selectedQuarto.setId(q.getCodigo());
                selectedQuarto.setCapacidade(q.getCapacidade());
                selectedQuarto.setDescricao(q.getDescricao());
                selectedQuarto.setFoto(q.getFoto());
                selectedQuarto.setPreco(q.getPreco());
                selectedQuarto.setSituacao(q.getSituacao());

            } else {
                abrirTelaQuarto(idLng);
            }
        }
    }

    private void observarKeyEvent(KeyEvent keyEvent) {
        if (keyEvent == null || !(keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED))) {
            return;
        }
        
        Object source = keyEvent.getSource();

        if (source == null || keyEvent.getCode() != KeyCode.ESCAPE || !temLinhaSelecionada(selectionIndexProperty.get()))
            return;

        if (tableView != null)
            tableView.getSelectionModel().clearSelection();
    }
    
    private boolean temLinhaSelecionada(Integer selectedIndex) {
        return selectedIndex != -1;
    }

    private void editarQuarto() {
        Integer selectedIndex = selectionIndexProperty.get();
        
        if (!temLinhaSelecionada(selectedIndex) || usuario == null || quartoRepository == null || selectedQuarto == null)  {
            return;
        }
        
        DialogEditarQuarto dialogEditarQuarto = new DialogEditarQuarto();
        dialogEditarQuarto.setQuartoRepository(quartoRepository);
        dialogEditarQuarto.setUsuario(usuario);
        dialogEditarQuarto.setQuarto(selectedQuarto);
        dialogEditarQuarto.adicionarListeners();
        dialogEditarQuarto.show();
    }

    private void abrirTelaQuarto(Long idLng) {
        if (idLng == null)
            return;
        
        
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