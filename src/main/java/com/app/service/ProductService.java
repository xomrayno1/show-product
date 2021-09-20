package com.app.service;

import org.springframework.data.domain.Page;

import com.app.model.Product;

public interface ProductService extends BaseService<Product>{
	Page<Product> doFilterSearchPagingProduct(String searchKey, long categoryId, int pageSize, int pageNumber, int sortCase, boolean isAscSort);
}
