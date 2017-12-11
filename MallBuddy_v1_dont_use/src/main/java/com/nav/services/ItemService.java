package com.nav.services;

import java.util.List;
import com.nav.domain.DomainObject;
import com.nav.domain.Item;
import com.nav.repository.IItemService;

public class ItemService extends AbstractNumericKeyMapService implements IItemService{
	    @Override
	    public List<DomainObject> listAll() {
	        return super.listAll();
	    }

	    @Override
		public Item getById(Integer id) {
	        return (Item) super.getById(id);
	    }

	    @Override
	    public Item saveOrUpdate(Item domainObject) {
	        return (Item) super.saveOrUpdate(domainObject);
	    }

	    @Override
	    public void delete(Integer id) {
	        super.delete(id);
	    }
	
}
