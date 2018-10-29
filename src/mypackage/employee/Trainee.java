package mypackage.employee;

import java.math.BigDecimal;
import java.util.Date;

public class Trainee extends Employee {

	// (assignment 02)
	// attributes:
	// * practice start date
	// * practice length (in days)
	
	// (assignment 03)
	// * practice length is shorter than given number of days
	// * practice length is longer than given number of days
	
	public final Date _startDate;
	public final int _durationInDays;
	
	public Trainee(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary, Date startDate, int durationInDays) {
		super(firstName, surname, birthDate, manager, salary);
		_startDate = startDate;
		_durationInDays = durationInDays;
	}
	
	public boolean practiceLengthIsShorterThan(int n) {
		return _durationInDays < n;
	}
	
	public boolean practiceLengthIsLongerThan(int n) {
		return _durationInDays > n;
	}
}