package test.com.mcaligiuri.shopping.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcaligiuri.shopping.Constants;
import com.mcaligiuri.shopping.model.Basket;
import com.mcaligiuri.shopping.model.BasketImpl;
import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.taxes.BasicTax;
import com.mcaligiuri.shopping.taxes.ImportDutyTax;

/**
 * Unit test for {@link BasketImpl}
 * @author M.Caligiuri
 */
class TestBasket {

	private Basket basket;

	@BeforeEach
	void setUp() throws Exception {
		// Basic sales tax is applicable at a rate of 10% on all goods, except books,
		// food, and medical products that are exempt.
		basket = new BasketImpl(1,
				new BasicTax(Constants.BASIC_TAX_RATE, ProductType.BOOK, ProductType.FOOD, ProductType.MEDICAL),
				new ImportDutyTax(Constants.IMPORT_TAX_RATE));
	}

	@Test
	void testOneBookProductItem() {
		Product itemProduct = new Product("book", BigDecimal.valueOf(12.49), ProductType.BOOK, false);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(12.49), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testOneMusicProductItem() {
		Product itemProduct = new Product("music CD", BigDecimal.valueOf(14.99), ProductType.OTHER, false);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(16.49), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testOneChocolateProductItem() {
		Product itemProduct = new Product("chocolate bar", BigDecimal.valueOf(0.85), ProductType.FOOD, false);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(0.85), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testOneImportedBoxChocolateProductItem() {
		Product itemProduct = new Product("imported box of chocolates", BigDecimal.valueOf(10.00), ProductType.FOOD,
				true);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(10.50).setScale(2), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testOneImportedPerfumeProductItem() {
		Product itemProduct = new Product("imported bottle of perfume", BigDecimal.valueOf(47.50), ProductType.OTHER,
				true);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(54.65), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testTwoBookProductItem() {
		Product itemProduct = new Product("book", BigDecimal.valueOf(12.49), ProductType.BOOK, false);
		basket.addProduct(itemProduct, 1);
		basket.addProduct(itemProduct, 1);

		assertEquals(BigDecimal.valueOf(24.98), basket.getProductTotalPrice(itemProduct));
	}

	@Test
	void testInput1Basket() {
		// 1 book at 12.49
		// 1 music CD at 14.99
		// 1 chocolate bar at 0.85
		basket.addProduct(new Product("book", BigDecimal.valueOf(12.49), ProductType.BOOK, false), 1);
		basket.addProduct(new Product("music CD", BigDecimal.valueOf(14.99), ProductType.OTHER, false), 1);
		basket.addProduct(new Product("chocolate bar", BigDecimal.valueOf(0.85), ProductType.FOOD, false), 1);

		assertEquals(BigDecimal.valueOf(1.50).setScale(2), basket.getTotalTaxes());
		assertEquals(BigDecimal.valueOf(29.83), basket.getTotalPriceWithTaxes());
	}

	@Test
	void testInput2Basket() {
		// 1 imported box of chocolates at 10.00
		// 1 imported bottle of perfume at 47.50
		basket.addProduct(new Product("imported box of chocolates", BigDecimal.valueOf(10.00), ProductType.FOOD, true),
				1);
		basket.addProduct(new Product("imported bottle of perfume", BigDecimal.valueOf(47.50), ProductType.OTHER, true),
				1);

		assertEquals(BigDecimal.valueOf(7.65), basket.getTotalTaxes());
		assertEquals(BigDecimal.valueOf(65.15), basket.getTotalPriceWithTaxes());
	}

	@Test
	void testInput3Basket() {
		// 1 imported bottle of perfume at 27.99
		// 1 bottle of perfume at 18.99
		// 1 packet of headache pills at 9.75
		// 1 box of imported chocolates at 11.25
		basket.addProduct(new Product("imported bottle of perfume", BigDecimal.valueOf(27.99), ProductType.OTHER, true),
				1);
		basket.addProduct(new Product("bottle of perfume", BigDecimal.valueOf(18.99), ProductType.OTHER, false), 1);
		basket.addProduct(new Product("packet of headache pills", BigDecimal.valueOf(9.75), ProductType.MEDICAL, false),
				1);
		basket.addProduct(new Product("box of imported chocolates", BigDecimal.valueOf(11.25), ProductType.FOOD, true),
				1);

		assertEquals(BigDecimal.valueOf(6.70).setScale(2), basket.getTotalTaxes());
		assertEquals(BigDecimal.valueOf(74.68), basket.getTotalPriceWithTaxes());
	}

}
