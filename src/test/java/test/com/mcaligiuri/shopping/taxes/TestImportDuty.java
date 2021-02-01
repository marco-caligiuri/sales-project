package test.com.mcaligiuri.shopping.taxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcaligiuri.shopping.Constants;
import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.model.SalesTax;
import com.mcaligiuri.shopping.taxes.ImportDutyTax;

/**
 * Unit test for the {@link ImportDutyTax} class.
 * @author M.Caligiuri
 */
class TestImportDuty {
	private SalesTax importDutyTax;

	@BeforeEach
	void setUp() throws Exception {
		// Import duty is an additional sales tax applicable on all imported goods at a
		// rate of 5%, with no exemptions.
		importDutyTax = new ImportDutyTax(Constants.IMPORT_TAX_RATE);
	}

	@Test
	public void testImportedProductApplicable() {
		Product itemProduct = new Product("imported bottle of perfume", BigDecimal.valueOf(47.50), ProductType.OTHER,
				true);
		assertEquals(BigDecimal.valueOf(2.375), importDutyTax.calculateTaxAmount(itemProduct));
	}

	@Test
	public void testLocalProductNotApplicable() {
		Product itemProduct = new Product("book", BigDecimal.valueOf(12.49), ProductType.BOOK, false);
		assertEquals(BigDecimal.ZERO, importDutyTax.calculateTaxAmount(itemProduct));
	}
}
