package test.com.mcaligiuri.shopping.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcaligiuri.shopping.Constants;
import com.mcaligiuri.shopping.ReceiptService;
import com.mcaligiuri.shopping.model.Basket;
import com.mcaligiuri.shopping.model.BasketImpl;
import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.model.SalesTax;
import com.mcaligiuri.shopping.taxes.BasicTax;
import com.mcaligiuri.shopping.taxes.ImportDutyTax;

/**
 * Test the {@link ReceiptService}
 * @author M.Caligiuri
 */
class TestReceiptService {

	private SalesTax[] taxes;
	private Map<ProductType, List<String>> productNames;

	private String input3 = "Input 3:\r\n"
			+ "1 imported bottle of perfume at 27.99\r\n"
			+ "1 bottle of perfume at 18.99\r\n"
			+ "1 packet of headache pills at 9.75\r\n"
			+ "1 box of imported chocolates at 11.25";
	
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
		
		productNames = new HashMap<ProductType, List<String>>();
		productNames.put(ProductType.BOOK, Arrays.asList("book"));
		productNames.put(ProductType.FOOD, Arrays.asList("chocolate bar", "box of chocolates"));
		productNames.put(ProductType.MEDICAL, Arrays.asList("packet of headache pills"));
	}

	@Test
	void testPrintBascket1() {
		// 1 imported bottle of perfume at 27.99
		// 1 bottle of perfume at 18.99
		// 1 packet of headache pills at 9.75
		// 1 box of imported chocolates at 11.25
		Basket basket = new BasketImpl(3, taxes);
		basket.addProduct(new Product("bottle of perfume", BigDecimal.valueOf(27.99), ProductType.OTHER, true), 1);
		basket.addProduct(new Product("bottle of perfume", BigDecimal.valueOf(18.99), ProductType.OTHER, false), 1);
		basket.addProduct(new Product("packet of headache pills", BigDecimal.valueOf(9.75), ProductType.MEDICAL, false), 1);
		basket.addProduct(new Product("box of chocolates", BigDecimal.valueOf(11.25), ProductType.FOOD, true), 1);
		
		String basketString = ReceiptService.printBasket(basket);
		assertEquals(output3, basketString);
	}
	
	@Test
	void testParseBascket1() {
		Basket basket = ReceiptService.parseBasket(input3, productNames, taxes);
		List<Product> products = basket.getProducts();
		
		assertEquals(3, basket.getId());
		//Check the number of products in the basket
		assertEquals(4, basket.getProducts().size());
		//Check the products name
		assertEquals("bottle of perfume", products.get(0).getName());
		assertEquals("bottle of perfume", products.get(1).getName());
		assertEquals("packet of headache pills", products.get(2).getName());
		assertEquals("box of chocolates", products.get(3).getName());
		//Check product type
		assertEquals(ProductType.OTHER, products.get(0).getType());
		assertEquals(ProductType.OTHER, products.get(1).getType());
		assertEquals(ProductType.MEDICAL, products.get(2).getType());
		assertEquals(ProductType.FOOD, products.get(3).getType());
		//Check the product prices
		assertEquals(BigDecimal.valueOf(27.99), products.get(0).getPrice());
		assertEquals(BigDecimal.valueOf(18.99), products.get(1).getPrice());
		assertEquals(BigDecimal.valueOf(9.75), products.get(2).getPrice());
		assertEquals(BigDecimal.valueOf(11.25), products.get(3).getPrice());
		//Check the product quantity
		assertEquals(BigDecimal.valueOf(1), basket.getProductQuantity(products.get(0)));
		assertEquals(BigDecimal.valueOf(1), basket.getProductQuantity(products.get(1)));
		assertEquals(BigDecimal.valueOf(1), basket.getProductQuantity(products.get(2)));
		assertEquals(BigDecimal.valueOf(1), basket.getProductQuantity(products.get(3)));
	}
	
	

}
