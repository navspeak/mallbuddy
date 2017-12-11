package com.nav.repository;

import java.util.List;

public interface IStringKeyCRUDService<T> {
	
	List<?> listAll();
	T getByName(String name);
	T saveOrUpdate(T domainObject);
	void delete(String name);

}
