package com.nav.services;

import java.util.Arrays;
import java.util.List;

import com.nav.csv.bootstrap.LoadDomains;
import com.nav.domain.Brand;
import com.nav.domain.DomainObject;
import com.nav.exceptions.InvalidEntryInCSVFile;

public class BrandService extends AbstractStringKeyMapService {

		@Override
	    public List<DomainObject> listAll() {
	        return super.listAll();
	    }

	    @Override
	    public Brand getByName(String name) {
	        return (Brand) super.getByName(name);
	    }

	    @Override
	    public Brand saveOrUpdate(DomainObject domainObject) {
	        return (Brand) super.saveOrUpdate(domainObject);
	    }

	    @Override
	    public void delete(String name) {
	        super.delete(name);
	    }
	    
	    public void loadBrands(String brandCsv) throws InvalidEntryInCSVFile{
	    	LoadDomains.initBrands(this, brandCsv);
	    }
  }
