package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public void update(Book book) {
		bookRepository.update(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getCategory(), book.getCollection());
	}

	public <S extends Book> S save(S entity) {
		return bookRepository.save(entity);
	}

	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}

	public boolean existsById(Long id) {
		return bookRepository.existsById(id);
	}

	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	public long getNumRows() {
		return bookRepository.count();
	}

	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}
}