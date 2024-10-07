/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

/**
 *
 * @author Kevin Ntumi
 */
public class Path {
    public static final String BASE_FOLDER_PATH = System.getProperty("user.dir");
    public static final String BASE_SRC_FOLDER_PATH = BASE_FOLDER_PATH + "\\src";
    public static final String BASE_JAVA_SRC_FOLDER_PATH = BASE_SRC_FOLDER_PATH + "\\main\\java\\";
    public static final String SQLITE_SCHEMA_FOLDER_PATH = BASE_JAVA_SRC_FOLDER_PATH + "\\edu\\uem\\sgh\\schema";
    public static final String REMOTE_DATABASE_NAME = "hotel";
    public static final String REMOTE_DATABASE_PORT = "3306";
    public static final String REMOTE_DATABASE_BASE_ENDPOINT = "localhost:" + REMOTE_DATABASE_PORT;
    public static final String REMOTE_DATABASE_INITIAL_URL = "jdbc:mysql://";
    public static final String REMOTE_DATABASE_URL = REMOTE_DATABASE_INITIAL_URL + REMOTE_DATABASE_BASE_ENDPOINT + "/" + REMOTE_DATABASE_NAME;
    public static final String REMOTE_DATABASE_USERNAME = "root";
    public static final String REMOTE_DATABASE_PASSWORD = "kevinntumi";
    public static final String LOCAL_DATABASE_NAME = "local.db";
    public static final String LOCAL_DATABASE_INITIAL_PATH = "jdbc:sqlite:";
    public static final String LOCAL_DATABASE_PATH =  LOCAL_DATABASE_INITIAL_PATH + System.getProperty("user.dir") + "\\" + LOCAL_DATABASE_NAME;
    
    public static String getRelativeFolderPath(String absoluteFolderPath) {
        return absoluteFolderPath.replace(BASE_JAVA_SRC_FOLDER_PATH, "").replace("\\", ".");
    }
}