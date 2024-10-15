/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.model.Usuario;
import edu.uem.sgh.repository.servico.ServicoRepository;
import edu.uem.sgh.util.Path;
import java.io.IOException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogInserirServico extends Stage {
    private DialogInserirServicoController controller = null;
    
    public DialogInserirServico() {
        FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL("DialogInserirServico", "\\gerente\\dialog\\"));
        Parent content;
        
        try {
            content = fXMLLoader.load();
        } catch (IOException e) {
            return;
        }
        
        controller = fXMLLoader.getController();
        initModality(Modality.NONE);
        initStyle(StageStyle.UNDECORATED);
        setScene(new Scene(content));
    }

    public void setUsuario(Usuario usuario) {
        if (controller == null)
            return;
        
        controller.setUsuario(usuario);
    }

    public void setParentEventHandler(EventHandler<Event> parentEventHandler) {
        if (controller == null)
            return;
        
        controller.setParentEventHandler(parentEventHandler);
    }

    public void setServicoRepository(ServicoRepository servicoRepository) {
        if (controller == null)
            return;
        
        controller.setServicoRepository(servicoRepository);
    }
    
    public void adicionarListeners() {
        if (controller == null)
            return;
        
        controller.adicionarListeners();
    }

    public void removerListeners() {
        if (controller == null)
            return;
        
        controller.removerListeners();
    }

    public ImageView getCloseButton() {
        if (controller == null)
            return null;
        
        return controller.getCloseButton();
    }

    public Button getBtnAdicionar() {
        if (controller == null)
            return null;
        
        return controller.getBtnAdicionar();
    }

    public DialogInserirServicoController getController() {
        return controller;
    }
    
    public Parent getRoot() {
        if (controller == null)
            return null;
        
        return controller.getRoot();
    }
}