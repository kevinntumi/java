/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.gerente;

import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Kevin Ntumi
 */
public class DialogPaneProgressBar extends DialogPane {
    private ProgressIndicator progressIndicator;
    
    public DialogPaneProgressBar() {
        setContent(progressIndicator);
    }
}