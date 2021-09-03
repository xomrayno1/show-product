package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Category;
import com.app.repository.CategoryRepository;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private CategoryRepository cateRepo;
	
	@Autowired
	public CategoryController(CategoryRepository cateRepo) {
		this.cateRepo = cateRepo;
	}

	@GetMapping
	public List<Category> findAll(){
		List<Category> categories =  cateRepo.findAll();
		return categories;
	}
}
