package com.mcaligiuri.shopping.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Define a generic product identified by name, price, type and if it is an imported goods
 * @author M.Caligiuri
 */
public class Product {

	private String name;
	private BigDecimal price;
	private ProductType type;
	private boolean isImported = false;

	/**
	 * Create a new {@link Product}
	 * @param name        the name of the product
	 * @param price       the price of the product
	 * @param type        the {@link ProductType} of the product
	 * @param isImported  <code>true</code> if the product is imported
	 */
	public Product(String name, BigDecimal price, ProductType type, boolean isImported) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.isImported = isImported;
	}

	/**
	 * @return {@link String} the name of the product
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return {@link BigDecimal} the price of the product
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @return {@link ProductType} of the product
	 */
	public ProductType getType() {
		return type;
	}

	/**
	 * @return <code>true</code> if the product is imported
	 */
	public boolean isImported() {
		return isImported;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.price, this.type, this.isImported);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;
		
		final Product other = (Product) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.price, other.price)
				&& Objects.equals(this.type, other.type)
				&& Objects.equals(this.isImported, other.isImported);
	}

}
