/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller;

import javafx.scene.Parent;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class AbstractController {
    public abstract void adicionarListeners();
    public abstract void removerListeners();
    public abstract Parent getRoot();
}