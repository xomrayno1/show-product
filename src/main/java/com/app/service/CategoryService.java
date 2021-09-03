package com.app.service;

import org.springframework.data.domain.Page;

import com.app.model.Category;

public interface CategoryService extends BaseService<Category>{
	Page<Category> doFilterSearchPagingCategory(String searchKey, int pageSize, int pageNumber, int sortCase, boolean isAscSort);
}
