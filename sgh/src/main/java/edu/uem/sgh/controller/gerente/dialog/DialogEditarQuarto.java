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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogEditarQuarto extends Dialog<Object> {
    private QuartoRepository quartoRepository;
    private Usuario usuario;
    private TextField txtDescricao, txtPreco, txtCapacidade;
    private Button btnOk, btnEscolherFoto;
    private ImageView close, foto;
    private EventHandler<MouseEvent> mouseEventHandler;
    private EventHandler<DialogEvent> dialogEventHandler;
    private ChangeListener<Object> changeListener;
    private FileInputStream newFileInputStream;
    private ByteArrayInputStream originalFileInputStream;
    private File file = null;
    private Result<Boolean> rslt;
    private Integer capacidade = null;
    private Quarto quarto;
    private Alert alert;
    private Double preco = null;
    private String descricao = null;
    private FileChooser fileChooser;
    
    public DialogEditarQuarto() {
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

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
    
    public void adicionarListeners() {
        close.setOnMouseClicked(getMouseEventHandler());
        setOnCloseRequest(getDialogEventHandler());
        
        if (usuario == null || quarto == null || quartoRepository == null) {
            return;
        }
        
        init();
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
    
    private DialogEditarQuarto getDialogEditarQuarto() {
        return this;
    }

    private void fecharDialog() {
        ButtonType buttonType = ButtonType.CLOSE;
        
        if (getResult() == null || !(getResult().equals(buttonType) || getResult().equals(ButtonType.CANCEL) || getResult().equals(ButtonType.FINISH))) {
            setResult(buttonType);
        }
        
        getDialogEventHandler().handle(new DialogEvent(getDialogEditarQuarto(), DialogEvent.DIALOG_CLOSE_REQUEST));
    }

    private void cliqueBtnOK() {
        if (usuario == null || quarto == null || quartoRepository == null){
            return;
        }
        
        boolean hasPickedPhoto = (QuartoValidator.isFotoValid(newFileInputStream));
        
        if (!hasPickedPhoto && quarto.getFoto() == null) {
            mostrarMsg("Escolha uma foto!");
            return;
        }
        
        boolean isDescricaoValid = (QuartoValidator.isDescricaoValid(descricao));
        
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
        q.setId(quarto.getId());
        q.setCapacidade(capacidade);
        q.setDescricao(descricao);
        q.setPreco(preco);
        q.setSituacao(ServicoSituacao.EM_MANUNTENCAO);
        q.setFoto(quarto.getFoto());
        
        rslt = quartoRepository.edit(q, file);
        
        if (rslt == null) {
            return;
        }
        
        String msg = null;
    
        if (rslt instanceof Result.Error) {
            Result.Error<Boolean> error = (Result.Error<Boolean>) rslt;
            msg = (error.getException().getClass().equals(SQLException.class)) ? "Não foi possivel estabelecer uma conexão a base de dados remota." : "Não foi possivel realizar o pedido. Tente novamente em uma outra altura.";
        } else {
            Result.Success<Boolean> success = (Result.Success<Boolean>) rslt;
                    
            if (success.getData())
                fecharDialog();
            else
                msg = "Não foi possivel realizar o pedido neste momento. Tente novamente numa outra altura.";
        }
        
        if (msg != null) {
            mostrarMsg(msg);
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
            newFileInputStream = null;
            return;
        }
        
        newFileInputStream = obterFileInputStreamViaFile(selectedFile);
        file = selectedFile;
        foto.setImage(new Image(newFileInputStream));
    }

    public ChangeListener<Object> getChangeListener() {
        if (changeListener == null) {
            changeListener = (ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
                System.out.println(observable + ": " + newValue);
                
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
        descricao = (newValue == null) ? null : (String) newValue;
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
            capacidade = null;
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
            preco = null;
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
    
    private Boolean isSamePhoto (FileInputStream originalPhotoFileInputStream, FileInputStream newPhotoFileInputStream) {
        Boolean isSamePhoto;
        
        if (originalPhotoFileInputStream == null || newPhotoFileInputStream == null) {
            return null;
        }
        
        try {
            isSamePhoto = IOUtils.contentEquals(originalPhotoFileInputStream, newPhotoFileInputStream);
        } catch (IOException iOException) {
            isSamePhoto = false;
        }
        
        return isSamePhoto;
    }

    private FileInputStream obterFileInputStreamViaFile(File selectedFile) {
        FileInputStream targetStream;
        
        try {
            targetStream = FileUtils.openInputStream(selectedFile);
        } catch (IOException e) {
            targetStream = null;
        }
        
        return targetStream;
    }
    
    private InputStream obterInputStreamViaBlob(Blob blob) {
        if (blob == null) {
            return null;
        }
        
        InputStream inputStream;
        
        try {
            inputStream = quarto.getFoto().getBinaryStream();
        } catch (SQLException | NullPointerException e) {
            inputStream = null;
        }
        
        return inputStream;
    }

    private void init() {
        if (usuario == null || quarto == null || quartoRepository == null) {
            return;
        }
        
        txtCapacidade.setText(quarto.getCapacidade() + "");
        txtDescricao.setText(quarto.getDescricao());
        txtPreco.setText(quarto.getPreco() + "");
        originalFileInputStream  = (ByteArrayInputStream) obterInputStreamViaBlob(quarto.getFoto());
        
        if (originalFileInputStream == null)
            return;
        
       
        Image img = new Image(originalFileInputStream);

        if (img.errorProperty().get()) {
            return;
        }
        
        foto.setImage(img);
    }
}