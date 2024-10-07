package edu.uem.sgh.model;

import java.util.Date;

/**
 * @author Kevin Ntumi
 */
public class Hospede extends Pessoa {

    private Date dataRegisto;
    private String nacionalidade;
    private String statusHospedagem;
    private int numQuarto;
    private int id;

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumQuarto() {
        return numQuarto;
    }

    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }

    public String getStatusHospedagem() {
        return statusHospedagem;
    }

    public void setStatusHospedagem(String statusHospedagem) {
        this.statusHospedagem = statusHospedagem;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}
