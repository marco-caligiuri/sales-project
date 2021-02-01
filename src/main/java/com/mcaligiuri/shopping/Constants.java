package com.mcaligiuri.shopping;

import java.math.BigDecimal;

public final class Constants {

	// Basic sales tax is applicable at a rate of 10%
	public static final BigDecimal BASIC_TAX_RATE = BigDecimal.valueOf(0.10);
	// Import duty is an additional sales tax applicable on all imported goods at a
	// rate of 5%,
	public static final BigDecimal IMPORT_TAX_RATE = BigDecimal.valueOf(0.05);
	// The rounding rules for sales tax are that for a tax rate of n%, a shelf price
	// of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.
	public static final BigDecimal TAX_ROUNDING_UNIT = BigDecimal.valueOf(0.05);

}
