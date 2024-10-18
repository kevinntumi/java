/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente.dialog;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.Path;
import edu.uem.sgh.util.ServicoValidator;
import java.io.IOException;
import java.sql.SQLException;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogEditarServico extends Dialog<Object> {
    private TextField txtDescricao;
    private ComboBox<String> cbSituacao;
    private Button btnOK;
    private EventHandler<DialogEvent> eventHandler;
    private ServicoRepository servicoRepository;
    private ChangeListener<Object> changeListener;
    private StringProperty descricao, situacao;
    private edu.uem.sgh.schema.Servico servico = null;
    private Usuario usuario;
    private Alert alert;
    
    public DialogEditarServico() {
        setTitle("Novo serviço");
        FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("DialogInserirServico", "\\gerente\\dialog\\"));
        Parent content;
        
        try {
            content = fXMLLoader.load();
        } catch (IOException e) {
            return;
        }
        
        initStyle(StageStyle.UNDECORATED);
        cbSituacao = (ComboBox<String>) findById("cbSituacao", content.getChildrenUnmodifiable());
        
        if (!cbSituacao.getItems().isEmpty()) {
            cbSituacao.getItems().clear();
        }
        
        ServicoSituacao[] servicoSituacao = ServicoSituacao.values();
        
        for (ServicoSituacao situacao : servicoSituacao) {
            cbSituacao.getItems().add(ServicoSituacao.obterPorValor(situacao));
        }
        
        txtDescricao = (TextField)  findById("txtDescricao", content.getChildrenUnmodifiable());
        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        btnOK = (Button) getDialogPane().lookupButton(ButtonType.OK);
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

    public void setDescricao(StringProperty descricao) {
        this.descricao = descricao;
    }

    public void setSituacao(StringProperty situacao) {
        this.situacao = situacao;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setServico(edu.uem.sgh.schema.Servico servico) {
        this.servico = servico;
    }
    
    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    public void adicionarListeners() {
        if (servico == null) {
            System.out.println(btnOK.getOnAction());
            System.out.println(btnOK.getOnMouseClicked());
        }
        
        if (changeListener == null) {
            changeListener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                    if (observable.equals(cbSituacao.getSelectionModel().selectedIndexProperty()))
                        observarIndice(newValue);
                    else if (observable.equals(txtDescricao.textProperty()))
                        observarDescricao(newValue);
                }
            };
        }
        
        if (eventHandler == null) {
            eventHandler = (DialogEvent event) -> {
                boolean isUsuarioNotValid = (usuario == null || usuario.getTipo() == null || usuario.getTipo() != Usuario.Tipo.GERENTE), isRepositoryNotValid = (servicoRepository == null);
                
                if (isUsuarioNotValid || isRepositoryNotValid) {
                    if (isUsuarioNotValid) {
                        mostrarMsgErro("Inicie a sessao para realizar o pedido!");
                    } else {
                        mostrarMsgErro("Não foi possivel estabelecer uma conexão a base de dados remota.");
                    }
                    
                    return;
                }
                
                getDialogPane().getContent().setMouseTransparent(true);
                servico.setDescricao(descricao.get());
                servico.setSituacao(situacao.get());
                Result<Boolean> rslt = servicoRepository.edit(servico);
                getDialogPane().getContent().setMouseTransparent(false);
                
                if (rslt == null) {
                    return;
                }
                
                boolean consumeEvent = false;
                
                if (rslt instanceof Result.Error) {
                    Result.Error<Boolean> error = (Result.Error<Boolean>) rslt;
                    String descricao1 = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
                    mostrarMsgErro(descricao1);
                    consumeEvent = true;
                } else {
                    Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;
                    
                    if (!success.getData()) {
                        consumeEvent = true;
                    }
                }
                
                if (consumeEvent)
                    event.consume();
            };
        } else {
            setOnCloseRequest(null);
        }
        
        setOnCloseRequest(eventHandler);
        cbSituacao.getSelectionModel().selectedIndexProperty().addListener(changeListener);
        txtDescricao.textProperty().addListener(changeListener);
    }

    public void removerListeners() {
        setOnCloseRequest(null);
        cbSituacao.getSelectionModel().selectedIndexProperty().removeListener(changeListener);
        txtDescricao.textProperty().removeListener(changeListener);
    }
    
    private void mostrarMsgErro(String descricao) {
        if (descricao == null) {
            return;
        }
        
        getAlert().setContentText(descricao);
        getAlert().show();
    }
    
    private void observarIndice(Object value) {
        if (!(value instanceof Number))
            return;

        Number number = (Number) value;
        Integer selectedIndex = number.intValue();
        btnOK.setDisable(!temLinhaSelecionada(selectedIndex) || !ServicoValidator.isDescricaoValid(txtDescricao.getText()));
        situacao.set(temLinhaSelecionada(selectedIndex) ? cbSituacao.getSelectionModel().getSelectedItem() : null);
    }
     
    private Alert getAlert() {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
        }
        
        return alert;
    }

    private void observarDescricao(Object value) {
        if (!(value instanceof String))
            return;

        descricao.set((String) value);
        btnOK.setDisable(!temLinhaSelecionada(cbSituacao.getSelectionModel().getSelectedIndex()) || !ServicoValidator.isDescricaoValid(descricao.get()));
    }
    
    private boolean temLinhaSelecionada(int selectedIndex) {
        return selectedIndex != -1;
    }
}