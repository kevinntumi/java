/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import edu.uem.sgh.annotation.Dependency;
import edu.uem.sgh.connection.ConnectionType;
import edu.uem.sgh.connection.Type;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.SQLException;
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
            if (!FieldUtil.isPrivate(field) || FieldUtil.isPrimitive(field)) continue;
            
            Dependency[] fieldDependencies = field.getAnnotationsByType(Dependency.class);
            
            if (fieldDependencies.length == 0) continue;
            
            Object dependency = getDependency(field.getType(), availableDependencies);
            
            if (dependency != null) requiredDependencies.add(dependency);
        }
        
        return requiredDependencies;
    }
    
    public static Type getUnnavailableConnection(List<Object> availableDependencies) {
        for (Object object : availableDependencies) {
            if (!(object instanceof Connection)) continue;
            
            Connection connection = (Connection) object;
            Type type;
            
            try {
                type = (connection.getMetaData().getURL().startsWith(Path.REMOTE_DATABASE_INITIAL_URL)) ? Type.LOCAL : Type.REMOTE;
            } catch (SQLException e) {
                type = null;
            }
            
            return type;
        }
        
        return null;
    }
    
    private static Object getDependency(Class<?> clazz, List<Object> availableDependencies) {
        String typeName = clazz.getTypeName();
        
        if (!availableDependencies.isEmpty()) {
            for (Object object : availableDependencies) {
                if (typeName.equals(object.getClass().getTypeName())) return object;
            }
        }
        
        Class<?> clzz = ClassUtil.getClassByTypeName(typeName);
        
        if (clzz == null) return null;
        
        Constructor[] constructors = clzz.getConstructors();
        
        if (constructors.length == 0) return null;
        
        for (Constructor constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            
            if (!(temAnotacao(constructor) && temParametros(parameters))) continue;
            
            for (Parameter parameter : parameters) {
                Class<?> typeClass = parameter.getType();
                
                if (typeClass.equals(java.sql.Connection.class)) {
                    
                    ConnectionType connectionType = parameter.getAnnotation(ConnectionType.class);
                    
                    if (connectionType == null) return null;
                    
                    String prefix;
                    
                    switch (connectionType.type()) {
                        case LOCAL: prefix = Path.LOCAL_DATABASE_INITIAL_PATH;
                            break;
                        case REMOTE: prefix = Path.REMOTE_DATABASE_INITIAL_URL;
                            break;
                        default: 
                            return null;
                    }
                    
                    for (Object object : availableDependencies) {
                        if (!(object instanceof Connection)) continue;
                        
                        Connection connection = (Connection) object;
                        String url;
                        
                        try {
                            url = connection.getMetaData().getURL();
                        } catch (SQLException e) {
                            continue;
                        }
                        
                        if (url.startsWith(prefix)) return connection;
                    }
                    
                }
            }
        }
        
        return null;
    }
    
    private static boolean temParametros(Constructor constructor) {
        return constructor.getParameterCount() > 0;
    }
    
    private static boolean temParametros(Parameter[] parameters) {
        return parameters.length > 0;
    }
    
    private static boolean temAnotacao(Constructor constructor) {
        return constructor.isAnnotationPresent(edu.uem.sgh.annotation.Constructor.class);
    }
 }