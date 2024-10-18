/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import edu.uem.sgh.util.Path;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogPaneMostrarMsgErro extends DialogPane {
    private Parent content;
    private Label lblDescription;
    private Label lblTitle;
    private final String layoutName = "error_dialog";
            
    public DialogPaneMostrarMsgErro() {
        FXMLLoader fXMLLoader = new FXMLLoader(Path.getFXMLURL(layoutName));
        
        try {
            content = fXMLLoader.load();
        } catch (IOException e) {
            return;
        }
        
        lblTitle = (Label) findById("title", content.getChildrenUnmodifiable());
        lblDescription = (Label) findById("description", content.getChildrenUnmodifiable());
        setContent(content);
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
    
    protected void setTitle(String title) {
        if (title == null || lblTitle == null)
            return;
        
        lblTitle.setText(title);
    }
    
    protected void setDescription(String description) {
        if (description == null || lblDescription == null)
            return;
        
        lblDescription.setText(description);
    }
}
