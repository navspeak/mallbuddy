package com.nav.services;

import java.util.List;

public interface ICRUDService<T> {
	
	List<?> listAll();
	T getById(Integer id);
	T saveOrUpdate(T domainObject);
	void delete(Integer id);

}
