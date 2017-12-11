package com.nav.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nav.domain.DomainObject;
import com.nav.repository.ICRUDService;


public abstract class AbstractNumericKeyMapService implements ICRUDService<DomainObject>{
	private Map<Integer, DomainObject> domainMap;

    private Integer getNextKey(){
        if (domainMap.isEmpty()) 
        	return 1;
        else
        	return Collections.max(domainMap.keySet()) + 1;
    }
	
	public AbstractNumericKeyMapService() {
		domainMap = new HashMap<>();
	}

	public List<DomainObject> listAll() {
		return new ArrayList<>(domainMap.values());
	}

	public DomainObject getById(Integer id) {
		return domainMap.get(id);
	}

	public DomainObject saveOrUpdate(DomainObject domainObject) {
		if (domainObject != null){

            if (domainObject.getId() == null){
                domainObject.setId(getNextKey());
            }
            domainMap.put(domainObject.getId(), domainObject);

            return domainObject;
        } else {
            throw new RuntimeException("Object Can't be null");
        }
	}

	public void delete(Integer id) {
		domainMap.remove(id);
		
	}

}
