package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Collection;
import com.example.demo.repository.CollectionRepository;

@Service
public class CollectionService {
	@Autowired
	private CollectionRepository collectionRepository;

	public void update(long id, String name) {
		collectionRepository.update(id, name);
	}

	public <S extends Collection> S save(S entity) {
		return collectionRepository.save(entity);
	}

	public Optional<Collection> findById(Long id) {
		return collectionRepository.findById(id);
	}

	public boolean existsById(Long id) {
		return collectionRepository.existsById(id);
	}

	public Iterable<Collection> findAll() {
		return collectionRepository.findAll();
	}

	public String findNameById(long id) {
		return collectionRepository.findNameById(id);
	}

	public long getNumRows() {
		return collectionRepository.count();
	}

	public void deleteById(Long id) {
		collectionRepository.deleteById(id);
	}
}