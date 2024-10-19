/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.schema;

/**
 *
 * @author Kevin Ntumi
 */
public class Funcionario {
    private Long id;
    private String nome;
    private Long dataNascimento;
    private Long dataRegisto;
    private String morada;
    private String numBilheteIdentidade;
    private Integer numTelefone;
    private String email;
    private Long idGerente;

    public Funcionario() {
    }
    
    public Funcionario(Long id, String nome, Long dataNascimento, Long dataRegisto, String morada, String numBilheteIdentidade, Integer numTelefone, String email, Long idGerente) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataRegisto = dataRegisto;
        this.morada = morada;
        this.numBilheteIdentidade = numBilheteIdentidade;
        this.numTelefone = numTelefone;
        this.email = email;
        this.idGerente = idGerente;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Long dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNumBilheteIdentidade() {
        return numBilheteIdentidade;
    }

    public void setNumBilheteIdentidade(String numBilheteIdentidade) {
        this.numBilheteIdentidade = numBilheteIdentidade;
    }

    public Integer getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(Integer numTelefone) {
        this.numTelefone = numTelefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", dataRegisto=" + dataRegisto + ", morada=" + morada + ", numBilheteIdentidade=" + numBilheteIdentidade + ", numTelefone=" + numTelefone + ", email=" + email + ", idGerente=" + idGerente + '}';
    }
}