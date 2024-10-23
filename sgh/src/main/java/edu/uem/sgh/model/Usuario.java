/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import java.sql.Blob;

/**
 *
 * @author Kevin Ntumi
 */
public class Usuario {
    private Long id;
    private Long idTipo;
    private String nome;
    private Tipo tipo;
    private Long dataInicio;
    private Long dataRegisto;
    private String palavraPasse;
    private Boolean manterSessaoIniciada;
    
    public final static Usuario ANONIMO = new Usuario();
    
    public Usuario() {
    }

    public Usuario(Long dataInicio, Long id) {
        this.dataInicio = dataInicio;
        this.id = id;
    }
   
    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public long getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
    
    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPalavraPasse() {
        return palavraPasse;
    }

    public void setPalavraPasse(String palavraPasse) {
        this.palavraPasse = palavraPasse;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Boolean getManterSessaoIniciada() {
        return manterSessaoIniciada;
    }

    public void setManterSessaoIniciada(Boolean manterSessaoIniciada) {
        this.manterSessaoIniciada = manterSessaoIniciada;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", idTipo=" + idTipo + ", nome=" + nome + ", tipo=" + tipo + ", dataInicio=" + dataInicio + ", dataRegisto=" + dataRegisto + ", palavraPasse=" + palavraPasse + '}';
    }

    public enum Tipo {
        CLIENTE,
        FUNCIONARIO,
        GERENTE,
        ADMINISTRADOR;
        
        public static String converterTipoParaString(Tipo tipo) {
            if (tipo == null)
                return null;
            
            return tipo.toString();
        }
        
        public static Tipo obterTipoViaString (String tipo) {
            if (tipo == null || tipo.isBlank()) return null;

            if (tipo.equals(Tipo.CLIENTE.toString())) {
                return Tipo.CLIENTE;
            } else if (tipo.equals(Tipo.FUNCIONARIO.toString())) {
                return Tipo.FUNCIONARIO;
            } else if (tipo.equals(Tipo.GERENTE.toString())) {
                return Tipo.GERENTE;
            } else if (tipo.equals(Tipo.ADMINISTRADOR.toString())) {
                return Tipo.ADMINISTRADOR;
            }

            return null;
        }
    }
}