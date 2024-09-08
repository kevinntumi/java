/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_dois;

/**
 *
 * @author Kevin Ntumi
 */
public abstract class Funcionario {
    private int id;
    private String nome;
    private double salario;
    private int idade;
    private char sexo;
    private boolean estaDemitido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isEstaDemitido() {
        return estaDemitido;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nome=" + nome + ", idade=" + idade + ", sexo=" + sexo + ", estaDemitido=" + estaDemitido;
    }

    public void setEstaDemitido(boolean estaDemitido) {
        this.estaDemitido = estaDemitido;
    }

    public abstract void exibirDados();
    public abstract double calcularSalario();
}