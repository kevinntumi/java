/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model.table;

import edu.uem.sgh.helper.ServicoSituacao;
import java.sql.Blob;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kevin Ntumi
 */
public class Quarto {
    private SimpleLongProperty codigo;
    private SimpleDoubleProperty preco;
    private SimpleIntegerProperty capacidade;
    private SimpleObjectProperty<ServicoSituacao> situacao;
    private SimpleStringProperty temFoto;
    private SimpleStringProperty descricao;
    private Blob foto;
    
    public Quarto(long codigo, double preco, int capacidade, String descricao, ServicoSituacao situacao, Blob foto) {
        this.codigo = new SimpleLongProperty(codigo);
        this.preco = new SimpleDoubleProperty(preco);
        this.capacidade = new SimpleIntegerProperty(capacidade);
        this.situacao = new SimpleObjectProperty<>(situacao);
        this.descricao = new SimpleStringProperty(descricao);
        this.foto = foto;
        this.temFoto = new SimpleStringProperty(foto != null ? "Tem" : "Não tem");
    }

    public Long getCodigo() {
        return codigo.get();
    }

    public Double getPreco() {
        return preco.get();
    }

    public Integer getCapacidade() {
        return capacidade.get();
    }

    public ServicoSituacao getSituacao() {
        return situacao.get();
    }

    public Blob getFoto() {
        return foto;
    }
    
    public String getTemFoto() {
        return temFoto.get();
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setCodigo(Long codigo) {
        this.codigo.set(codigo);
    }

    public void setPreco(double preco) {
        this.preco.set(preco);
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade.set(capacidade);
    }

    public void setSituacao(ServicoSituacao situacao) {
        this.situacao.set(situacao);
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
        this.temFoto.set(foto != null ? "Tem" : "Não tem");
    }    
}