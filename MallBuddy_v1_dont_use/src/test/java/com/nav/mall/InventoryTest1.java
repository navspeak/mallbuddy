package com.nav.mall;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nav.csv.util.BillSelectionUtil;
import com.nav.csv.util.CSVUtil;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.services.BillingService;
import com.nav.services.InventoryService;
import com.nav.strategy.BillStyle;
public class InventoryTest1 {


	private static InventoryService inventorySvc;
	private static String csvInventory;
	private static String customerInputFile;
	private static String csvbrand;
	private static String csvCategory;

	@BeforeClass
	public static void inputPathSetup() throws URISyntaxException, InvalidEntryInCSVFile {
		// Test Data: inventory1.csv
		/*5
		1, Arrow,Shirts,800
		2, Vero Moda,Dresses,1400
		3, Provogue,Footwear,1800
		4, Wrangler,Jeans,2200
		5, UCB,Shirts,1500*/

		// Test Data: customerInput1.csv
		/*2
		1,2,3,4
		1,5*/

		// Test Data: brands1.csv
		/*Wrangler,10
		Arrow,20
		Vero Moda,60
		UCB,0
		Adidas,5
		Provogue,20*/

		// Test Data: category1.csv
		/*Apparel,,Menswear:Womenswear,0
		Menswear,Apparel,Shirts:Trousers:Casuals:Jeans,0
		Womenswear,Apparel,Dresses:Footwear,50
		Shirts,Menswear,,0
		Trousers,Menswear,,0
		Casuals,Menswear,,30
		Jeans,Menswear,,20
		Dresses,Womenswear,,0
		Footwear,,Womenswear,50*/

		Path csvFile = Paths.get("src/test/resources/inventory1.csv");
		csvInventory = csvFile.toAbsolutePath().toString();
		Path custcsvFile = Paths.get("src/test/resources/customerInput1.csv");
		customerInputFile = custcsvFile.toAbsolutePath().toString();
		Path brandcsvFile = Paths.get("src/test/resources/brands1.csv");
		csvbrand = brandcsvFile.toAbsolutePath().toString();
		Path categorycsvFile = Paths.get("src/test/resources/category1.csv");
		csvCategory = categorycsvFile.toAbsolutePath().toString();
		inventorySvc = new InventoryService();
		inventorySvc.loadInventory(csvInventory,csvbrand,csvCategory);
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
		Map<String, Double> map = BillSelectionUtil.getBillForSelection(billSvc, customerInputFile);
		assertEquals(3860.0, map.get("1,2,3,4").doubleValue(), 0);
		assertEquals(2140.0, map.get("1,5").doubleValue(), 0);
		//assertEquals(640.0, map.get("1").doubleValue(), 0);	
		//assertEquals(1200.0, map.get("1,2").doubleValue(), 0);
	}



}
