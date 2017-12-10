package com.nav.mall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.CategoryService;

public class CategoryTest {

	private static CategoryService catergorySvc;

	@BeforeClass
	public static void setUpCategory() throws URISyntaxException, InvalidEntryInCSVFile {
		// Test Data: category2.csv
		/*APPAREL,,MENSWEAR:WOMENSWEAR,0
		MENSWEAR,APPAREL,SHIRTS:CASUALS,10
		SHIRTS,MENSWEAR,,90
		CASUALS,MENSWEAR,,7
		WOMENSWEAR,APPAREL,,0*/
		Path categoryCsvFile = Paths.get("src/test/resources/category2.csv");
		catergorySvc = new CategoryService();
		catergorySvc.loadCategories(categoryCsvFile.toString());
	}
	
	@Test
	public void testAllCategoriesAreLoaded(){
		assertEquals(5,catergorySvc.listAll().size());
	}
	
	@Test
	public void testParentSetUp(){
		assertEquals("APPAREL",catergorySvc.getByName("MENSWEAR").getParent().getName());
		assertEquals("MENSWEAR",catergorySvc.getByName("SHIRTS").getParent().getName());
		assertEquals("MENSWEAR",catergorySvc.getByName("CASUALS").getParent().getName());
	}
	
	@Test
	public void testChildrenSetUp(){
		assertEquals(2,catergorySvc.getByName("MENSWEAR").getChildren().size());
		assertEquals("SHIRTS",catergorySvc.getByName("MENSWEAR").getChildren().get(0).getName());
		assertEquals("CASUALS",catergorySvc.getByName("MENSWEAR").getChildren().get(1).getName());
		assertEquals(0,catergorySvc.getByName("WOMENSWEAR").getChildren().size());
	}
	
	@Test
	public void tesDiscountIsAppliedWell(){
		assertEquals((Double)10.0,catergorySvc.getByName("MENSWEAR").getDiscount());
		assertEquals((Double)90.0,catergorySvc.getByName("SHIRTS").getDiscount());
		assertEquals((Double)7.0,catergorySvc.getByName("CASUALS").getDiscount());
	}
}
