package com.example.demo.request_body;

import java.io.Serializable;

public class BookRequestBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956039891504739006L;
	
	private String title;
	private long authorId;
	private long publisherId;
	private long categoryId;
	private long collectionId;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	
	public long getPublisherId() {
		return publisherId;
	}
	
	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	public long getCollectionId() {
		return collectionId;
	}
	
	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}
}