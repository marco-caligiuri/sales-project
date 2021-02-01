package com.mcaligiuri.shopping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mcaligiuri.shopping.model.Basket;
import com.mcaligiuri.shopping.model.BasketImpl;
import com.mcaligiuri.shopping.model.Product;
import com.mcaligiuri.shopping.model.ProductType;
import com.mcaligiuri.shopping.model.SalesTax;

/**
 * Main class to parse a string into a {@link Basket} of {@link Product} or to format as a string a {@link Basket}
 * @author M.Caligiuri
 */
public class ReceiptService {

	//Pattern of a product
	private static final String ITEM_PATTERN = "(?<quantity>\\d+) (?<name>[\\p{Alpha}\\s]+)at (?<price>.*+)";
	private static final String ITEM_FORMAT = "%d %s: %.2f";
	
	//Pattern for a basket
	private static final String BASKET_ID_PATTERN = "Input (?<id>\\d+)";
		
	//Matcher identifier
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final String QUANTITY = "quantity";
	private static final String ID = "id";
		
	private static final String IMPORTED = "imported ";
	private static final String COLON = ":";
	private static final String NEW_LINE = "\r\n";
	private static final String OUTPUT = "Output ";
	private static final String SALES_TAXES = "Sales Taxes: ";
	private static final String TOTAL = "Total: ";

	/**
	 * Parse a string representing a basket in the format:
	 * 
	 * Input 1:
	 * 1 book at 12.49
	 * 
	 * @param basketString a string to be parsed
	 * @param productNames a {@link Map} defining the association between product name and product type
	 * @param taxes an array of {@link SalesTax}
	 * @return a {@link Basket}
	 */
	public static Basket parseBasket(String basketString, Map<ProductType,List<String>> productNames, SalesTax... taxes) {

		Basket basket = null;
		// Search for the basket identifier in the format "Input #:"
		Pattern basketPattern = Pattern.compile(BASKET_ID_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher basketMatch = basketPattern.matcher(basketString);
		if (basketMatch.find()) {
			// Create a new Basket
			basket = new BasketImpl(Integer.parseInt(basketMatch.group(ID)), taxes);
			// Search for a product line in the format quantity, name, "at", price
			Pattern itemPattern = Pattern.compile(ITEM_PATTERN, Pattern.CASE_INSENSITIVE);
			Matcher itemMatch = itemPattern.matcher(basketString);
			while (itemMatch.find()) {
				// Remove the "imported" string from the name
				String name = itemMatch.group(NAME).trim().replace(IMPORTED, "");
				BigDecimal price = new BigDecimal(itemMatch.group(PRICE));
				boolean isImported = itemMatch.group(NAME).contains(IMPORTED);
				// Create a new Product
				Product product = new Product(name, price, calculateType(productNames, name), isImported);
				basket.addProduct(product, Integer.parseInt(itemMatch.group(QUANTITY)));
			}
		}
		return basket;
	}

	/**
	 * Print a {@link Basket} to a string in the format:
	 * 
	 * Output 1:
	 * 1 book : 12.49
	 * 
	 * @param basket a {@link Basket}
	 * @return a {@link String} representing a {@link Basket}
	 */
	public static String printBasket(Basket basket) {
		StringBuilder builder = new StringBuilder(OUTPUT);
		// Print the identifier of the basket
		builder.append(basket.getId()).append(COLON);
		// For each product in the basket print the quantity, the name, the total cost
		for (Product product : basket.getProducts()) {
			builder.append(NEW_LINE)
					.append(String.format(
							Locale.ROOT, 
							ITEM_FORMAT, 
							basket.getProductQuantity(product).intValue(),
							product.isImported() ? IMPORTED + product.getName() : product.getName(),
							basket.getProductTotalPrice(product)));
		}
		// Print the total of sales taxes
		builder.append(NEW_LINE).append(SALES_TAXES).append(basket.getTotalTaxes());
		// Print the total cost
		builder.append(NEW_LINE).append(TOTAL).append(basket.getTotalPriceWithTaxes());
		return builder.toString();
	}
	
	private static ProductType calculateType(Map<ProductType, List<String>> productNames, String name) {
		for (Map.Entry<ProductType, List<String>> entry : productNames.entrySet()) {
			if (entry.getValue().contains(name))
				return entry.getKey();
		}
		return ProductType.OTHER;
	}

}
