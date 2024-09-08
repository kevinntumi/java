package com.example.demo.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Author;
import com.example.demo.request_body.AuthorRequestBody;
import com.example.demo.service.AuthorService;
import com.example.demo.utils.Util;

@RestController
@RequestMapping("/authors")
public class AuthorController{
	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/get/{authorId}")
	public ResponseEntity<?> get(@PathVariable String authorId) {
		if (Util.isStringInvalid(authorId)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(authorId);
			if (!Util.isIdValid(id)) return ResponseEntity.badRequest().build();
			return ResponseEntity.of(authorService.findById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@PatchMapping("/update/{authorId}")
	public ResponseEntity<Boolean> update(@PathVariable String authorId, @RequestBody Author author) {
		if (Util.isStringInvalid(authorId) || !Util.isAuthorValid(author)) return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		
		long id;
		
		try {
			id = Long.parseLong(authorId);
			boolean isIdInvalid = !Util.isIdValid(id);
			if (isIdInvalid || !authorService.existsById(id)) return (isIdInvalid ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());			
			
			Author dbAuthorBeforeUpdate = authorService.findById(id).orElse(null), dbAuthorAfterUpdate;
			
			if (!Util.hasAuthorChanged(dbAuthorBeforeUpdate, author)) return ResponseEntity.ok(false);
			
			authorService.update(author);
			dbAuthorAfterUpdate = authorService.findById(id).orElse(null);
			
			if (Util.hasAuthorChanged(dbAuthorBeforeUpdate, dbAuthorAfterUpdate)) return ResponseEntity.ok(true);
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Author>> getAll() {
		Iterable<Author> authors;
		
		try {
			authors = authorService.findAll();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(authors);
	}
	
	@PostMapping("/add/")
	public ResponseEntity<Long> save(@RequestBody AuthorRequestBody authorRequestBody) {
		if (!Util.isAuthorRequestBodyValid(authorRequestBody)) return ResponseEntity.badRequest().build();
		
		Author author = new Author();
		author.setGender(authorRequestBody.getGender());
		author.setName(authorRequestBody.getName());
		
		try {
			Author newAuthor = authorService.save(author);
			return ResponseEntity.created(new URI("/authors/get/" + newAuthor.getId())).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/delete/{authorId}")
	public ResponseEntity<Boolean> delete(@PathVariable String authorId) {
		if (Util.isStringInvalid(authorId)) return ResponseEntity.badRequest().build();
		
		try {
			long id = Long.parseLong(authorId);
			boolean isIdInvalid = !Util.isIdValid(id);
			if (isIdInvalid || !authorService.existsById(id)) return (isIdInvalid ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());
			authorService.deleteById(id);	
			return ResponseEntity.ok(!authorService.existsById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
}