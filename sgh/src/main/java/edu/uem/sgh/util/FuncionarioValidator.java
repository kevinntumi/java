/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 *
 * @author Kevin Ntumi
 */
public class FuncionarioValidator {
    private final static int LEAST_NAME_LENGTH = 10;
    private final static int PHONE_NUMBER_LENGTH = 9;
    private final static int BILHETE_ID_LENGTH = 13;
    private final static int MORADA_LENGTH = 5;
    private final static String EMAIL_REGEX_PATTERN = "^(.+)@(\\S+)$";
    
    public static boolean isNomeValid(String nome) {
        if (nome == null || nome.isBlank() || nome.length() < LEAST_NAME_LENGTH)
            return false;
        
        int letterCount = 0, whitespaceCount = 0, length = nome.length();
        
        for (int i = 0 ; i < length; i++) {
            char ch = nome.charAt(i);
            
            if (Character.isLetter(ch))
                letterCount += 1;
            
            if (Character.isWhitespace(ch))
                whitespaceCount += 1;
        }
        
        return (letterCount + whitespaceCount) == length;
    }
    
    public static boolean isNumTelefoneValid(String numTelStr) {
        if (numTelStr == null || numTelStr.isBlank() || numTelStr.length() != PHONE_NUMBER_LENGTH)
            return false;
        
        int digitCount = 0, length = numTelStr.length();
        
        for (int i = 0 ; i < length; i++) {
            char ch = numTelStr.charAt(i);
            
            if (Character.isDigit(ch))
                digitCount += 1;
        }
        
        return digitCount == length;
    }
    
    public static boolean isNumBIValid(String numBilheteId) {
        if (numBilheteId == null || numBilheteId.isBlank() || numBilheteId.length() != BILHETE_ID_LENGTH)
            return false;
        
        int digitCount = 0, length = numBilheteId.length() - 2;
        
        for (int i = 0 ; i <= length; i++) {
            char ch = numBilheteId.charAt(i);
            
            if (Character.isDigit(ch))
                digitCount += 1;
        }
        
        if (digitCount != 12) {
            return false;
        } else {
            char lastCh = numBilheteId.charAt(numBilheteId.length() - 1);
            return Character.isLetter(lastCh) && Character.isUpperCase(lastCh);
        }
    }
    
    public static boolean isMoradaValid(String morada) {
        if (morada == null || morada.isBlank() || morada.length() < MORADA_LENGTH) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isDataNascimentoValid(LocalDate date) {
        return date != null;
    }
    
    public static boolean isEmailValid(String emailAddress) {
        if (emailAddress == null || emailAddress.isBlank())
            return false;
            
        return Pattern.compile(EMAIL_REGEX_PATTERN)
          .matcher(emailAddress)
          .matches();
    }
}