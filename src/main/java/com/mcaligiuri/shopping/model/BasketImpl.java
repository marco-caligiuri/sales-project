package com.mcaligiuri.shopping.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mcaligiuri.shopping.Constants;

/**
 * Implements a {@link Basket} that contains a list of {@link Product}
 * @author M.Caligiuri
 */
public class BasketImpl implements Basket {

	private int id;
	private Map<Product, BasketItem> items = new LinkedHashMap<Product, BasketItem>();
	private List<SalesTax> salesTaxes = new ArrayList<SalesTax>();

	/**
	 * Initialize a new {@link BasketImpl} specifying the taxes that must be applied
	 * @param id the identifier of the basket
	 * @param taxes an array of {@link SalesTax}
	 */
	public BasketImpl(int id, SalesTax... taxes) {
		this.id = id;
		this.salesTaxes = Arrays.asList(taxes);
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void addProduct(Product product, int quantity) {
		// Check if the same Product is already in the basket
		BasketItem item = items.get(product);
		// If the Product is not in the Basket create a new BasketItem
		if (item == null) {
			item = createBasketItem(product);
			items.put(product, item);
		}
		// Increase the BusketItem quantity
		item.addQuantity(quantity);
	}
	
	@Override
	public List<Product> getProducts() {
		return new ArrayList<Product>(items.keySet());
	}

	@Override
	public BigDecimal getProductQuantity(Product product) {
		BasketItem item = items.get(product);
		if (item != null)
			return item.getQuantity();
		else
			throw new IllegalArgumentException("Product is not in the basket");
	}

	@Override
	public BigDecimal getProductTotalPrice(Product product) {
		BasketItem item = items.get(product);
		if (item != null)
			return item.getTotalPriceWithTaxes();
		else
			throw new IllegalArgumentException("Product is not in the basket");
	}
	
	@Override
	public BigDecimal getTotalTaxes() {
		BigDecimal totalTaxes = BigDecimal.ZERO;
		//Sum the taxes multiplied for the quantity of the Product
		for (Map.Entry<Product, BasketItem> entry : items.entrySet()) {
			BasketItem item = entry.getValue();
			totalTaxes = totalTaxes.add(item.getTaxes().multiply(item.getQuantity()));
		}
		return totalTaxes;
	}

	@Override
	public BigDecimal getTotalPriceWithTaxes() {
		BigDecimal total = BigDecimal.ZERO;
		for (Map.Entry<Product, BasketItem> entry : items.entrySet()) {
			BasketItem item = entry.getValue();
			total = total.add(item.getTotalPriceWithTaxes());
		}
		return total;
	}
	
	//Create a BusketItem and calculate the amount of the taxes applicable for the Product 
	private BasketItem createBasketItem(Product product) {
		BigDecimal itemTaxes = BigDecimal.ZERO;
		for (SalesTax tax : salesTaxes) {
			BigDecimal taxAmount = tax.calculateTaxAmount(product);
			itemTaxes = itemTaxes.add(roundAmount(taxAmount));
		}
		return new BasketItem(product, itemTaxes);
	}
	
	//Round an amount to the next TAX_ROUNDING_UNIT
	private BigDecimal roundAmount(BigDecimal amount) {
		if(BigDecimal.ZERO.equals(amount))
			return amount;
		//Calculate how many times the tax rounding unit is contained in the amount variable rounding up to the next non-zero value
		BigDecimal divided = amount.divide(Constants.TAX_ROUNDING_UNIT, 0, RoundingMode.UP);
		return divided.multiply(Constants.TAX_ROUNDING_UNIT); 
	}
	
}
