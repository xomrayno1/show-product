package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.model.Category;
import com.app.repository.CategoryRepository;
import com.app.response.specification.CategorySpecification;
import com.app.service.CategoryService;
import com.app.utils.Constant;

@Service
public class CategoryServiceImpl implements CategoryService{
	 
	private CategoryRepository cateRepo;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository cateRepo) {
		this.cateRepo = cateRepo;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return cateRepo.findAll();
	}

	@Override
	public Category update(Category instance) {
		// TODO Auto-generated method stub
		return cateRepo.save(instance);
	}

	@Override
	public void delete(Category instance) {
		// TODO Auto-generated method stub
		cateRepo.delete(instance);
	}

	@Override
	public Category findById(long id) {
		// TODO Auto-generated method stub
		return cateRepo.findById(id).orElse(null);
	}

	@Override
	public Category insert(Category instance) {
		// TODO Auto-generated method stub
		instance.setActiveFlag(Constant.Status.ACTIVE.getValue());
		return cateRepo.save(instance);
	}

	@Override
	public Category findByName(String name) {
		// TODO Auto-generated method stub
		return cateRepo.findByName(name).orElse(null);
	}

	@Override
	public Page<Category> doFilterSearchPagingCategory(String searchKey, int pageSize, int pageNumber, int sortCase,
			boolean isAscSort) {
		// TODO Auto-generated method stub
		return cateRepo.findAll(new CategorySpecification(searchKey, sortCase, isAscSort),
				PageRequest.of(pageNumber - 1, pageSize));
	}

}
