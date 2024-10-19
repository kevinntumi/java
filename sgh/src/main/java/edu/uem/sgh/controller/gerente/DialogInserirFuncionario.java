/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.funcionario.FuncionarioRepository;
import edu.uem.sgh.schema.Funcionario;
import edu.uem.sgh.util.FuncionarioValidator;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogInserirFuncionario extends Dialog<Object> {
    private Parent content;
    private final String layoutName = "DialogInserirFuncionario", additionalPath = "\\gerente\\dialog\\";
    private String nome = null, email = null, morada = null, numTelefoneStr = null, numBilheteIdentidade = null;
    private LocalDate dataNascimento = null;
    private Integer numTelefone;
    private TextField txtNome, txtEmail, txtNumTelefone, txtNumBI, txtMorada;
    private DatePicker dataNascimentoInput;
    private ImageView close;
    private ChangeListener<Object> changeListener;
    private EventHandler<MouseEvent> mouseClickedEventHandler;
    private EventHandler<DialogEvent> dialogLifecycleEventHandler;
    private FuncionarioRepository funcionarioRepository;
    private Button btnOk, btnFechar;
    private Result<Boolean> rslt = null;
    private Alert alert = null;
    private Usuario usuario;
    
    public DialogInserirFuncionario() {
        initStyle(StageStyle.UNDECORATED);
        
        try {
            content = new FXMLLoader(Path.getFXMLURL(layoutName, additionalPath)).load();
        } catch (IOException e) {
            return;
        }
        
        ObservableList<Node> children = content.getChildrenUnmodifiable();
        txtNome = (TextField) findById("txtNome", children);
        txtEmail = (TextField) findById("txtEmail", children);
        txtNumTelefone = (TextField) findById("txtNumTelefone", children);
        txtNumBI = (TextField) findById("txtNumBI", children);
        txtMorada = (TextField) findById("txtMorada", children);
        dataNascimentoInput = (DatePicker) findById("dataNascimentoInput", children);
        btnOk = (Button) findById("btnOk", children);
        btnFechar = (Button) findById("btnFechar", children);
        close = (ImageView) findById("close", children);
        getDialogPane().setContent(content);
    }
    
    private Node findById(String id, ObservableList<Node> children) {
        if ((id == null || id.isBlank()) || children.isEmpty())
            return null;
        
        for (Node node : children) {
            if (id.equals(node.getId()))
                return node;
            
            Node n = null;
            
            if (node instanceof Parent) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            } else if (n instanceof Pane) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            } else if (n instanceof Region) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            } else if (n instanceof VBox) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            } else if (n instanceof HBox) {
                n = findById(id, ((Parent) node).getChildrenUnmodifiable());
            }

            if (n != null)
                return n;
        }
        
        return null;
    }
    
    public void adicionarListeners() {
        if (content == null)
            return;
        
        txtNome.textProperty().addListener(getChangeListener());
        txtEmail.textProperty().addListener(changeListener);
        txtNumTelefone.textProperty().addListener(changeListener);
        txtNumBI.textProperty().addListener(changeListener);
        txtMorada.textProperty().addListener(changeListener);
        dataNascimentoInput.valueProperty().addListener(changeListener);
        btnFechar.setOnMouseClicked(getMouseClickedEventHandler());
        btnOk.setOnMouseClicked(getMouseClickedEventHandler());
        close.setOnMouseClicked(getMouseClickedEventHandler());
        setOnCloseRequest(getDialogLifecycleEventHandler());
    }
    
    public void removerListeners() {
        if (content == null)
            return;
        
        txtNome.textProperty().removeListener(getChangeListener());
        txtEmail.textProperty().removeListener(changeListener);
        txtNumTelefone.textProperty().removeListener(changeListener);
        txtNumBI.textProperty().removeListener(changeListener);
        txtMorada.textProperty().removeListener(changeListener);
        dataNascimentoInput.valueProperty().removeListener(changeListener);
        btnFechar.setOnMouseClicked(null);
        btnOk.setOnMouseClicked(null);
        close.setOnMouseClicked(null);
        setOnCloseRequest(null);
    }

    public ChangeListener<Object> getChangeListener() {
        if (changeListener == null)
            changeListener = new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                    if (observable.equals(txtNome.textProperty()))
                        observarNome((String) newValue);
                    else if (observable.equals(txtEmail.textProperty()))
                        observarEmail((String) newValue);
                    else if (observable.equals(txtNumTelefone.textProperty()))
                        observarNumTelefone((String) newValue);
                    else if (observable.equals(txtNumBI.textProperty()))
                        observarNumBilheteId((String) newValue);
                    else if (observable.equals(txtMorada.textProperty()))
                        observarMorada((String) newValue);
                    else if (observable.equals(dataNascimentoInput.valueProperty()))
                        observarDataNascimento((LocalDate) newValue);
                }
            };
        
        return changeListener;
    }
    
    private void observarMorada(String morada) {
        this.morada = morada;
    }
    
    private void observarDataNascimento(LocalDate localDate) {
        dataNascimento = localDate;
    }
    
    private void observarNome(String nome) {
        this.nome = nome;
    }
    
    private void observarEmail(String email) {
        this.email = email;
    }
    
    private void observarNumBilheteId(String numBilheteId) {
        numBilheteIdentidade = numBilheteId;
    }
    
    private void observarNumTelefone(String numTelefoneStr) {
        this.numTelefoneStr = numTelefoneStr;
        
        try {
            numTelefone = Integer.valueOf(numTelefoneStr);
        } catch (NumberFormatException e) {
            numTelefone = null;
        }
    }

    public EventHandler<MouseEvent> getMouseClickedEventHandler() {
        if (mouseClickedEventHandler == null) 
            mouseClickedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!(event.getEventType() == MouseEvent.MOUSE_CLICKED)) {
                        return;
                    }
                    
                    Object source = event.getSource();
                    
                    if (!(source.equals(btnFechar) || source.equals(btnOk) || source.equals(close))) {
                        return;
                    }
                    
                    if (source.equals(btnOk))
                        cliqueBtnOk();
                    else
                        cliqueBtnFechar();
                }
            };
        
        return mouseClickedEventHandler;
    }

    public EventHandler<DialogEvent> getDialogLifecycleEventHandler() {
        if (dialogLifecycleEventHandler == null) {
            dialogLifecycleEventHandler = new EventHandler<DialogEvent>() {
                @Override
                public void handle(DialogEvent event) {
                    if (!(event.getEventType().equals(DialogEvent.DIALOG_CLOSE_REQUEST))) {
                        event.consume();
                    }
                }
            };
        }
        
        return dialogLifecycleEventHandler;
    }
    
    private DialogInserirFuncionario getDialogInserirFuncionario() {
        return this;
    }
    
    private void cliqueBtnFechar() {
        setResult(ButtonType.CANCEL);
        getDialogLifecycleEventHandler().handle(new DialogEvent(getDialogInserirFuncionario(), DialogEvent.DIALOG_CLOSE_REQUEST));
    }
    
    private void cliqueBtnOk() {
        if (usuario == null || funcionarioRepository == null) {
            return;
        }

        List<Boolean> camposInvalidos = new ArrayList<>();
        camposInvalidos.add(!FuncionarioValidator.isNomeValid(nome));
        camposInvalidos.add(!FuncionarioValidator.isDataNascimentoValid(dataNascimento));
        camposInvalidos.add(!FuncionarioValidator.isNumBIValid(numBilheteIdentidade));
        camposInvalidos.add(!FuncionarioValidator.isNumTelefoneValid(numTelefoneStr));
        camposInvalidos.add(!FuncionarioValidator.isMoradaValid(morada));
        camposInvalidos.add(!FuncionarioValidator.isEmailValid(email));

        if (temCamposInvalidos(camposInvalidos)) {
            determinarRestantesCamposInvalidos(camposInvalidos);
            return;
        }

        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setDataRegisto(System.currentTimeMillis());
        f.setDataNascimento(System.currentTimeMillis());
        f.setIdGerente(usuario.getIdTipo());
        f.setNumTelefone(numTelefone);
        f.setEmail(email);
        f.setNumBilheteIdentidade(numBilheteIdentidade);

        getDialogPane().getContent().setMouseTransparent(true);

        rslt = funcionarioRepository.inserirFuncionario(f);

        getDialogPane().getContent().setMouseTransparent(false);
        
        if (rslt instanceof Result.Error) {
            getAlert().setContentText(rslt.getValue().toString());
            getAlert().show();
        } else {
            Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;

            if (!success.getData()) {
                return;
            }
            
            setResult(ButtonType.OK);
            getDialogLifecycleEventHandler().handle(new DialogEvent(getDialogInserirFuncionario(), DialogEvent.DIALOG_CLOSE_REQUEST));
        }
    }
    
    private boolean temCamposInvalidos(List<Boolean> camposInvalidos){
        if (camposInvalidos.isEmpty())
            return false;
        
        for (Boolean estaInvalido : camposInvalidos) {
            if (estaInvalido)
                return true;
        }
        
        return false;
    }
    
    private Alert getAlert() {
        if (alert == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
        }
        
        return alert;
    }
    
    private void determinarRestantesCamposInvalidos(List<Boolean> camposInvalidos) {
        if (camposInvalidos.isEmpty())
            return;
        
        for (int i = 0 ; i < camposInvalidos.size() ; i++) {
            boolean estaInvalido = camposInvalidos.get(i);
            
            Tooltip tooltip = null;
            String text = null;
            
            switch (i) {
                case 0:
                    tooltip = obterTooltip(txtNome);
                        text = estaInvalido ? "Nome inválido" : "Nome válido";
                            break;
                case 1:
                    tooltip = obterTooltip(dataNascimentoInput);
                        text = estaInvalido ? "Data inválida" : "Data válida";
                            break;
                case 2:
                    tooltip = obterTooltip(txtNumBI);
                        text = estaInvalido ? "Número de B.I inválido" : "Número de B.I válido";    
                            break;
                case 3:
                    tooltip = obterTooltip(txtNumTelefone);
                        text = estaInvalido ? "Número de telefone inválido" : "Número de telefone válido";
                            break;
                case 4:
                    tooltip = obterTooltip(txtMorada);
                        text = estaInvalido ? "Morada inválida" : "Morada válida";
                            break;
                case 5:
                    tooltip = obterTooltip(txtEmail);
                        text = estaInvalido ? "Email inválido" : "Email válido";
                            break;
            }
            
            if (tooltip == null)
                continue;
            
            tooltip.setText(text);
        }
    }
    
    private Tooltip obterTooltip(Object source) {
        if (!(source instanceof TextField || source instanceof DatePicker))
            return null;
            
        Tooltip tooltip;
        
        if (source instanceof TextField) {
            TextField textField = ((TextField) source);
            tooltip = textField.getTooltip();
            
            if (tooltip == null) {
                textField.setTooltip(new Tooltip());
                tooltip = textField.getTooltip();
            }
        } else {
            DatePicker datePicker = ((DatePicker) source);
            tooltip = datePicker.getTooltip();
            
            if (tooltip == null) {
                datePicker.setTooltip(new Tooltip());
                tooltip = datePicker.getTooltip();
            }
        }
        
        return tooltip;
    }
    
    public void setFuncionarioRepository(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}