package com.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

	@Override
	List<Category> findAll();
}
