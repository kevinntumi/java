package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@Service
public class AuthorService{
	@Autowired
	private AuthorRepository authorRepository;

	public <S extends Author> S save(S entity) {
		return authorRepository.save(entity);
	}
	
	public void update(Author author) {
		authorRepository.update(author.getId(), author.getName(), author.getGender());
	}

	public boolean existsById(Long id) {
		return authorRepository.existsById(id);
	}

	public Iterable<Author> findAll() {
		return authorRepository.findAll();
	}

	public void deleteById(Long id) {
		authorRepository.deleteById(id);
	}
	
	public int getNumRows() {
		return (int) authorRepository.count();
	}

	public void delete(Author entity) {
		authorRepository.delete(entity);
	}
	
	public Optional<Author> findById(Long id) {
		return authorRepository.findById(id);
	}
}