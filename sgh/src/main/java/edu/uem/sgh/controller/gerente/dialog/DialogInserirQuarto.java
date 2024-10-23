/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente.dialog;

import edu.uem.sgh.helper.ServicoSituacao;
import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.quarto.QuartoRepository;
import edu.uem.sgh.util.Path;
import edu.uem.sgh.util.QuartoValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogInserirQuarto extends Dialog<Object> {
    private QuartoRepository quartoRepository;
    private Usuario usuario;
    private TextField txtDescricao, txtPreco, txtCapacidade;
    private Button btnOk, btnEscolherFoto;
    private ImageView close, foto;
    private EventHandler<MouseEvent> mouseEventHandler;
    private EventHandler<DialogEvent> dialogEventHandler;
    private ChangeListener<Object> changeListener;
    private Result<Boolean> rslt;
    private FileInputStream fis;
    private File file;
    private Integer capacidade = null;
    private Alert alert;
    private Double preco = null;
    private FileChooser fileChooser;
    
    public DialogInserirQuarto() {
        FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("DialogInserirQuarto", "\\gerente\\dialog\\"));
        Parent content;
        
        try {
            content = fXMLLoader.load();
        } catch (IOException e) {
            return;
        }
        
        initStyle(StageStyle.UNDECORATED);
        ObservableList<Node> children = content.getChildrenUnmodifiable();
        txtCapacidade = (TextField) findById("txtCapacidade", children);
        txtPreco = (TextField) findById("txtPreco", children);
        txtDescricao = (TextField) findById("txtDescricao", children);
        btnOk = (Button) findById("btnOk", children);
        close = (ImageView) findById("close", children);
        foto = (ImageView) findById("foto", children);
        btnEscolherFoto = (Button) findById("btnEscolherFoto", children);
        getDialogPane().setContent(content);
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

    public void setQuartoRepository(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void adicionarListeners() {
        close.setOnMouseClicked(getMouseEventHandler());
        setOnCloseRequest(getDialogEventHandler());
        
        if (usuario == null || quartoRepository == null) {
            return;
        }
        
        btnOk.setOnMouseClicked(getMouseEventHandler());
        btnEscolherFoto.setOnMouseClicked(getMouseEventHandler());
        txtCapacidade.textProperty().addListener(getChangeListener());
        txtDescricao.textProperty().addListener(getChangeListener());
        txtPreco.textProperty().addListener(getChangeListener());
    }
    
    public void removerListeners() {
        close.setOnMouseClicked(null);
        setOnCloseRequest(null);
        btnOk.setOnAction(null);
        btnEscolherFoto.setOnMouseClicked(null);
        txtCapacidade.textProperty().removeListener(getChangeListener());
        txtDescricao.textProperty().removeListener(getChangeListener());
        txtPreco.textProperty().removeListener(getChangeListener());
    }

    public EventHandler<MouseEvent> getMouseEventHandler() {
        if (mouseEventHandler == null) {
            mouseEventHandler = (MouseEvent event) -> {
                if (!(event.getEventType().equals(MouseEvent.MOUSE_CLICKED))) {
                    return;
                }
                
                Object source = event.getSource();
                
                if (source.equals(btnOk))
                    cliqueBtnOK();
                else if (source.equals(close))
                    fecharDialog();
                else if (source.equals(btnEscolherFoto))
                    escolherFoto();
            };
        }
        
        return mouseEventHandler;
    }

    public EventHandler<DialogEvent> getDialogEventHandler() {
        if (dialogEventHandler == null) {
            dialogEventHandler = (DialogEvent event) -> {
                if (!(event.getEventType().equals(DialogEvent.DIALOG_CLOSE_REQUEST))) {
                    event.consume();
                }
            };
        }
        
        return dialogEventHandler;
    }
    
    private DialogInserirQuarto getDialogInserirQuarto() {
        return this;
    }

    private void fecharDialog() {
        ButtonType buttonType = ButtonType.CLOSE;
        
        if (getResult() == null || !(getResult().equals(buttonType) || getResult().equals(ButtonType.CANCEL) || getResult().equals(ButtonType.FINISH))) {
            setResult(buttonType);
        }
        
        getDialogEventHandler().handle(new DialogEvent(getDialogInserirQuarto(), DialogEvent.DIALOG_CLOSE_REQUEST));
    }

    private void cliqueBtnOK() {
        if (usuario == null || quartoRepository == null){
            return;
        }
        
        boolean isFotoValid = (QuartoValidator.isFotoValid(fis));
        
        if (!isFotoValid) {
            mostrarMsg("Foto invalida!");
            return;
        }
        
        boolean isDescricaoValid = (QuartoValidator.isDescricaoValid(txtDescricao.getText()));
        
        if (!isDescricaoValid) {
            mostrarMsg("Descrição invalida!");
            return;
        }
        
        boolean isCapacidadeValid = (QuartoValidator.isCapacidadeValid(capacidade));
        
        if (!isCapacidadeValid) {
            mostrarMsg("Capacidade invalida!");
            return;
        }
        
        boolean isPrecoValid = (QuartoValidator.isPrecoValid(preco));
        
        if (!isPrecoValid) {
            mostrarMsg("Preço invalido!");
            return;
        }
        
        Quarto q = new Quarto();
        q.setCapacidade(capacidade);
        q.setDescricao(txtDescricao.getText());
        q.setPreco(preco);
        q.setSituacao(ServicoSituacao.EM_MANUNTENCAO);
        
        rslt = quartoRepository.add(q, file);
        
        if (rslt == null) {
            return;
        }
        
        String descricao = null;
    
        if (rslt instanceof Result.Error) {
            Result.Error<Boolean> error = (Result.Error<Boolean>) rslt;
            descricao = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
        } else {
            Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;
                    
            if (success.getData())
                fecharDialog();
            else
                descricao = "Não foi possivel realizar o pedido neste momento. Tente novamente numa outra altura.";
        }
        
        if (descricao != null) {
            mostrarMsg(descricao);
        }
    }

    private void escolherFoto() {
        File selectedFile = getFileChooser().showOpenDialog(getOwner());
        
        if (selectedFile == null) {
            return;
        }
        
        URL url;
        
        try {
            url = selectedFile.toURI().toURL();
        } catch (MalformedURLException e) {
            url = null;
        }
        
        if (url == null) {
            return;
        }
        
        fis = obterFileInputStreamViaFile(selectedFile);
        foto.setImage(new Image(fis));
        file = selectedFile;
    }

    public ChangeListener<Object> getChangeListener() {
        if (changeListener == null) {
            changeListener = (ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
                if (observable.equals(txtDescricao.textProperty())) 
                    observarDescricao(newValue);
                else if (observable.equals(txtCapacidade.textProperty()))
                    observarCapacidade(newValue);
                else if (observable.equals(txtPreco.textProperty()))
                    observarPreco(newValue);
            };
        }
        
        return changeListener;
    }
    
    private void observarDescricao(Object newValue) {
        if (newValue == null) {
            return;
        }
        
        
    }
    
    private Alert getAlert() {
        if (alert == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
        }
        
        return alert;
    }
    
    private void mostrarMsg(String descricao) {
        if (descricao == null) {
            return;
        }
        
        getAlert().setContentText(descricao);
        getAlert().show();
    }

    private void observarCapacidade(Object newValue) {
        if (newValue == null) {
            return;
        }
        
        String str = (String) newValue;
        
        try {
            capacidade = Integer.valueOf(str);
        } catch (NumberFormatException ex) {
            capacidade = null;
        }
    }

    private void observarPreco(Object newValue) {
        if (newValue == null) {
            return;
        }
        
        String str = (String) newValue;
        
        try {
            preco = Double.valueOf(str);
        } catch (NumberFormatException ex) {
            preco = null;
        }
    }

    public FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open a file");
            fileChooser.setInitialFileName("C:\\");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All images", "*.jpeg", "*.jpg", "*.png", "*.gif", "*.bmp"));
        }
        
        return fileChooser;
    }

    private FileInputStream obterFileInputStreamViaFile(File selectedFile) {
        FileInputStream targetStream;
        
        try {
            targetStream = new FileInputStream(selectedFile);
        } catch (IOException e) {
            targetStream = null;
        }
        
        return targetStream;
    }
}