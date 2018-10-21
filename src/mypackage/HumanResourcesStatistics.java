package mypackage;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import mypackage.employee.Employee;
import mypackage.employee.Manager;
import mypackage.employee.Worker;
import mypackage.payroll.PayrollEntry;

public final class HumanResourcesStatistics {

	public static List<PayrollEntry> payroll(List<Employee> employees) {
		List<PayrollEntry> res = employees.stream()
				// not sure what else we can do here to pull out the bonus value
				.map(e -> new PayrollEntry(e, e.getSalary(), (e instanceof Worker ? ((Worker) e).getBonus() : null)))
				.collect(Collectors.toList());
		
		return res;
	}
	
	// immediate subordinates or all in the tree?
	public static List<PayrollEntry> subordinatesPayroll(Manager manager, List<Employee> allEmployees) {
		return payroll(manager.getAllSubordinates(allEmployees));
	}

	public static BigDecimal bonusTotal(List<Employee> employees) {
		BigDecimal res = employees.stream()
				.filter(e -> e instanceof Worker)
			    .map(e -> (Worker) e)
			    .map(e -> e.getBonus())
			    .reduce(new BigDecimal("0"), (BigDecimal a, BigDecimal b) -> a.add(b));
				
		return res;
	}
	
	public static Employee getLongestSeniorityEmployee(List<Employee> allEmployees) {
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker)
			    .map(e -> (Worker) e)
			    .sorted(Comparator.comparing(o -> o.getEmploymentDate()))
				.collect(Collectors.toList());
		
		return res.get(0);
	}

	public static BigDecimal getTheHighestSalary(List<Employee> allEmployees) {
		List<Employee> res = allEmployees.stream()
			    .sorted(Comparator.comparing(o -> o.getSalary()))
				.collect(Collectors.toList());
		
		return res.get(res.size() - 1).getSalary();
	}
	
	public static BigDecimal getTheHighestSalaryPlusBonus(List<Employee> allEmployees) {
		
		List<PayrollEntry> res = payroll(allEmployees).stream()
			    .sorted(Comparator.comparing(o -> o.getSalaryPlusBonus()))
				.collect(Collectors.toList());
		
		return res.get(res.size() - 1).getSalaryPlusBonus();
	}
	
	public static List<Employee> searchSubordinatesByFirstLetter(Manager m, List<Employee> allEmployees) {
		
		final String pattern = "A";
		
		List<Employee> res = m.getAllSubordinates(allEmployees).stream()
				.filter(e -> e.getFirstName().substring(0, 1).equals(pattern))
				.collect(Collectors.toList());
		
		return res;
	}
	
	public static List<Employee> searchForEmployeesWhoEarnMoreThan(BigDecimal n, List<Employee> allEmployees) {
		List<Employee> res = allEmployees.stream()
			    .filter( e -> e.getSalary().compareTo(n) > 0 )
				.collect(Collectors.toList());
		
		return res;
	}
}