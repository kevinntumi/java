/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.model;

/**
 *
 * @author Kevin Ntumi
 */
public class CheckOut {
    private Long id;
    private CheckIn checkIn;
    private Long dataCheckOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public Long getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(Long dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    @Override
    public String toString() {
        return "CheckOut{" + "id=" + id + ", checkIn=" + checkIn + ", dataCheckOut=" + dataCheckOut + '}';
    }
}