package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}
	
	public void update(long id, String name) {
		categoryRepository.update(id, name);
	}

	public String findNameById(long id) {
		return categoryRepository.findNameById(id);
	}

	public <S extends Category> S save(S entity) {
		return categoryRepository.save(entity);
	}

	public boolean existsById(Long id) {
		return categoryRepository.existsById(id);
	}

	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}

	public long getNumRows() {
		return categoryRepository.count();
	}

	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}
}