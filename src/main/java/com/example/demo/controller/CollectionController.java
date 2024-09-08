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

import com.example.demo.model.Collection;
import com.example.demo.service.CollectionService;
import com.example.demo.utils.Util;

@RestController
@RequestMapping("/collections")
public class CollectionController {
	@Autowired
	private CollectionService collectionService;
	
	@GetMapping("/get/{collectionId}")
	public ResponseEntity<Collection> get(@PathVariable String collectionId) {
		if (Util.isStringInvalid(collectionId)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(collectionId);
			if (id < 0 || id > Long.MAX_VALUE) return ResponseEntity.badRequest().build();
			return ResponseEntity.of(collectionService.findById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Collection>> getAll() {
		Iterable<Collection> categories;
		
		try {
			categories = collectionService.findAll();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(categories);
	}
	
	@PostMapping("/add/")
	public ResponseEntity<Long> save(@RequestBody String name) {
		if (Util.isStringInvalid(name)) return ResponseEntity.badRequest().build();

		Collection collection = new Collection();
		collection.setName(name);
		
		try {
			Collection newCollection = collectionService.save(collection);
			return ResponseEntity.created(new URI("/collections/get/" + newCollection.getId())).build();
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/delete/{collectionId}")
	public ResponseEntity<Boolean> delete(@PathVariable String collectionId) {
		if (Util.isStringInvalid(collectionId)) return ResponseEntity.badRequest().build();
		
		try {
			long id = Long.parseLong(collectionId);
			if (!Util.isIdValid(id) || !collectionService.existsById(id)) return (!Util.isIdValid(id) ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());
			collectionService.deleteById(id);
			return ResponseEntity.ok(!collectionService.existsById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@PatchMapping("/update/{collectionId}")
	public ResponseEntity<Boolean> update(@PathVariable String collectionId, @RequestBody String name) {
		if (Util.isStringInvalid(collectionId) || Util.isStringInvalid(name)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(collectionId);
			if (!Util.isIdValid(id) || !collectionService.existsById(id)) return (!Util.isIdValid(id) ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());

			String nameBefore = collectionService.findNameById(id), nameAfter;
			
			if (name.equals(nameBefore)) return ResponseEntity.ok(true);
			
			collectionService.update(id, name);
			nameAfter = collectionService.findNameById(id);
			
			return ResponseEntity.ok(nameAfter.equals(name));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
}