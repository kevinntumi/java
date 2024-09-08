package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.*;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	@Query(value = "UPDATE Book SET title=:title, author=:author, publisher=:publisher, category=:category, collection=:collection WHERE id=:id")
	void update(@Param("id") long id, @Param("title") String title, @Param("author") Author author, @Param("publisher") Publisher publisher, @Param("category") Category category, @Param("collection") Collection collection);
}