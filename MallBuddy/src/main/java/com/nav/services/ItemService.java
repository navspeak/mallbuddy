package com.nav.services;

import java.util.List;
import com.nav.domain.DomainObject;
import com.nav.domain.Item;

public class ItemService extends AbstractNumericKeyMapService {
	    @Override
	    public List<DomainObject> listAll() {
	        return super.listAll();
	    }

	    @Override
		public Item getById(Integer id) {
	        return (Item) super.getById(id);
	    }

	    @Override
	    public Item saveOrUpdate(DomainObject domainObject) {
	        return (Item) super.saveOrUpdate(domainObject);
	    }

	    @Override
	    public void delete(Integer id) {
	        super.delete(id);
	    }
	
}
