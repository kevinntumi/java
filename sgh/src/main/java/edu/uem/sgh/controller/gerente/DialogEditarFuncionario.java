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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogEditarFuncionario  extends Dialog<Object> {
    private Parent content;
    private final String layoutName = "DialogInserirFuncionario", additionalPath = "\\gerente\\dialog\\";
    private String nome = null, email = null, morada = null, numTelefoneStr = null, numBilheteIdentidade = null;
    private LocalDate dataNascimento = null;
    private Integer numTelefone;
    private TextField txtNome, txtEmail, txtNumTelefone, txtNumBI, txtMorada;
    private DatePicker dataNascimentoInput;
    private ChangeListener<Object> changeListener;
    private EventHandler<DialogEvent> eventHandler;
    private FuncionarioRepository funcionarioRepository;
    private Result<Boolean> rslt = null;
    private Usuario usuario;
    
    public DialogEditarFuncionario() {
        setTitle("Novo funcionário");
        
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
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        System.out.println(getDialogPane().lookupButton(ButtonType.OK).getStyle());
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
        setOnCloseRequest(getEventHandler());
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
    
    private DialogEditarFuncionario getDialogEditarFuncionario(){
        return this;
    }

    public EventHandler<DialogEvent> getEventHandler() {
        if (eventHandler == null) 
            eventHandler = new EventHandler<DialogEvent>() {
                @Override
                public void handle(DialogEvent event) {
                    DialogEditarFuncionario dialogEditarFuncionario = getDialogEditarFuncionario();
                    
                    if (usuario == null || funcionarioRepository == null || !event.getSource().equals(dialogEditarFuncionario)) {
                        event.consume();
                        return;
                    }
                    
                    if (!FuncionarioValidator.isNomeValid(nome)) {
                        System.out.println("Nome invalido");
                        return;
                    }
                    
                    if (!FuncionarioValidator.isDataNascimentoValid(dataNascimento)) {
                        System.out.println("Data de Nascimento invalida");
                        return;
                    }
                    
                    if (!FuncionarioValidator.isNumBIValid(numBilheteIdentidade)) {
                        System.out.println("NumBI invalido");
                        return;
                    }
                    
                    if (!FuncionarioValidator.isNumTelefoneValid(numTelefoneStr)) {
                        System.out.println("Num telefone invalido");
                        return;
                    }
                    
                    if (!FuncionarioValidator.isMoradaValid(morada)) {
                        System.out.println("Morada invalida");
                        return;
                    }
                    
                    if (!FuncionarioValidator.isEmailValid(email)) {
                        System.out.println("Email invalido");
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
                    
                    dialogEditarFuncionario.getDialogPane().getContent().setMouseTransparent(true);
                    
                    rslt = funcionarioRepository.inserirFuncionario(f);
                    
                    dialogEditarFuncionario.getDialogPane().getContent().setMouseTransparent(false);
                    
                    if (rslt instanceof Result.Error) {
                        event.consume();
                    } else {
                        Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;
                        System.out.println(success);
                        if (!success.getData()) {
                            event.consume();
                        }
                    }
                }
            };
        
        return eventHandler;
    }
    
    public void setFuncionarioRepository(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}