package test.com.mcaligiuri.shopping.taxes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcaligiuri.shopping.Constants;
import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.model.SalesTax;
import com.mcaligiuri.shopping.taxes.BasicTax;

/**
 * Unit test for {@link BasicTax}
 * @author M.Caligiuri
 */
class TestBasicTax {

	private SalesTax basicTax;

	@BeforeEach
	void setUp() throws Exception {
		// Basic sales tax is applicable at a rate of 10% on all goods, except books,
		// food, and medical products that are exempt.
		basicTax = new BasicTax(Constants.BASIC_TAX_RATE, ProductType.BOOK, ProductType.FOOD, ProductType.MEDICAL);
	}

	@Test
	void testExceptionProductItemNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			basicTax.calculateTaxAmount(null);
		});
	}

	@Test
	void testExceptionProductItemPriceNull() {
		Product itemProduct = new Product("music CD", null, ProductType.OTHER, false);
		assertThrows(IllegalArgumentException.class, () -> {
			basicTax.calculateTaxAmount(itemProduct);
		});
	}

	@Test
	void testBasicTaxBookNotApplicable() {
		Product itemProduct = new Product("book", BigDecimal.valueOf(12.49), ProductType.BOOK, false);
		assertEquals(BigDecimal.ZERO, basicTax.calculateTaxAmount(itemProduct));
	}

	@Test
	void testBasicTaxFoodNotApplicable() {
		Product itemProduct = new Product("chocolate bar", BigDecimal.valueOf(0.85), ProductType.FOOD, false);
		assertEquals(BigDecimal.ZERO, basicTax.calculateTaxAmount(itemProduct));
	}

	@Test
	void testBasicTaxMedicalNotApplicable() {
		Product itemProduct = new Product("packet of headache pills", BigDecimal.valueOf(9.75), ProductType.MEDICAL,
				false);
		assertEquals(BigDecimal.ZERO, basicTax.calculateTaxAmount(itemProduct));
	}

	@Test
	void testBasicTaxMusicApplicable() {
		Product itemProduct = new Product("music CD", BigDecimal.valueOf(14.99), ProductType.OTHER, false);
		assertEquals(BigDecimal.valueOf(1.499), basicTax.calculateTaxAmount(itemProduct));
	}

}
