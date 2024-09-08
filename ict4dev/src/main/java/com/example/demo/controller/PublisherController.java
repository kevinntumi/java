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

import com.example.demo.model.Publisher;
import com.example.demo.request_body.PublisherRequestBody;
import com.example.demo.service.PublisherService;
import com.example.demo.utils.Util;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;

	@GetMapping("/get/{publisherId}")
	public ResponseEntity<Publisher> getPublisherById(@PathVariable String publisherId) {
		if (Util.isStringInvalid(publisherId)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(publisherId);
			if (!Util.isIdValid(id)) return ResponseEntity.badRequest().build();
			return ResponseEntity.of(publisherService.findById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@PatchMapping("/update/{publisherId}")
	public ResponseEntity<Boolean> update(@PathVariable String publisherId, @RequestBody Publisher publisher) {
		if (Util.isStringInvalid(publisherId) || !Util.isPublisherValid(publisher)) return ResponseEntity.badRequest().build();
		
		long id;
		
		try {
			id = Long.parseLong(publisherId);
			boolean isIdInvalid = !Util.isIdValid(id);
			if (isIdInvalid || !publisherService.existsById(id)) return (isIdInvalid ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());
			
			Publisher dbPublisherBeforeUpdate = publisherService.findById(id).orElse(null), dbPublisherAfterUpdate;
			
			if (!Util.hasPublisherChanged(dbPublisherBeforeUpdate, publisher)) return ResponseEntity.ok(false);
			
			publisherService.update(publisher);
			dbPublisherAfterUpdate = publisherService.findById(id).orElse(null);
			
			if (Util.hasPublisherChanged(dbPublisherBeforeUpdate, dbPublisherAfterUpdate)) return ResponseEntity.ok(true);
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Publisher>> getAll() {
		Iterable<Publisher> publishers;
		
		try {
			publishers = publisherService.findAll();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(publishers);
	}
	
	@PostMapping("/add/")
	public ResponseEntity<Long> save(@RequestBody PublisherRequestBody publisherRequestBody) {
		if (!Util.isPublisherRequestBodyValid(publisherRequestBody)) return ResponseEntity.badRequest().build();
		
		Publisher publisher = new Publisher();
		publisher.setAddress(publisherRequestBody.getAddress());
		publisher.setName(publisherRequestBody.getName());
		
		try {
			Publisher newPublisher = publisherService.save(publisher);
			return ResponseEntity.created(new URI("/publishers/get/" + newPublisher.getId())).build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/delete/{publisherId}")
	public ResponseEntity<Boolean> delete(@PathVariable String publisherId) {
		if (Util.isStringInvalid(publisherId)) return ResponseEntity.badRequest().build();
		
		try {
			long id = Long.parseLong(publisherId);
			boolean isIdInvalid = !Util.isIdValid(id);
			if (isIdInvalid || !publisherService.existsById(id)) return (isIdInvalid ? ResponseEntity.badRequest().build() : ResponseEntity.notFound().build());
			publisherService.deleteById(id);
			return ResponseEntity.ok(!publisherService.existsById(id));
		} catch (Exception e) {
			return (e instanceof NumberFormatException) ? ResponseEntity.badRequest().build() : ResponseEntity.internalServerError().build();
		}
	}
}