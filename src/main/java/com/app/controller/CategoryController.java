package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Category;
import com.app.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public List<Category> findAll(){
		List<Category> categories =  categoryService.findAll();
		return categories;
	}
	
	@PostMapping
	public Category findAll(@RequestBody Category cateRequest){
		if(cateRequest.getName() != null) {
			return null;
		}
		Category category =  categoryService.insert(cateRequest);
		return category;
	}
}
