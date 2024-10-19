/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import javafx.scene.image.Image;

/**
 *
 * @author Kevin Ntumi
 */
public class QuartoValidator {
    public static boolean isFotoValid(Image image) {
        return image != null;
    }
    
    public static boolean isDescricaoValid(String descricao) {
        if (descricao == null || descricao.isBlank() || descricao.length() < 5){
            return false;
        }
        
        return true;
    }
    
    public static boolean isCapacidadeValid(Integer capacidade) {
        if (capacidade == null || capacidade < 0) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isPrecoValid(Double preco) {
        if (preco == null || preco < 0) {
            return false;
        }
        
        return true;
    }
}