package com.mcaligiuri.shopping.taxes;

import java.math.BigDecimal;

import com.mcaligiuri.shopping.model.Product;

/**
 * Define an import duty tax
 * @author M.Caligiuri
 */
public class ImportDutyTax extends AbstractSalesTax {

	/**
	 * Create a new import duty tax
	 * @param taxRate     the rate of the tax
	 */
	public ImportDutyTax(BigDecimal taxRate) {
		super(taxRate);
	}

	@Override
	protected boolean isTaxEligible(Product productItem) {
		//Verify if the {@link Product} item is imported and so the import duty tax is
		//applicable
		return productItem.isImported();
	}

}
