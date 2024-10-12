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
    private Blob fotoPerfil;
    private String palavraPasse;
    private Long dataAlterado;
    
    public static Usuario VAZIO = new Usuario();
    
    public Usuario() {
    }

    public Usuario(Long dataInicio, Long id) {
        this.dataInicio = dataInicio;
        this.id = id;
    }

    public Blob getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Blob fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
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

    public long getDataAlterado() {
        return dataAlterado;
    }

    public void setDataAlterado(long dataAlterado) {
        this.dataAlterado = dataAlterado;
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
    
    public static boolean isVazio(Usuario usuario) {
        if (usuario == null) return true;
        return usuario.dataAlterado == null && usuario.dataInicio == null && usuario.dataRegisto == null && usuario.id == null && usuario.idTipo == null && usuario.nome == null && usuario.tipo == null;
    }
    
    public static boolean temTodosAtributos(Usuario usuario) {
        if (usuario == null) throw new RuntimeException();
        return (usuario.id != null && usuario.idTipo != null && usuario.nome != null && usuario.palavraPasse != null && usuario.tipo != null && usuario.fotoPerfil != null && usuario.dataAlterado != null && usuario.dataRegisto != null);
    } 

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", idTipo=" + idTipo + ", nome=" + nome + ", tipo=" + tipo + ", dataInicio=" + dataInicio + ", dataRegisto=" + dataRegisto + ", fotoPerfil=" + fotoPerfil + ", palavraPasse=" + palavraPasse + ", dataAlterado=" + dataAlterado + '}';
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