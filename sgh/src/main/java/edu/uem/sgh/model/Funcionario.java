/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

import edu.uem.sgh.helper.FuncionarioSituacao;

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
    private Gerente gerente;
    private FuncionarioSituacao funcionarioSituacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public FuncionarioSituacao getFuncionarioSituacao() {
        return funcionarioSituacao;
    }

    public void setFuncionarioSituacao(FuncionarioSituacao funcionarioSituacao) {
        this.funcionarioSituacao = funcionarioSituacao;
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

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", dataRegisto=" + dataRegisto + ", morada=" + morada + ", numBilheteIdentidade=" + numBilheteIdentidade + ", numTelefone=" + numTelefone + ", email=" + email + ", gerente=" + gerente + ", funcionarioSituacao=" + funcionarioSituacao + '}';
    }
}