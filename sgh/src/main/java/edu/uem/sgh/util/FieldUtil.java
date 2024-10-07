/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 *
 * @author Kevin Ntumi
 */
public class FieldUtil {
    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }
    
    public static boolean isPrimitive(Field field) {
        return field.getType().isPrimitive();
    }
}
