package com.spark.lms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.model.Category;
import com.spark.lms.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Long getTotalCount() {
		return categoryRepository.count();
	}
	
	public List<Category> getAllBySort() {
		return categoryRepository.findAllByOrderByNameAsc();
	}
	
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	
	public Category get(Long id) {
		return categoryRepository.findById(id).get();
	}
	
	public Category addNew(Category category) {
		category.setCreateDate(new Date());
		return categoryRepository.save(category);
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public void delete(Category category) {
		categoryRepository.delete(category);
	}
	
	public void delete(Long id) {
		categoryRepository.deleteById(id);
	}
	
	public boolean hasUsage(Category category) {
		return category.getBooks().size()>0;
	}
}
