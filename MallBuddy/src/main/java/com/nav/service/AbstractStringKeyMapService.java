package com.nav.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nav.domain.DomainObject;


public abstract class AbstractStringKeyMapService {
	
	private Map<String, DomainObject> domainMap;

   	public AbstractStringKeyMapService() {
		domainMap = new HashMap<>();
	}

	public List<DomainObject> listAll() {
		return new ArrayList<>(domainMap.values());
	}

	public DomainObject getByName(String name) {
		return domainMap.get(name);
	}

	public DomainObject saveOrUpdate(DomainObject domainObject) {
		if (domainObject != null){
            domainMap.put(domainObject.getName(), domainObject);
            return domainObject;
        } else {
            throw new RuntimeException("Object Can't be null");
        }
	}

	public void delete(String name) {
		domainMap.remove(name);
	}

}
