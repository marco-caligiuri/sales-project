package com.mcaligiuri.shopping.taxes;

import java.math.BigDecimal;

import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.SalesTax;

/**
 * Define an abstract sales tax implementing {@link SalesTax}
 * @author M.Caligiuri
 */
public abstract class AbstractSalesTax implements SalesTax {

	private BigDecimal taxRate;

	/**
	 * Create a sales tax
	 * @param taxRate     the rate of the tax
	 */
	protected AbstractSalesTax(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	@Override
	public BigDecimal calculateTaxAmount(Product productItem) {
		if (productItem == null)
			throw new IllegalArgumentException("Product cannot be null");

		if (isTaxEligible(productItem)) {
			try {
				return productItem.getPrice().multiply(getTaxRate());
			} catch (NullPointerException exp) {
				throw new IllegalArgumentException("Product price cannot be null");
			}
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * Verify if the tax is applicable to the {@link Product}
	 * @param productItem a {@link Product}
	 * @return <code>true</code> if the {@link Product} is eligible for the tax
	 */
	protected abstract boolean isTaxEligible(Product productItem);

}
