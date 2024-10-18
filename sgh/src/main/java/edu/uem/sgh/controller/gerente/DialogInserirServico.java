/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.Path;
import edu.uem.sgh.util.ServicoValidator;
import java.io.IOException;
import java.sql.SQLException;
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
public class DialogInserirServico extends Dialog<Object> {
    private TextField txtDescricao;
    private ComboBox<String> cbSituacao;
    private Button btnOK;
    private EventHandler<DialogEvent> eventHandler;
    private ServicoRepository servicoRepository;
    private ChangeListener<Object> changeListener;
    private String descricao = null, situacao = null;
    private Usuario usuario;
    private Alert alert;
    
    public DialogInserirServico() {
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
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        btnOK = (Button) getDialogPane().lookupButton(ButtonType.OK);
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    public void adicionarListeners() {
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
            eventHandler = new EventHandler<DialogEvent>() {
                @Override
                public void handle(DialogEvent event) {
                    boolean isUsuarioNotValid = (usuario == null || usuario.getTipo() == null || usuario.getTipo() != Usuario.Tipo.GERENTE),
                            isRepositoryNotValid = (servicoRepository == null),
                            isDescricaoNotValid = (txtDescricao.getText() == null || txtDescricao.getText().isBlank()),
                            isSituacaoNotValid = (cbSituacao.getSelectionModel().isEmpty());
                    
                    if (isUsuarioNotValid || isRepositoryNotValid || isDescricaoNotValid || isSituacaoNotValid) {
                        System.out.println("isUsuarioNotValid: " + isUsuarioNotValid);
                        System.out.println("isDescricaoNotValid: " + isDescricaoNotValid);
                        System.out.println("isSituacaoNotValid: " + isSituacaoNotValid);
                        
                        if (isUsuarioNotValid) {
                            
                        } else if (isRepositoryNotValid) {
                            
                        } else if (isDescricaoNotValid) {

                        } else {

                        }

                        return;
                    }
                    
                    getDialogPane().getContent().setMouseTransparent(true);
                    
                    edu.uem.sgh.schema.Servico servico = new edu.uem.sgh.schema.Servico();
                    servico.setDescricao(txtDescricao.getText());
                    servico.setSituacao(situacao);
                    servico.setIdGerente(usuario.getIdTipo());
                    servico.setDataRegisto(System.currentTimeMillis());
                    
                    Result<Boolean> rslt = servicoRepository.add(servico);

                    getDialogPane().getContent().setMouseTransparent(false);
                    
                    if (rslt == null) {
                        return;
                    }
                    
                    boolean consumeEvent = false;
                    
                    if (rslt instanceof Result.Error) {
                        Result.Error<Boolean> error = (Result.Error<Boolean>) rslt;
                        String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
                        mostrarMsgErro(descricao);
                        consumeEvent = true;
                    } else {
                        Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;
                        System.out.println("" + success.getData());
                        if (!success.getData()) {
                            consumeEvent = true;
                        }
                    }
                    
                    if (consumeEvent)
                        event.consume();
                }
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
        situacao = temLinhaSelecionada(selectedIndex) ? cbSituacao.getSelectionModel().getSelectedItem() : null;
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

        descricao = (String) value;
        btnOK.setDisable(!temLinhaSelecionada(cbSituacao.getSelectionModel().getSelectedIndex()) || !ServicoValidator.isDescricaoValid(descricao));
    }
    
    private boolean temLinhaSelecionada(int selectedIndex) {
        return selectedIndex != -1;
    }
}