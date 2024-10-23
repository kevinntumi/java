/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

/**
 *
 * @author Kevin Ntumi
 */
public class LoginValidator {
    private static final int LEAST_LETTER_COUNT = 4;
    private static final int LEAST_NUMBER_COUNT = 1;
    
    public static boolean isCodigoUsuarioValid(Long codigoUsuario) {
        if(codigoUsuario == null || codigoUsuario < 0) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isPasswordValid(String password) {
        if (password == null || password.isBlank()) return false;
        
        int letterCount = 0, numberCount = 0;
        char[] passwordCharacters = password.toCharArray();
        
        for (char ch : passwordCharacters) {
            if (!Character.isLetterOrDigit(ch)) return false;
            
            if (Character.isLetter(ch)) 
                letterCount += 1;
            else
                numberCount += 1;
        }
        
        return (letterCount >= LEAST_LETTER_COUNT && numberCount >= LEAST_NUMBER_COUNT);
    }

    public static int getLeastLetterCount() {
        return LEAST_LETTER_COUNT;
    }

    public static int getLeastNumberCount() {
        return LEAST_NUMBER_COUNT;
    }
}