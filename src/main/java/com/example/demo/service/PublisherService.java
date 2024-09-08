package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;

@Service
public class PublisherService {
	@Autowired 
	private PublisherRepository publisherRepository;

	public void update(Publisher publisher) {
		publisherRepository.update(publisher.getId(), publisher.getName(), publisher.getAddress());
	}

	public Iterable<Publisher> findAll() {
		return publisherRepository.findAll();
	}

	public <S extends Publisher> S save(S entity) {
		return publisherRepository.save(entity);
	}

	public Optional<Publisher> findById(Long id) {
		return publisherRepository.findById(id);
	}

	public boolean existsById(Long id) {
		return publisherRepository.existsById(id);
	}

	public long getNumRows() {
		return publisherRepository.count();
	}

	public void deleteById(Long id) {
		publisherRepository.deleteById(id);
	}
}