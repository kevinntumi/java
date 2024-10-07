package edu.uem.sgh.model;

import java.util.Date;

/**
 *
 * @author Kevin Ntumi
 */
public class Funcionario extends Pessoa {

    private int id;
    private Date dataRegisto;
    private String cargo;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

}
