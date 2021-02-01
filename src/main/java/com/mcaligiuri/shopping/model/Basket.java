package com.mcaligiuri.shopping.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Define a basket of {@link Product}
 * @author M.Caligiuri
 */
public interface Basket {
	
	/**
	 * @return a basket identifier
	 */
	public int getId();

	/**
	 * Add a {@link Product} to the basket and specify a quantity
	 * @param product  a {@link Product}
	 * @param quantity the quantity to add
	 */
	public void addProduct(Product product, int quantity);
	
	/**
	 * @return a {@link List} of products in the basket
	 */
	public List<Product> getProducts();
	
	/**
	 * @param product a {@link Product}
	 * @throws IllegalArgumentException if the product is not the basket
	 * @return the quantity of the passed product in the basket
	 */
	public BigDecimal getProductQuantity(Product product);
	
	/**
	 * @param product a {@link Product}
	 * @throws IllegalArgumentException if the product is not the basket
	 * @return the total price with taxes for a passed product
	 */
	public BigDecimal getProductTotalPrice(Product product);

	/**
	 * @return the total of taxes for the products in the basket
	 */
	public BigDecimal getTotalTaxes();
	
	/**
	 * @return the total price with taxes
	 */
	public BigDecimal getTotalPriceWithTaxes();

}