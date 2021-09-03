package com.app.service;

import java.util.List;

public interface BaseService<T> {
	List<T> findAll();
	
	T update(T instance);

	void delete(T instance);
	
	T findById(long id);
	
	T insert(T instance);
	
	T findByName(String name);
}
