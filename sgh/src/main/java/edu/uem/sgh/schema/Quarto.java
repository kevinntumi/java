/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

import java.sql.Blob;

/**
 *
 * @author Kevin Ntumi
 */
public class Quarto {
    private long id;
    private String tipo;
    private String descricao;
    private Blob foto;
    private double preco;
    private String estado;
    private int capacidade;
}