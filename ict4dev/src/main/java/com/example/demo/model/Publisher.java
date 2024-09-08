package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class Publisher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7690152511577240613L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String address;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}