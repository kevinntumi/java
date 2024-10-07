/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import edu.uem.sgh.annotation.Dependency;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class DependencyUtil {
    public static ArrayList<Object> getDependenciesByClass(Class<?> clazz, List<Object> availableDependencies) {
        ArrayList<Object> requiredDependencies = new ArrayList<>();
        List<Field> fields = ClassUtil.getFieldsByClass(clazz);
        
        if (fields.isEmpty()) return requiredDependencies;
        
        for (Field field : fields) {
            if (!FieldUtil.isPrivate(field)) continue;
            
            Dependency[] fieldDependencies = field.getAnnotationsByType(Dependency.class);
            
            if (fieldDependencies.length == 0) continue;
            
            Object dependency = getDependency(field.getType(), availableDependencies);
            
            if (dependency != null) requiredDependencies.add(dependency);
        }
        
        return requiredDependencies;
    }
    
    private static Object getDependency(Class<?> clazz, List<Object> availableDependencies) {
        Object dependency = null;
        System.out.println("tsk: " + clazz.getTypeName());
        if (!availableDependencies.isEmpty()) {
            for (Object object : availableDependencies) {
                System.out.println("tsoka " + object.getClass().getTypeName());
            }
        }
        
        if (dependency == null) {
            switch (clazz.getSimpleName()) {
                case "":
                    break;
            }
        }
        
        return dependency;
    }
 }