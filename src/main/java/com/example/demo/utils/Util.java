package com.example.demo.utils;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Collection;
import com.example.demo.model.Publisher;
import com.example.demo.request_body.AuthorRequestBody;
import com.example.demo.request_body.BookRequestBody;
import com.example.demo.request_body.PublisherRequestBody;

public class Util {
	private static final long LEAST_AUTHOR_DATE_OF_BIRTH = Long.parseLong("2584834606");
	private static final long MAX_AUTHOR_BIRTH_DATE = System.currentTimeMillis();
	private static final int LEAST_MOZAMBIQUE_PHONE_NUMBER = 820000000;
	private static final int MAX_MOZAMBIQUE_PHONE_NUMBER = 879999999;
	
	public static boolean isIdValid(long id) {
		if (id < 0 || id > Long.MAX_VALUE) return false;
		return true;
	}
	
	public static boolean isAuthorValid(Author author) {
		if (author == null || isStringInvalid(author.getName()) || (author.getGender() != 'M' && author.getGender() != 'F')) return false;
		return true;
	}
	
	public static boolean isGenderValid(char gender) {
		if (gender == 'M' || gender == 'F') return true;
		return false;
	}
	
	public static boolean isCategoryValid(Category category) {
		if (category == null || isStringInvalid(category.getName())) return false;
		return true;
	}
	
	public static boolean isCollectionValid(Collection collection) {
		if (collection == null || isStringInvalid(collection.getName())) return false;
		return true;
	}
	
	public static boolean isPublisherValid(Publisher publisher) {
		if (publisher == null || isStringInvalid(publisher.getAddress()) || isStringInvalid(publisher.getName())) return false;
		return true;
	}
	
	public static boolean isBookRequestBodyValid(BookRequestBody bookRequestBody) {
		if (!isIdValid(bookRequestBody.getAuthorId()) || !isIdValid(bookRequestBody.getCategoryId()) || !isIdValid(bookRequestBody.getCollectionId()) || !isIdValid(bookRequestBody.getPublisherId()) || isStringInvalid(bookRequestBody.getTitle())) return false;
		return true;
	}
	
	public static boolean isAuthorRequestBodyValid(AuthorRequestBody authorRequestBody) {
		if (isStringInvalid(authorRequestBody.getName()) || !isGenderValid(authorRequestBody.getGender())) return false;
		return true;
	}
	
	public static boolean isPublisherRequestBodyValid(PublisherRequestBody publisherRequestBody) {
		if (isStringInvalid(publisherRequestBody.getAddress()) || isStringInvalid(publisherRequestBody.getName())) return false;
		return true;
	}
	
	public static boolean isStringInvalid(String any) {
		if (any == null || any.isBlank()) return true;
		return false;
	}
	
	static boolean isDateInvalid(Long any) {
		if (any == null || (any < LEAST_AUTHOR_DATE_OF_BIRTH || any > MAX_AUTHOR_BIRTH_DATE)) return true;
		return false;
	}
	
	static boolean isPhoneNumberInvalid(Integer any) {
		if (any == null || any < LEAST_MOZAMBIQUE_PHONE_NUMBER || any > MAX_MOZAMBIQUE_PHONE_NUMBER) return true;
		return false;
	}
	
	public static boolean isBookValid(Book book) {
		if (book == null || isStringInvalid(book.getTitle()) || isDateInvalid(book.getLaunchDate().getTime()) || isPublisherValid(book.getPublisher()) || isAuthorValid(book.getAuthor()) || isCategoryValid(book.getCategory()) || isCollectionValid(book.getCollection())) return false;
		return true;
	}
	
	public static boolean hasPublisherChanged(Publisher old, Publisher nw) {
		if (old == null && nw != null) return true;
		if (!isPublisherValid(nw) || (((old.getAddress().equals(nw.getAddress())) || (old.getName().equals(nw.getName()))))) return false; 
		return true;
	}
	
	public static boolean hasAuthorChanged(Author old, Author nw) {
		if (old == null && nw != null) return true;
		if (!isAuthorValid(nw) || ((old.getName().equals(nw.getName())) || (old.getGender() == nw.getGender()))) return false;
		return true;
	}
	
	public static boolean hasCategoryChanged(Category oldCat, Category newCat) {
		if (oldCat.getName().equals(newCat.getName())) return false;
		return true;
	}
	
	public static boolean hasCollectionChanged(Collection oldCol, Collection newCol) {
		if (oldCol.getName().equals(newCol.getName())) return false;
		return true;
	}
	
	public static boolean hasBookChanged(Book old, Book nw) {
		if (old == null && nw != null) return true;
		if (!isBookValid(nw) || (old.getTitle().equals(nw.getTitle()) || !hasPublisherChanged(old.getPublisher(), nw.getPublisher()) || !hasAuthorChanged(old.getAuthor(), nw.getAuthor()) || !hasCategoryChanged(old.getCategory(), nw.getCategory()) || !hasCollectionChanged(old.getCollection(), nw.getCollection()))) return false;
		return true;
	}
}