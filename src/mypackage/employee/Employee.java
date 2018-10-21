package mypackage.employee;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Employee extends Person {

	//
	// attributes:
	// * salary (use BigDecimal type for representing currency values)
	// * manager (empty if at top of hierarchy)
	
	private Manager _manager = null;
	private BigDecimal _salary = new BigDecimal("0");
	
	protected Employee(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary) {
		super(firstName, surname, birthDate);
		_manager = manager;
		if (salary != null)
			_salary = salary;
	}
	
	public BigDecimal getSalary() {
		return _salary;
	}
 	
	public boolean isSubordinatedTo(Manager m) {
		return m.equals(_manager);
	}
}