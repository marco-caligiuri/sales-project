package test.com.mcaligiuri.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcaligiuri.shopping.Constants;
import com.mcaligiuri.shopping.ReceiptService;
import com.mcaligiuri.shopping.model.Basket;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.model.SalesTax;
import com.mcaligiuri.shopping.taxes.BasicTax;
import com.mcaligiuri.shopping.taxes.ImportDutyTax;

/**
 * Test the solution
 * @author M.Caligiuri
 */
class TestSolution {

	private SalesTax[] taxes;
	private Map<ProductType, List<String>> productNames;

	private String input1 = "Input 1:\r\n"
			+ "1 book at 12.49\r\n"
			+ "1 music CD at 14.99\r\n"
			+ "1 chocolate bar at 0.85";
	private String input2 = "Input 2:\r\n"
			+ "1 imported box of chocolates at 10.00\r\n"
			+ "1 imported bottle of perfume at 47.50";
	private String input3 = "Input 3:\r\n"
			+ "1 imported bottle of perfume at 27.99\r\n"
			+ "1 bottle of perfume at 18.99\r\n"
			+ "1 packet of headache pills at 9.75\r\n"
			+ "1 box of imported chocolates at 11.25";
	
	private String output1 = "Output 1:\r\n"
			+ "1 book: 12.49\r\n"
			+ "1 music CD: 16.49\r\n"
			+ "1 chocolate bar: 0.85\r\n"
			+ "Sales Taxes: 1.50\r\n"
			+ "Total: 29.83";
	private String output2 = "Output 2:\r\n"
			+ "1 imported box of chocolates: 10.50\r\n"
			+ "1 imported bottle of perfume: 54.65\r\n"
			+ "Sales Taxes: 7.65\r\n"
			+ "Total: 65.15";
	private String output3 = "Output 3:\r\n"
			+ "1 imported bottle of perfume: 32.19\r\n"
			+ "1 bottle of perfume: 20.89\r\n"
			+ "1 packet of headache pills: 9.75\r\n"
			+ "1 imported box of chocolates: 11.85\r\n"
			+ "Sales Taxes: 6.70\r\n"
			+ "Total: 74.68";

	@BeforeEach
	void setUp() throws Exception {
		// Basic sales tax is applicable at a rate of 10% on all goods, except books,
		// food, and medical products that are exempt.
		taxes = new SalesTax[] {
				new BasicTax(Constants.BASIC_TAX_RATE, ProductType.BOOK, ProductType.FOOD, ProductType.MEDICAL),
				new ImportDutyTax(Constants.IMPORT_TAX_RATE) };
		
		//Define the type of goods
		productNames = new HashMap<ProductType, List<String>>();
		productNames.put(ProductType.BOOK, Arrays.asList("book"));
		productNames.put(ProductType.FOOD, Arrays.asList("chocolate bar", "box of chocolates"));
		productNames.put(ProductType.MEDICAL, Arrays.asList("packet of headache pills"));
	}

	@Test
	void testBascket1() {
		Basket basket = ReceiptService.parseBasket(input1, productNames, taxes);
		String basketString = ReceiptService.printBasket(basket);
		assertEquals(output1, basketString);
	}
	
	@Test
	void testBascket2() {
		Basket basket = ReceiptService.parseBasket(input2, productNames, taxes);
		String basketString = ReceiptService.printBasket(basket);
		assertEquals(output2, basketString);
	}
	
	@Test
	void testBascket3() {
		Basket basket = ReceiptService.parseBasket(input3, productNames, taxes);
		String basketString = ReceiptService.printBasket(basket);
		assertEquals(output3, basketString);
	}
	
	

}
