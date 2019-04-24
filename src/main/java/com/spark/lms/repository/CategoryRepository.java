package com.spark.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
