package mypackage.employee;

import java.math.BigDecimal;
import java.util.Date;

public class Worker extends Employee {

	// attributes
	// * employment date
	// * bonus
	
	Date _employmentDate; 
	BigDecimal _bonus;
	
	/*protected*/ public Worker(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary, Date employmentDate, BigDecimal bonus) {
		super(firstName, surname, birthDate, manager, salary);
		_employmentDate = employmentDate;
		_bonus = bonus;
	}
	
	public BigDecimal getBonus() {
		return _bonus;
	}
	
	public Date getEmploymentDate() {
		return _employmentDate;
	}
	
}