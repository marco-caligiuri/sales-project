package com.mcaligiuri.shopping.model;

import java.math.BigDecimal;

/**
 * Define an item of a {@link Basket} identified by a {@link Product}, taxes and quantity
 * @author M.Caligiuri
 */
public class BasketItem {

	private Product product;
	private BigDecimal taxes;
	private int quantity = 0;

	/**
	 * Create a new {@link BasketItem} and set the quantity to zero 
	 * @param product the {@link Product} referenced by the item
	 * @param taxes the amount of taxes for a single item
	 */
	public BasketItem(Product product, BigDecimal taxes) {
		this.product = product;
		this.taxes = taxes;
	}

	/**
	 * Increase the quantity of an item
	 * @param quantity the quantity to be added
	 */
	protected void addQuantity(int quantity) {
		this.quantity += quantity;
	}
	
	/**
	 * @return the quantity of an item
	 */
	public BigDecimal getQuantity() {
		return BigDecimal.valueOf(quantity);
	}
	
	/**
	 * @return the amount of taxes for a single item
	 */
	public BigDecimal getTaxes() {
		return taxes;
	}
	
	/**
	 * Calculate the total price with taxes of an item in a basket
	 * @return the total price
	 */
	public BigDecimal getTotalPriceWithTaxes() {
		BigDecimal totalPrice = product.getPrice().multiply(getQuantity());
		totalPrice = totalPrice.add(taxes.multiply(getQuantity()));
		return totalPrice;
	}
}
