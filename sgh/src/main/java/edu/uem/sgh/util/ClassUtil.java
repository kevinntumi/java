/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class ClassUtil {
    public static boolean isAbstractClass(Class<?> clazz) {
        if (clazz == null) throw new NullPointerException();
        return Modifier.isAbstract(clazz.getModifiers());
    }
    
    public static List<Class<?>> getSubClassesBySuperClass(Class<?> superClass) {
        List<Class<?>> subClasses = new ArrayList<>();
        
        for (Class<?> clazz : superClass.getNestMembers()) {
            if (clazz.getSuperclass().equals(superClass)) subClasses.add(clazz);
        }
        
        return subClasses;
    }
    
    public static List<Field> getFieldsByClass(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        
        if (declaredFields.length != 0) {
            for (Field declaredField : declaredFields) {
                if (Modifier.isPrivate(declaredField.getModifiers())) fields.add(declaredField);
            }
        }
        
        Class<?> superClass = clazz.getSuperclass();
        
        if (!superClass.equals(Object.class)) {
            List<Field> declaredSuperClassFields = getFieldsByClass(superClass);
            
            for (Field declaredField : declaredSuperClassFields) {
                if(Modifier.isPrivate(declaredField.getModifiers())) fields.add(declaredField);
            }
        }
        
        return fields;
    }
    
    public static List<Class<?>> getJavaClassesInFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isFile() || !folder.exists()) return null;
        
        String suffix = ".java";
        String[] folderList = folder.list();        
        List<Class<?>> classes = new ArrayList<>();
       
        for (String path : folderList) {
            if (!path.endsWith(suffix)) continue;

            Class<?> clazz;

            try {
                clazz = Class.forName(Path.getRelativeFolderPath(folder.getAbsolutePath()) + "." + path.replace(suffix, ""));
            } catch (ClassNotFoundException ex) {
                clazz = null;
            }
            
            if (clazz != null) classes.add(clazz);
        }
        
        return classes;
    }
}