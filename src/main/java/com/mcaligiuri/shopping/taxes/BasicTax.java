package com.mcaligiuri.shopping.taxes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;

/**
 * Define a basic tax and optionally specify a list of {@link ProductType} for
 * which the tax is not applicable.
 * @author M.Caligiuri
 */
public class BasicTax extends AbstractSalesTax {

	private List<ProductType> productTypes;

	/**
	 * Create a new basic tax
	 * @param taxRate     the rate of the tax
	 */
	public BasicTax(BigDecimal taxRate) {
		super(taxRate);
	}

	/**
	 * Create a new basic tax specifying an array of {@link ProductType} for
	 * which the tax is not applicable
	 * @param taxRate     the rate of the tax
	 * @param exeptTypes  an array of {@link ProductType} for which the tax is not
	 *                    applicable
	 */
	public BasicTax(BigDecimal taxRate, ProductType... exeptTypes) {
		super(taxRate);
		this.productTypes = Arrays.asList(exeptTypes);
	}

	@Override
	protected boolean isTaxEligible(Product productItem) {
		// If a list of product types is defined it will be used to verify if the tax is
		// not applicable
		if (productTypes != null)
			return !productTypes.contains(productItem.getType());
		return true;
	}

}
