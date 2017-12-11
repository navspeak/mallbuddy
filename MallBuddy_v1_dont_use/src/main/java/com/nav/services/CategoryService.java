package com.nav.services;

import java.util.List;

import com.nav.csv.bootstrap.LoadDomains;
import com.nav.domain.Brand;
import com.nav.domain.Category;
import com.nav.domain.DomainObject;
import com.nav.exceptions.InvalidEntryInCSVFile;



public class CategoryService extends AbstractStringKeyMapService {
	    @Override
	    public List<DomainObject> listAll() {
	        return super.listAll();
	    }

	    @Override
	    public Category getByName(String name) {
	        return (Category) super.getByName(name);
	    }

	    @Override
	    public Category saveOrUpdate(DomainObject domainObject) {
	        return (Category) super.saveOrUpdate(domainObject);
	    }

	    @Override
	    public void delete(String name) {
	        super.delete(name);
	    }

		public void loadCategories(String categorySource) throws InvalidEntryInCSVFile {
			LoadDomains.initCategories(this, categorySource);
			
		}
}
