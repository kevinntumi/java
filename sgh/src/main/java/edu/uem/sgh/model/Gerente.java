/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public class Gerente {
    private Long id;
    private String nome;
    private Character sexo;
    private Integer numTelefone;
    private String numBilheteIdentidade;
    private long dataNascimento;
    private long dataRegisto;

    public Gerente() {
    }

    public Gerente(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public long getDataNascimento() {
        return dataNascimento;
    }

    public long getDataRegisto() {
        return dataRegisto;
    }

    public Character getSexo() {
        return sexo;
    }

    public Integer getNumTelefone() {
        return numTelefone;
    }

    public String getNumBilheteIdentidade() {
        return numBilheteIdentidade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public void setNumTelefone(Integer numTelefone) {
        this.numTelefone = numTelefone;
    }

    public void setNumBilheteIdentidade(String numBilheteIdentidade) {
        this.numBilheteIdentidade = numBilheteIdentidade;
    }

    public void setDataNascimento(long dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataRegisto(long dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    @Override
    public String toString() {
        return "Gerente{" + "id=" + id + ", nome=" + nome + ", sexo=" + sexo + ", numTelefone=" + numTelefone + ", numBilheteIdentidade=" + numBilheteIdentidade + ", dataNascimento=" + dataNascimento + ", dataRegisto=" + dataRegisto + '}';
    }
}