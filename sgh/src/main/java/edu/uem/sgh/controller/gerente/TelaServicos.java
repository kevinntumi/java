/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import com.gluonhq.charm.glisten.control.Dialog;
import edu.uem.sgh.controller.AbstractController;
import edu.uem.sgh.controller.DialogController;
import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.DialogDetails;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.CommandLinksDialog;
import org.controlsfx.dialog.CommandLinksDialog.CommandLinksButtonType;
import org.controlsfx.dialog.ExceptionDialog;
import org.controlsfx.dialog.ProgressDialog;

/**
 *
 * @author Kevin Ntumi
 */
public class TelaServicos extends AbstractController implements EventHandler<Event>, ChangeListener<Object>, Initializable{
    @FXML
    private StackPane root;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private TableView<edu.uem.sgh.model.table.Servico> tableView;
    
    @FXML
    private Button btnCarregar;
    
    private Usuario usuario;
    private Result<List<Servico>> rslt;
    private ServicoRepository servicoRepository;
    private DialogController dialogController;
    private List<Object> dialogs;
    private int totalTimesVisible;

    @Override
    public void adicionarListeners() {
        btnAdicionar.setOnMouseClicked(this);
        btnCarregar.setOnMouseClicked(this);
        root.visibleProperty().addListener(this);
        alterarVisibilidadeRoot(true);
    }

    @Override
    public void removerListeners() {
        btnAdicionar.setOnMouseClicked(null);
        btnCarregar.setOnMouseClicked(null);
        root.visibleProperty().removeListener(this);
        alterarVisibilidadeRoot(false);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void handle(Event event) {
        Object source = event.getSource(), eventType = event.getEventType();
        
        if (eventType == MouseEvent.MOUSE_CLICKED) {
            if (!(source.equals(btnAdicionar) || source.equals(btnCarregar))) {
                if (dialogs != null) {
                    DialogInserirServico dialogInserirServico = null;
                    
                    for (Object object : dialogs) {
                        if (!(object instanceof DialogInserirServico))
                            return; 
                        
                        dialogInserirServico = (DialogInserirServico) object;
                        
                        if (source.equals(dialogInserirServico.getCloseButton())){
                            resolverDependencias(dialogInserirServico.getClass().getTypeName(),false);
                            dialogInserirServico.showingProperty().removeListener(this);
                            dialogInserirServico.close();
                        }
                        
                        break;
                    }
                    
                    if (dialogInserirServico != null) {
                        dialogs.remove(dialogInserirServico);
                    }
                }
                
                return;
            }

            if (source.equals(btnAdicionar))
                adicionarNovoServico();
            else
                carregarServicos();
        } else if (eventType == ActionEvent.ANY) {
            if (dialogs == null)
                return;
            
            DialogInserirServico dialogInserirServico = null;
            
            for (Object object : dialogs) {
                if (!(object instanceof DialogInserirServico))
                    return;
                
                dialogInserirServico = (DialogInserirServico) object;
                
                if (!source.equals(dialogInserirServico.getRoot())) 
                    continue;
                
                resolverDependencias(dialogInserirServico.getClass().getTypeName(),false);
                dialogInserirServico.showingProperty().removeListener(this);
                dialogInserirServico.close();
                break;
            }
            
            if (dialogInserirServico != null) {
                dialogs.remove(dialogInserirServico);
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (!(newValue instanceof Usuario)){
            if (observable.equals(root.visibleProperty())) {
                if (totalTimesVisible == 0) {
                    carregarServicos();
                    totalTimesVisible++;
                }
                
                observable.removeListener(this);
            }
            
            if (dialogController != null && observable.equals(dialogController.getRoot().visibleProperty())) {
                observarVisibilidade(newValue);
                return;
            }
            
            DialogInserirServico dialogInserirServico = encontrarDialog();
            
            if (dialogInserirServico != null && observable.equals(dialogInserirServico.showingProperty())) {
                boolean isShowing = (boolean) newValue;
                resolverDependencias(dialogInserirServico.getClass().getTypeName(), isShowing);
            }
            
            return;
        }
        
        usuario = (Usuario) newValue;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUiClassID(getClass().getTypeName());
    }

    public List<Object> getDialogs() {
        if (dialogs == null) dialogs = new ArrayList<>();
        return dialogs;
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    private DialogInserirServico encontrarDialog() {
        if (dialogs == null)
            return null;
        
        for (Object object : dialogs) {
            if (object instanceof DialogInserirServico)
                return (DialogInserirServico) object;
        }
        
        return null;
    }
    
    private void adicionarNovoServico() {
        DialogInserirServico dialogInserirServico = encontrarDialog();
        
        if (dialogInserirServico == null) {
            dialogInserirServico = new DialogInserirServico();
            getDialogs().add(dialogInserirServico);
        } else {
            dialogInserirServico.showingProperty().removeListener(this);
        }
 
        dialogInserirServico.showingProperty().addListener(this);
        
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog<>();
        dialog.getDialogPane().setContent(dialogInserirServico.getRoot());
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        WritableImage transparentImage = new WritableImage(32, 32);
        stage.getIcons().add(transparentImage);
        
        dialog.setTitle("xxx");
        dialog.show();
    }
    
    private void resolverDependencias(String uiClassId, boolean add) {
        if (uiClassId == null || uiClassId.isBlank())
            return;
        
        if (uiClassId.equals(DialogInserirServico.class.getTypeName())) {
            DialogInserirServico dialogInserirServico = encontrarDialog();
            
            if (dialogInserirServico == null) {
                return;
            }
            
            DialogInserirServicoController dialogInserirServicoController = dialogInserirServico.getController();
            
            if (dialogInserirServicoController == null)
                return;
            
            if (add) {
                dialogInserirServico.setParentEventHandler(getEventHandler());
                dialogInserirServico.setServicoRepository(servicoRepository);
                dialogInserirServico.setUsuario(usuario);
                dialogInserirServico.getController().adicionarListeners();
            } else {
                dialogInserirServicoController.removerListeners();
                dialogInserirServico.setParentEventHandler(null);
                dialogInserirServico.setUsuario(null);
                dialogInserirServico.setServicoRepository(null);
            }
        }
    }
    
    private boolean pertenceAUIAtual(Object source) {
        if (source == null)
            throw new NullPointerException();
        
        ObservableList<Node> children = root.getChildrenUnmodifiable();
        
        if (children.isEmpty())
            throw new NullPointerException();
        
        for (Node node : children) {
            boolean pertenceAUI;
                    
            if (node instanceof Parent)
                pertenceAUI = pertenceAParent((Parent) node, source);
            else
                pertenceAUI = source.equals(node);
            
            if (pertenceAUI)
                return true;
        }
        
        return false;
    }
    
    private boolean pertenceAParent(Parent parent, Object child) {
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        
        if (child == null || children.isEmpty())
            throw new NullPointerException();
        
        for (Node node : children) {
            boolean pertenceAUI;
                    
            if (node instanceof Parent)
                pertenceAUI = pertenceAParent((Parent) node, child);
            else
                pertenceAUI = child.equals(node);
            
            if (pertenceAUI)
                return true;
        }
        
        return false;
    }

    private void carregarServicos() {
        if (servicoRepository == null)
            return;
        
        rslt = servicoRepository.getAll();
        
        if (rslt instanceof Result.Error) {
            Result.Error<List<Servico>> error = (Result.Error<List<Servico>>) rslt;
            String descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
            mostrarMsgErro("Ocorreu algo inesperado", descricao);
        } else {
            Result.Success<List<Servico>> success = (Result.Success<List<Servico>>) rslt;
            List<Servico> servicos = success.getData();
            
            limparTabela();
            
            if (servicos.isEmpty())
                return;
            
            for (Servico servico : servicos) {
                edu.uem.sgh.model.table.Servico s = new edu.uem.sgh.model.table.Servico(servico.getId(), servico.getDescricao(), new Date(servico.getDataRegisto()).toString(), ServicoSituacao.obterPorValor(servico.getSituacao()), servico.getGerente().getNome());
                tableView.getItems().add(s);
            }
        }
    }
    
    private void limparTabela() {
        if (tableView.getItems().isEmpty()) 
            return;
        
        tableView.getItems().clear();
    }
    
    private void mostrarMsgErro(String title, String description) {
        Parent content;
        
        if (dialogController == null) {
            FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("error_dialog"));

            try {
                content = fXMLLoader.load();
            }catch(IOException e) {
                content = null;
            }
            
            if (content == null) return;
            
            content.setVisible(false);
            dialogController = fXMLLoader.getController();
            dialogController.getRoot().visibleProperty().addListener(this);
            root.getChildren().add(content);
        } else {
            content = dialogController.getRoot();
        }
        
        if (content != null && !content.isVisible()) {
            content.setVisible(true);
        }
        
        if (dialogController == null)
            return;
        
        DialogDetails dialogDetails = new DialogDetails(title, description);
        dialogController.changed(null, null, dialogDetails);
    }
    
    private void observarVisibilidade(Object newValue) {
        if (dialogController == null)
            return;
            
        boolean estaVisivel = (boolean) newValue;
        dialogController.setLifecycleEventHandler(estaVisivel ? getEventHandler() : null);
        
        if (estaVisivel) {
            dialogController.adicionarListeners();
        } else {
            dialogController.removerListeners();
        }
    }
    
    private void alterarVisibilidadeRoot(boolean visivel) {
        if (visivel && !root.isVisible()) {
            root.setVisible(true);
        }
        
        if (!visivel && root.isVisible()) {
            root.setVisible(false);
        }
    }
    
    private EventHandler<Event> getEventHandler() {
        return this;
    }
}