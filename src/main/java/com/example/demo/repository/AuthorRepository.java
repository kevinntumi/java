package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{
	@Query(value = "UPDATE Author SET name=:name, gender=:gender WHERE id=:id")
	void update(@Param("id") long id, @Param("name") String name, @Param("gender") char gender);
}
