/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.util.regex.Pattern;

/**
 *
 * @author Kevin Ntumi
 */
public class LoginValidator {
    private static final String EMAIL_REGEX_PATTERN = "^(.+)@(\\S+)$";
    private static final int LEAST_LETTER_COUNT = 4;
    private static final int LEAST_NUMBER_COUNT = 1;
    
    public static boolean isEmailValid(String emailAddress) {
        return Pattern.compile(EMAIL_REGEX_PATTERN)
          .matcher(emailAddress)
          .matches();
    }
    
    public static boolean isPasswordValid(String password) {
        if (password == null || password.isBlank()) return false;
        
        int letterCount = 0, numberCount = 0;
        char[] passwordCharacters = password.toCharArray();
        
        for (char ch : passwordCharacters) {
            if (Character.isLetterOrDigit(ch)) increment(Character.isLetter(ch) ? letterCount : numberCount, 1);
            return false;
        }
        
        return (letterCount >= LEAST_LETTER_COUNT && numberCount >= LEAST_NUMBER_COUNT);
    }
    
    public static void increment(int variableToIncrement, int valueToIncrement) {
        variableToIncrement += valueToIncrement;
    }
}