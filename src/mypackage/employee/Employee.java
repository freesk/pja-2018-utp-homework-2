package mypackage.employee;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Employee extends Person {

	// (assignment 02)
	// attributes:
	// * salary
	// * manager (empty if at top of hierarchy)

	// (assignment 03)
	// methods:
	// * salary is greater than given amount of money
	// * salary is less than given amount of money
	// * compare salary with other employee salary
	
	private Manager _manager = null;
	private BigDecimal _salary = new BigDecimal("0");
	
	protected Employee(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary) {
		super(firstName, surname, birthDate);
		_manager = manager;
		if (salary != null)
			_salary = salary;
	}
	
	public boolean isSalaryGreaterThan(BigDecimal n) {
		return _salary.compareTo(n) > 0;
	}
	
	public boolean isSalaryLessThan(BigDecimal n) {
		return _salary.compareTo(n) < 0;
	}
	
	public int compareSalaryTo(Employee e) {
		return _salary.compareTo(e.getSalary());
	}
	
	public BigDecimal getSalary() {
		return _salary;
	}
	
	public void setSalary(BigDecimal sal) {
		_salary = sal;
	}
 	
	public boolean isSubordinatedTo(Manager m) {
		return m.equals(_manager);
	}
}