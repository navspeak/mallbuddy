package com.nav.mall;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nav.csv.util.BillSelectionUtil;
import com.nav.csv.util.CSVUtil;
import com.nav.domain.Category;
import com.nav.domain.DomainObject;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.BillingService;
import com.nav.service.InventoryService;
import com.nav.strategy.BillStyle;
public class InventoryTest2 {


	private static InventoryService inventorySvc = null;
	private static String csvInventory = null;
	private static String customerInputFile = null;
	private static String csvbrand = null;
	private static String csvCategory = null;

	@BeforeClass
	public static void inputPathSetup() throws URISyntaxException, InvalidEntryInCSVFile {
		// Test Data: inventory2.csv
		/*5
		1, Arrow,Shirts,800
		2, Vero Moda,Dresses,1400
		3, Provogue,Footwear,1800
		4, Wrangler,Jeans,2200
		5, UCB,Shirts,1500*/
		
		// Test Data: customerInput2.csv
		/*2
		1,2,3,4
		1,5*/
		Path csvFile = Paths.get("src/test/resources/inventory2.csv");
		csvInventory = csvFile.toAbsolutePath().toString();
		Path custcsvFile = Paths.get("src/test/resources/customerInput2.csv");
		customerInputFile = custcsvFile.toAbsolutePath().toString();
		/*Path brandcsvFile = Paths.get("src/test/resources/brands1.csv");
		csvbrand = brandcsvFile.toAbsolutePath().toString();
		Path categorycsvFile = Paths.get("src/test/resources/category1.csv");
		csvCategory = categorycsvFile.toAbsolutePath().toString();*/
		inventorySvc = new InventoryService();
		inventorySvc.loadInventory(csvInventory,csvbrand,csvCategory);
		System.out.println("============BRANDS=================");
		inventorySvc.getBrandService().listAll().forEach(System.out::println);
		System.out.println("===================================");
		System.out.println("============CATEGORY=================");
		/*List<DomainObject> list = inventorySvc.getCategoryService().listAll();
		for (DomainObject obj : list){
			System.out.println("-----------------------");
			Category cat = (Category)obj;
			System.out.println(cat.getId());
			System.out.println(cat.getName());
			System.out.println(cat.getParent() == null ? "NULL" : cat.getParent().getName());
			System.out.println(Arrays.toString(cat.getChildren().toArray()));
			System.out.println(cat.getDiscount());
			System.out.println("-----------------------");
		}*/
		inventorySvc.getCategoryService().listAll().forEach(System.out::println);		
		System.out.println("====================================");
		System.out.println("============ITEMS=================");
		inventorySvc.getItemService().listAll().forEach(System.out::println);
		System.out.println("====================================");
	}

	@Test
	public void testIfInventorySetUp(){
		assertEquals(5,inventorySvc.getItemService().listAll().size());
	}

	@Test
	public void testCustomerInputFile() throws IOException{
		List<String[]> input = CSVUtil.readFromCSV(customerInputFile);
		assertEquals(1, input.get(0).length);
		assertEquals(Integer.parseInt(input.get(0)[0]),input.size() - 1);
	}

	@Test
	public void testIfPricesAreCorrectlyComputed() throws IOException{
		BillingService billSvc = new BillingService(inventorySvc);
		System.out.println(billSvc.printBill(BillStyle.DISCOUNTDETAILS));
		assertEquals(5, billSvc.getInventoryService().getItemService().listAll().size());
		Map<String, Double> map = BillSelectionUtil.getBillForSelection(billSvc, customerInputFile);
		assertEquals(3860.0, map.get("1,2,3,4").doubleValue(), 0);
		assertEquals(2140.0, map.get("1,5").doubleValue(), 0);
		//assertEquals(640.0, map.get("1").doubleValue(), 0);	
		//assertEquals(1200.0, map.get("1,2").doubleValue(), 0);
	}



}
