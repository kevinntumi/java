/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import edu.uem.sgh.util.Path;
import javafx.scene.control.DialogPane;

/**
 *
 * @author Kevin Ntumi
 */
public class DefaultDialogPane extends DialogPane {
    public DefaultDialogPane() {
        getStylesheets().add(Path.getStylesheetResourceURL("style", "\\gerente\\dialog\\").toExternalForm());
    }
}