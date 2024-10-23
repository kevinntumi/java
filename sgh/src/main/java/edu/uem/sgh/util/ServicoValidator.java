/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

/**
 *
 * @author Kevin Ntumi
 */
public class ServicoValidator {
    public static boolean isDescricaoValid(String descricao) {
        if (descricao == null || descricao.isBlank())
            return false;
        
        return descricao.length() > 5;
    }
}
