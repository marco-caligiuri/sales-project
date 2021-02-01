package com.mcaligiuri.shopping.model;

import java.math.BigDecimal;

/**
 * Define a sales tax
 * @author M.Caligiuri
 */
public interface SalesTax {

	/**
	 * @return {@link BigDecimal} the rate of the tax
	 */
	public BigDecimal getTaxRate();

	/**
	 * Calculate the amount of the tax for a {@link Product}
	 * @param productItem a {@link Product} item
	 * @throws IllegalArgumentException if the productItem parameter or the
	 *                                  ProductItem price are null.
	 * @return the amount of the tax
	 */
	public BigDecimal calculateTaxAmount(Product productItem);

}