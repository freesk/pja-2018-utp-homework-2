package mypackage.employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Worker extends Employee {


	// (assignment 02)
	// attributes:
	// * employment date
	// * bonus
	
	// (assignment 03)
	// attributes:
	// * has bonus
	//
	// methods:
	// * seniority is longer than given number of years
	// * seniority is longer than given number of months
	// * has bonus greater than given amount of money
	
	Date _employmentDate = null; 
	BigDecimal _bonus = null;
	
	/*protected*/ public Worker(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary, Date employmentDate, BigDecimal bonus) {
		super(firstName, surname, birthDate, manager, salary);
		_employmentDate = employmentDate;
		_bonus = bonus;
	}
	
	public boolean isSeniorityLongerThanYears(int n) {
		Calendar cal = Calendar.getInstance();
		// now 
		cal.setTime(new Date());
		// n years ago
		cal.add(Calendar.YEAR, -n);
		Date nYearsAgoDate = cal.getTime();
		return _employmentDate.before(nYearsAgoDate);
	}
	
	public boolean isSeniorityLongerThanMonths(int n) {
		Calendar cal = Calendar.getInstance();
		// now
		cal.setTime(new Date());
		// n years ago
		cal.add(Calendar.MONTH, -n);
		Date nYearsAgoDate = cal.getTime();
		return _employmentDate.before(nYearsAgoDate);
	}
	
	public boolean hasBonus() {
		if (_bonus == null) return false;
		if (_bonus.compareTo(BigDecimal.ZERO) == 0) return false;
		
		return true;
	}
	
	public BigDecimal getBonus() {
		if (_bonus == null) 
			return BigDecimal.ZERO;
		return _bonus;
	}
	
	public void setBonus(BigDecimal bonus) {
		_bonus = bonus;
	}
	
	public Date getEmploymentDate() {
		if (_employmentDate == null)
			return new Date();
		
		return _employmentDate;
	}
	
}