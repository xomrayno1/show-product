package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.model.Product;
import com.app.repository.ProductRepository;
import com.app.response.specification.ProductSpecification;
import com.app.service.ProductService;
import com.app.utils.Constant;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository proRepo;
	
	@Autowired
	public ProductServiceImpl(ProductRepository proRepo) {
		this.proRepo = proRepo;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return proRepo.findAll();
	}

	@Override
	public Product update(Product instance) {
		// TODO Auto-generated method stub
		return proRepo.save(instance);
	}

	@Override
	public void delete(Product instance) {
		// TODO Auto-generated method stub
		proRepo.delete(instance);
	}

	@Override
	public Product findById(long id) {
		// TODO Auto-generated method stub
		return proRepo.findById(id).orElse(null);
	}

	@Override
	public Product insert(Product instance) {
		// TODO Auto-generated method stub
		instance.setActiveFlag(Constant.Status.ACTIVE.getValue());
		return proRepo.save(instance);
	}

	@Override
	public Product findByName(String name) {
		// TODO Auto-generated method stub
		return proRepo.findByName(name).orElse(null);
	}

	@Override
	public Page<Product> doFilterSearchPagingProduct(String searchKey, long categoryId, int pageSize, int pageNumber, int sortCase,
			boolean isAscSort) {
		// TODO Auto-generated method stub
		return proRepo.findAll(new ProductSpecification(searchKey, categoryId, sortCase, isAscSort), PageRequest.of(pageNumber - 1, pageSize));
	}

}
