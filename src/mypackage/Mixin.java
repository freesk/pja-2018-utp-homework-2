package mypackage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Mixin {
	
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static LocalDate DateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
	    return base.multiply(pct).divide(ONE_HUNDRED);
	}

}
