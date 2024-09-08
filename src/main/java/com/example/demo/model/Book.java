package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5369979223248680852L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title;
	
	@Column(name = "launch_date", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
	private Date launchDate;
	
	@OneToOne
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;
	
	@OneToOne
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;
	
	@OneToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToOne
	@JoinColumn(name = "collection_id", nullable = false)
	private Collection collection;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getLaunchDate() {
		return launchDate;
	}
	
	public void setLaunchDate(long launchDateInMillis) {
		if (launchDate == null) 
			launchDate = new Date(launchDateInMillis);
		else 
			launchDate.setTime(launchDateInMillis);
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Collection getCollection() {
		return collection;
	}
	
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
}