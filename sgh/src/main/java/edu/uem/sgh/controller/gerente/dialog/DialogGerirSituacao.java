/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.uem.sgh.controller.gerente.dialog;

import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Passagem;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.autenticacao.AutenticacaoRepository;
import edu.uem.sgh.repository.funcionario.FuncionarioRepository;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
public class DialogGerirSituacao extends Dialog<Object>{
    private edu.uem.sgh.model.Funcionario funcionario;
    private Label lblCodigoUsuario,
            lblSituacaoAtual, lblCodigoFuncionario,
            lblNome, lblDataNasc,
            lblNumBI, lblContratadoPor,
            lblNumTelefone, lblEmail,
            lblDataRegisto,
            lblAlterar;
    
    private CheckBox checkBoxDemitido, checkBoxContratado;
    private TableView<edu.uem.sgh.model.table.Passagem> tableView;
    private ImageView close;
    private EventHandler<Event> eventHandler;
    private Usuario usuario;
    private FuncionarioRepository funcionarioRepository;
    private AutenticacaoRepository autenticacaoRepository;
    private Result<List<Passagem>> rsltPassagem;
    private SimpleDateFormat dataNascimentoFormat = new SimpleDateFormat("dd.MM.yyyy"), dataRegistoFormat = new SimpleDateFormat("dd.MM.yyyy, HH:SS");
    private Result<Usuario> rsltUsuario;
    private Result<Boolean> rsltAlterar;
    private EventHandler<DialogEvent> dialogEventHandler;
    private Alert alert;
    private ChangeListener<Object> changeListener;
    
    @SuppressWarnings("unchecked")
    public DialogGerirSituacao() {
        initStyle(StageStyle.UNDECORATED);
        
        FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("DialogGerirSituacao", "\\gerente\\dialog\\"));
        Parent content;
        
        try {
            content = fXMLLoader.load();
        } catch (IOException e) {
            return;
        }
        
        ObservableList<Node> children = content.getChildrenUnmodifiable();
        lblCodigoUsuario = (Label) findById("lblCodigoUsuario", children);
        lblSituacaoAtual = (Label) findById("lblSituacaoAtual", children);
        lblCodigoFuncionario = (Label) findById("lblCodigoFuncionario", children);
        lblNome = (Label) findById("lblNome", children);
        lblDataNasc = (Label) findById("lblDataNasc", children);
        lblNumBI = (Label) findById("lblNumBI", children);
        lblContratadoPor = (Label) findById("lblContratadoPor", children);
        lblNumTelefone = (Label) findById("lblNumTelefone", children);
        lblEmail = (Label) findById("lblEmail", children);
        lblDataRegisto = (Label) findById("lblDataRegisto", children);
        lblAlterar = (Label) findById("lblAlterar", children);
        checkBoxDemitido = (CheckBox) findById("checkBoxDemitido", children);
        checkBoxContratado = (CheckBox) findById("checkBoxContratado", children);
        close = (ImageView) findById("close", children);
        tableView = (TableView<edu.uem.sgh.model.table.Passagem>) findById("tableView", children);
        getDialogPane().setContent(content);
    }

    public void setFuncionarioRepository(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void setAutenticacaoRepository(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }
    
    private Node findById(String id, ObservableList<Node> children) {
        if ((id == null || id.isBlank()) || children.isEmpty())
            return null;
        
        for (Node node : children) {
            if (id.equals(node.getId()))
                return node;
            
            Node n = null;
            
            if (node instanceof Parent || node instanceof AnchorPane || n instanceof Pane || n instanceof Region || n instanceof VBox || n instanceof HBox || node instanceof GridPane) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());;
            }
            
            if (n != null)
                return n;
        }
        
        return null;
    }
    
    public void adicionarListeners() {
        setOnCloseRequest(getDialogEventHandler());
        close.setOnMouseClicked(getEventHandler());
        
        if (funcionario == null)
            return;
        
        checkBoxContratado.selectedProperty().addListener(getChangeListener());
        checkBoxDemitido.selectedProperty().addListener(getChangeListener());
        lblAlterar.setOnMouseClicked(getEventHandler());
        init();
    }
    
    public void removerListeners() {
        setOnCloseRequest(null);
        close.setOnMouseClicked(null);
        
        if (funcionario == null)
            return;
        
        checkBoxContratado.selectedProperty().removeListener(getChangeListener());
        checkBoxDemitido.selectedProperty().removeListener(getChangeListener());
        lblAlterar.setOnMouseClicked(null);
    }

    public EventHandler<DialogEvent> getDialogEventHandler() {
        if (dialogEventHandler == null) {
            dialogEventHandler = this::observarDialogEvent;
        }
        
        return dialogEventHandler;
    }
    
    public EventHandler<Event> getEventHandler() {
        if (eventHandler == null) {
            eventHandler = (Event event) -> {
                EventType<? extends Event> eventType = event.getEventType();
                
                if (eventType.equals(MouseEvent.MOUSE_CLICKED))
                    observarMouseEvent((MouseEvent) event);
            };
        }
        
        return eventHandler;
    }
    
    private void observarDialogEvent(DialogEvent dialogEvent) {
        if (dialogEvent.isConsumed() || !(dialogEvent.getEventType().equals(DialogEvent.DIALOG_CLOSE_REQUEST))) {
            return;
        }
        
        fecharDialog();
    }
    
    private void observarMouseEvent(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        
        if (!(source.equals(lblAlterar) || source.equals(close))) {
            return;
        }

        if (source.equals(lblAlterar)) {
            cliqueBtnAlterar();
        } else {
            fecharDialog();
        }
    }
    
    private DialogGerirSituacao getDialogGerirSituacao() {
        return this;
    }
    
    private void fecharDialog() {
        setResult(ButtonType.CLOSE);
        getEventHandler().handle(new DialogEvent(getDialogGerirSituacao(), DialogEvent.DIALOG_CLOSE_REQUEST));
    }

    private void cliqueBtnAlterar() {
        if (funcionario == null || usuario == null || funcionario.getId() == null || autenticacaoRepository == null || funcionarioRepository == null)
            return;
        
        if (!(checkBoxContratado.isSelected() || checkBoxDemitido.isSelected())) {
            mostrarMsg("Selecione pelo menos uma das opcoes!");
            return;
        }
        
        
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    private void init() {
        if (funcionario == null || usuario == null || funcionario.getId() == null || autenticacaoRepository == null || funcionarioRepository == null)
            return;
        
        lblNome.setText(funcionario.getNome());
        lblNumTelefone.setText(funcionario.getNumTelefone() + "");
        lblNumBI.setText(funcionario.getNumBilheteIdentidade());
        lblEmail.setText(funcionario.getEmail());
        lblCodigoFuncionario.setText(funcionario.getId() + "");
        lblContratadoPor.setText(usuario.getIdTipo() == funcionario.getGerente().getId() ? "Eu" : funcionario.getGerente().getNome());
        lblDataNasc.setText(dataNascimentoFormat.format(new Date(funcionario.getDataNascimento())));
        lblDataRegisto.setText(dataRegistoFormat.format(new Date(funcionario.getDataRegisto())));
        
        rsltUsuario = autenticacaoRepository.getUserById(funcionario.getId());
        
        if (rsltUsuario instanceof Result.Success) {
            Result.Success<Usuario> sucess = (Result.Success<Usuario>) rsltUsuario;
            Usuario usuario = sucess.getData();
            lblCodigoUsuario.setText(usuario != null ? (usuario.getId() + "") : "-");
        }
        
        rsltPassagem = funcionarioRepository.obterPassagensPorFuncionario(funcionario.getId());
        
        if (rsltPassagem instanceof Result.Success) {
            Result.Success<List<Passagem>> sucess = (Result.Success<List<Passagem>>) rsltPassagem;
            List<Passagem> passagens = sucess.getData();
            
            initTabela();
            limparTabela();
            
            if (passagens.isEmpty()) {
                lblSituacaoAtual.setText("-");
                return;
            }
            
            boolean estaContratado = false;
            
            for (Passagem passagem : passagens) {
                if (passagem.getDataFim() != null && estaContratado == false) {
                    estaContratado = true;
                }
                
                if (tableView == null){
                    continue;
                }
                
                edu.uem.sgh.model.table.Passagem p = new edu.uem.sgh.model.table.Passagem(passagem.getId(), passagem.getSituacao(), passagem.getIdTipo(), passagem.getTipo(), dataNascimentoFormat.format(new Date(passagem.getDataInicio())), dataNascimentoFormat.format(new Date(passagem.getDataFim())));
                tableView.getItems().add(p);
            }
            
            lblSituacaoAtual.setText(estaContratado ? "CONTRATADO" : "DEMITIDO");
        }
    }
    
    @SuppressWarnings("unchecked")
    private void initTabela() {
        if (tableView == null) {
            return;
        }
        
        ObservableList<TableColumn<edu.uem.sgh.model.table.Passagem, ?>> columns = tableView.getColumns();
        
        for (int i = 0 ; i < columns.size() ; i++) {
            TableColumn<edu.uem.sgh.model.table.Passagem, ?> tableColumn = columns.get(i);
            
            if (tableColumn.getCellValueFactory() != null || (tableColumn.getId() == null || tableColumn.getId().isBlank())) {
                continue;
            }
            
            PropertyValueFactory propertyValueFactory = null;
            
            switch (tableColumn.getId()) {
                case "tblColumnCodigoPassagem":
                    propertyValueFactory = new PropertyValueFactory("codigo");
                        break;
                case "tblColumnSituacao":
                    propertyValueFactory = new PropertyValueFactory("situacao");
                        break;
                case "tblColumnDataInicio":
                    propertyValueFactory = new PropertyValueFactory("dataInicio");
                        break;  
                case "tblColumnDataFim":
                    propertyValueFactory = new PropertyValueFactory("dataFim");
                        break;
            }
            
            if (propertyValueFactory != null)
                tableColumn.setCellValueFactory(propertyValueFactory);
        }
    }

    private void limparTabela() {
        if (tableView == null || tableView.getItems().isEmpty()) {
            return;
        }
        
        tableView.getItems().clear();
    }

    public ChangeListener<Object> getChangeListener() {
        if (changeListener == null) {
            changeListener = (ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
                if (observable.equals(checkBoxDemitido.selectedProperty()))
                    observarCheckBoxDemitido((Boolean) newValue);
                else if (observable.equals(checkBoxContratado.selectedProperty()))
                    observarCheckBoxContratado((Boolean) newValue);
            };
        }
            
        return changeListener;
    }
    
    private void observarCheckBoxDemitido(Boolean checked) {
        if (checked && checkBoxContratado.isSelected()) {
            checkBoxContratado.setSelected(false);
        }
    }

    private void observarCheckBoxContratado(Boolean checked) {
        if (checked && checkBoxDemitido.isSelected()) {
            checkBoxDemitido.setSelected(false);
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