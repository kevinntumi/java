package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Publisher;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
	@Query(value = "UPDATE Publisher SET name=:name, address=:address WHERE id=:id")
	void update(@Param("id") long id, @Param("name") String name, @Param("address") String address);
}