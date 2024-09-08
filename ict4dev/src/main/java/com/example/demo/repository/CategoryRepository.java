package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	@Query(value = "UPDATE Category c SET c.name=:name WHERE c.id=:id")
	void update(@Param("id") long id, @Param("name") String name);
	
	@Query(value = "SELECT name FROM Category c WHERE c.id=:id")
	String findNameById(@Param("id") long id);
}