package com.nav.mall;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.BrandService;

public class BrandTest {
	
	private static BrandService brandService;
	
	@BeforeClass
	public static void setUpBrand() throws URISyntaxException, InvalidEntryInCSVFile {
		// Test Data:
		/*ARROW,20
		  VERO_MODA,60
		  XYZ,0*/
		Path brandCsvFile = Paths.get("src/test/resources/brands2.csv");
		brandService = new BrandService();
		brandService.loadBrands(brandCsvFile.toString());
	}
	
	@Test
	public void testAllBrandsAreLoaded() {
		assertEquals(3, brandService.listAll().stream().count());
	}
	
	@Test
	public void testDiscountsAreAppliedCorrectly() {
		assertEquals((Double)20.0, brandService.getByName("ARROW").getDiscount());
		assertEquals((Double)60.0, brandService.getByName("VERO_MODA").getDiscount());
		assertEquals((Double)0.0, brandService.getByName("XYZ").getDiscount());
	}

	@Test(expected = NullPointerException.class)
	public void testBrandListThatDoesntExist() {
		assertEquals((Double)20.0, brandService.getByName("I Dont exist").getDiscount());
	}
}
