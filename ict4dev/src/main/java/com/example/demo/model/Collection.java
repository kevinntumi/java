package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
public class Collection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576523996497816991L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	
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
}