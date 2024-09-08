package com.example.demo.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Collection;
import com.example.demo.model.Publisher;
import com.example.demo.request_body.BookRequestBody;
import com.example.demo.service.BookService;
import com.example.demo.utils.Util;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;
	private RestTemplate restTemplate = new RestTemplate();
	
	private static final String BASE_ENDPOINT = "http://localhost:8080/";
	private static final String AUTHOR_ENDPOINT = BASE_ENDPOINT.concat("authors/get/");
	private static final String PUBLISHER_ENDPOINT = BASE_ENDPOINT.concat("publishers/get/");
	private static final String CATEGORY_ENDPOINT = BASE_ENDPOINT.concat("categories/get/");
	private static final String COLLECTION_ENDPOINT = BASE_ENDPOINT.concat("collections/get/");

	@GetMapping("/get/{bookId}")
	public ResponseEntity<Book> getPublisherById(@PathVariable String bookId) {
		if (Util.isStringInvalid(bookId)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(bookId);
			if (id < 0 || id > Long.MAX_VALUE) return ResponseEntity.badRequest().build();
			return ResponseEntity.of(bookService.findById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@PatchMapping("/update/{bookId}")
	public ResponseEntity<Boolean> update(@PathVariable String bookId, @RequestBody Book book) {
		if (Util.isStringInvalid(bookId) || !Util.isBookValid(book)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(bookId);
			if (id < 0 || id > Long.MAX_VALUE) return ResponseEntity.badRequest().build();
			if (!bookService.existsById(id)) return ResponseEntity.notFound().build();
			
			Book dbBookBeforeUpdate = bookService.findById(id).orElse(null), dbBookAfterUpdate;
			
			if (!Util.hasBookChanged(dbBookBeforeUpdate, book)) return ResponseEntity.ok(false);
			
			bookService.update(book);
			dbBookAfterUpdate = bookService.findById(id).orElse(null);
			
			if (Util.hasBookChanged(dbBookBeforeUpdate, dbBookAfterUpdate)) return ResponseEntity.ok(true);
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Book>> getAll() {
		Iterable<Book> books;
		
		try {
			books = bookService.findAll();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(books);
	}
	
	@PostMapping("/add/")
	public ResponseEntity<Long> save(@RequestBody BookRequestBody bookRequestBody) {
		if (!Util.isBookRequestBodyValid(bookRequestBody)) return ResponseEntity.badRequest().build();
		
		Book book = new Book();
		book.setAuthor(getAuthorById(bookRequestBody.getAuthorId()));
		book.setCategory(getCategoryById(bookRequestBody.getCategoryId()));
		book.setCollection(getCollectionById(bookRequestBody.getCollectionId()));
		book.setPublisher(getPublisherById(bookRequestBody.getPublisherId()));
		book.setTitle(bookRequestBody.getTitle());
			
		if (book.getAuthor() == null || book.getCategory() == null || book.getCollection() == null | book.getPublisher() == null || Util.isStringInvalid(book.getTitle())) return ResponseEntity.badRequest().build();
			
		try {
			Book newBook = bookService.save(book);
			return ResponseEntity.created(new URI("/books/get/" + newBook.getId())).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Boolean> delete(@PathVariable String bookId) {
		if (Util.isStringInvalid(bookId)) return ResponseEntity.badRequest().build();
		
		try {
			long id = Long.parseLong(bookId);
			if (id < 0 || id > Long.MAX_VALUE) return ResponseEntity.badRequest().build();
			if (!bookService.existsById(id)) return ResponseEntity.notFound().build();
			
			long numRowsBeforeOperation = bookService.getNumRows(), numRowsAfterOperation;
			bookService.deleteById(id);
			numRowsAfterOperation = bookService.getNumRows();		
			
			return ResponseEntity.ok(numRowsAfterOperation < numRowsBeforeOperation);
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	private Author getAuthorById(long id) {
		ResponseEntity<Author> response = restTemplate.getForEntity(AUTHOR_ENDPOINT + "" + id, Author.class);
		if (response.getStatusCode().is2xxSuccessful()) return response.getBody();
		return null;
	}
	
	private Publisher getPublisherById(long id) {
		ResponseEntity<Publisher> response = restTemplate.getForEntity(PUBLISHER_ENDPOINT + "" + id, Publisher.class);
		if (response.getStatusCode().is2xxSuccessful()) return response.getBody();
		return null;
	}
	
	private Collection getCollectionById(long id) {
		ResponseEntity<Collection> response = restTemplate.getForEntity(COLLECTION_ENDPOINT + "" + id, Collection.class);
		if (response.getStatusCode().is2xxSuccessful()) return response.getBody();
		return null;
	}
	
	private Category getCategoryById(long id) {
		ResponseEntity<Category> response = restTemplate.getForEntity(CATEGORY_ENDPOINT + "" + id, Category.class);
		if (response.getStatusCode().is2xxSuccessful()) return response.getBody();
		return null;
	}
}