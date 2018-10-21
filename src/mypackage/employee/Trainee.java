package mypackage.employee;

import java.math.BigDecimal;
import java.util.Date;

public class Trainee extends Employee {

	// attributes:
	// * practice start date
	// * practice length (in days)
	
	public final Date _startDate;
	public final int _durationInDays;
	
	public Trainee(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary, Date startDate, int durationInDays) {
		super(firstName, surname, birthDate, manager, salary);
		_startDate = startDate;
		_durationInDays = durationInDays;
	}
}