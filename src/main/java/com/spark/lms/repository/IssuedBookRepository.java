package com.spark.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Book;
import com.spark.lms.model.IssuedBook;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
	public Long countByBookAndReturned(Book book, Integer returned);
}
