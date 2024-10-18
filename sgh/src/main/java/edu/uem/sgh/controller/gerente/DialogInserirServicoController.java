/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.ServicoValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogInserirServicoController extends AbstractController implements EventHandler<ActionEvent>, Initializable {
    @FXML
    private TextField txtDescricao;
    
    @FXML
    private VBox root;
    
    @FXML
    private ComboBox<String> cbSituacao;
    
    private EventHandler<Event> parentEventHandler;
    private ReadOnlyIntegerProperty selectedIndexProperty;
    private StringProperty descricaoProperty;
    private ChangeListener<Object> changeListener;
    private ServicoRepository servicoRepository;
    private String descricao = null, situacao = null;
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUiClassID(getClass().getTypeName());
        selectedIndexProperty = cbSituacao.getSelectionModel().selectedIndexProperty();
        ServicoSituacao[] servicoSituacao = ServicoSituacao.values();
        
        for (ServicoSituacao situacao : servicoSituacao) {
            cbSituacao.getItems().add(ServicoSituacao.obterPorValor(situacao));
        }
        
        descricaoProperty = txtDescricao.textProperty();
        changeListener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (observable.equals(selectedIndexProperty))
                    observarIndice(newValue);
                else if (observable.equals(descricaoProperty))
                    observarDescricao(newValue);
            }
        };
    }

    @Override
    public void adicionarListeners() {
        selectedIndexProperty.addListener(changeListener);
        descricaoProperty.addListener(changeListener);
    }

    @Override
    public void removerListeners() {
        selectedIndexProperty.removeListener(changeListener);
        descricaoProperty.removeListener(changeListener);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    private boolean temLinhaSelecionada(int selectedIndex) {
        return selectedIndex != -1;
    }

    public void setParentEventHandler(EventHandler<Event> parentEventHandler) {
        this.parentEventHandler = parentEventHandler;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null && this.usuario.equals(usuario)) {
            return;
        }
        
        this.usuario = usuario;
    }
    
    public void clicarBotao() {
    
    }
    
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        
        boolean isUsuarioNotValid = (usuario == null || usuario.getTipo() == null || usuario.getTipo() != Usuario.Tipo.GERENTE), 
                isDescricaoNotValid = (descricao == null || descricao.isBlank()),
                isSituacaoNotValid = (situacao == null);
        
        if (isUsuarioNotValid || isDescricaoNotValid || isSituacaoNotValid) {
            if (isUsuarioNotValid) {
                
            } else if (isDescricaoNotValid) {
                
            } else {
                
            }
            
            return;
        }
        
        edu.uem.sgh.schema.Servico servico = new edu.uem.sgh.schema.Servico();
        servico.setDescricao(descricao);
        servico.setSituacao(situacao);
        servico.setIdGerente(usuario.getIdTipo());
        servico.setDataRegisto(System.currentTimeMillis());
        Result<Boolean> r = servicoRepository.add(servico);
        
        if (r == null) {
            return;
        }
        
        if (r instanceof Result.Error) {
            //mostrarMsgErro
        } else {
            Result.Success<Boolean> success = (Result.Success<Boolean>) r;
            boolean inseriu = success.getData();
            
            if (inseriu) {
                parentEventHandler.handle(new Event(source, event.getTarget(), event.getEventType()));
            } else {
                
            }
        }
    }
    
    private void observarIndice(Object value) {
        if (!(value instanceof Number))
            return;

        Number number = (Number) value;
        Integer selectedIndex = number.intValue();
//        btnAdicionar.setDisable(!temLinhaSelecionada(selectedIndex) || !ServicoValidator.isDescricaoValid(txtDescricao.getText()));
        situacao = temLinhaSelecionada(selectedIndex) ? cbSituacao.getSelectionModel().getSelectedItem() : null;
    }

    private void observarDescricao(Object value) {
        if (!(value instanceof String))
            return;

        descricao = (String) value;
     //   btnAdicionar.setDisable(!temLinhaSelecionada(selectedIndexProperty.get()) || !ServicoValidator.isDescricaoValid(descricao));
    }
}