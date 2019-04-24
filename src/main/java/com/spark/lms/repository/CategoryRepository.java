package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	public List<Category> findAllByOrderByNameAsc();
}
