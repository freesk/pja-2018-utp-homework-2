package mypackage.payroll;

import java.math.BigDecimal;

import mypackage.employee.Employee;

public final class PayrollEntry {

	private final Employee _employee;
	private final BigDecimal _salaryPlusBonus;
	private BigDecimal _salary = new BigDecimal("0");
	private BigDecimal _bonus = new BigDecimal("0");
	
	public PayrollEntry(Employee employee, BigDecimal salary, BigDecimal bonus) {
		_employee = employee;
		// validate whether salary and bonus are not null
		if (salary != null)
			_salary = salary;
		if (bonus != null) 
			_bonus = bonus;
		_salaryPlusBonus = _salary.add(_bonus);
	}
	
	public BigDecimal getSalaryPlusBonus() {
		return _salaryPlusBonus;
	}
	
	@Override
	public String toString() {
		return _employee  + " " + _salaryPlusBonus;
	}
}