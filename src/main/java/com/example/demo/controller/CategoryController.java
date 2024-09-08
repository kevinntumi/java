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

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.utils.Util;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable String categoryId) {
		if (Util.isStringInvalid(categoryId)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(categoryId);
			if (!Util.isIdValid(id)) return ResponseEntity.badRequest().build();
			return ResponseEntity.of(categoryService.findById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Category>> getAll() {
		Iterable<Category> categories;
		
		try {
			categories = categoryService.findAll();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(categories);
	}
	
	@PostMapping("/add/")
	public ResponseEntity<Long> save(@RequestBody String name) {
		if (Util.isStringInvalid(name)) return ResponseEntity.badRequest().build();
		
		Category category = new Category();
		category.setName(name);
		
		try {
			Category newCategory = categoryService.save(category);
			return ResponseEntity.created(new URI("/categories/get/" + newCategory.getId())).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<Boolean> delete(@PathVariable String categoryId) {
		if (Util.isStringInvalid(categoryId)) return ResponseEntity.badRequest().build();
		
		try {
			long id = Long.parseLong(categoryId);
			if (!Util.isIdValid(id)) return ResponseEntity.badRequest().build();
			if (!categoryService.existsById(id)) return ResponseEntity.notFound().build();
			categoryService.deleteById(id);	
			return ResponseEntity.ok(!categoryService.existsById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@PatchMapping("/update/{categoryId}")
	public ResponseEntity<Boolean> update(@PathVariable String categoryId, @RequestBody String name) {
		if (Util.isStringInvalid(categoryId) || Util.isStringInvalid(name)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(categoryId);
			if (!Util.isIdValid(id)) return ResponseEntity.badRequest().build();
			if (!categoryService.existsById(id)) return ResponseEntity.notFound().build();
			
			String nameBefore = categoryService.findNameById(id), nameAfter;
			
			if (name.equals(nameBefore)) return ResponseEntity.ok(true);
			
			categoryService.update(id, name);
			nameAfter = categoryService.findNameById(id);
			
			return ResponseEntity.ok(nameAfter.equals(name));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
}