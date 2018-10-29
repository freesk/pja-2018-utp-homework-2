package mypackage;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import mypackage.employee.Trainee;
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
				.map(e -> (Worker) e).map(e -> e.getBonus())
				.reduce(new BigDecimal("0"), (BigDecimal a, BigDecimal b) -> a.add(b));

		return res;
	}

	public static Employee getLongestSeniorityEmployee(List<Employee> allEmployees) {
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker).map(e -> (Worker) e)
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
				.filter(e -> e.getSalary().compareTo(n) > 0)
				.collect(Collectors.toList());

		return res;
	}

	// * search for Employees older than given employee and earning less than him
	public static List<Employee> olderThenAndEarnMore(List<Employee> allEmployees, Employee employee) {
		List<Employee> res = allEmployees.stream()
			.filter(e -> e.isOlderThan(employee) && e.isSalaryLessThan(employee.getSalary()))
			.collect(Collectors.toList());
		
		return res;
	}

	//
	// * search for Trainees whose practice length is longer than given number of
	// days and raise their salary by 5%
	public static List<Trainee> practiceLengthLongerThan(List<Employee> allEmployees, int daysCount) {
		List<Trainee> res = allEmployees.stream()
				.filter(e -> e instanceof Trainee)
				.map(e -> (Trainee) e)
				.filter(t -> t.practiceLengthIsLongerThan(daysCount))
				.map(t -> {
					BigDecimal sal = t.getSalary();
					BigDecimal rise = Mixin.percentage(t.getSalary(), new BigDecimal("5"));
					BigDecimal newSal = sal.add(rise);
					t.setSalary(newSal);
					return t;
				})
				.collect(Collectors.toList());
		
		return res;
	}

	//
	// * search for Workers whose seniority is longer than given number of months
	// and give them bonus of 300 if their bonus is smaller
	// ** smaller than what? 
	public static List<Employee> seniorityLongerThan(List<Employee> allEmployees, int monthCount) {
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.isSeniorityLongerThanMonths(monthCount))
				.map(e -> {
					BigDecimal bonus = e.getBonus();
					BigDecimal defaultBonus = new BigDecimal("300");
					if (bonus.compareTo(defaultBonus) < 0)
						e.setBonus(defaultBonus);
					
					return e;
				})
				.collect(Collectors.toList());
		
		return res;
	}

	//
	// * search for Workers whose seniority is between 1 and 3 years and give them
	// raise of salary by 10%
	public static List<Employee> seniorityBetweenOneAndThreeYears(List<Employee> allEmployees) {
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.isSeniorityLongerThanYears(1) && (!e.isSeniorityLongerThanYears(3)) )
				.map(e -> {
					BigDecimal sal = e.getSalary();
					BigDecimal rise = Mixin.percentage(e.getSalary(), new BigDecimal("10"));
					BigDecimal newSal = sal.add(rise);
					e.setSalary(newSal);
					
					return e;
				})
				.collect(Collectors.toList());
		
		return res;
	}

	//
	// * search for Workers whose seniority is longer than the seniority of a given
	// employee and earn less than him and align their salary with the given
	// employee
	public static List<Employee> seniorityLongerThan(List<Employee> allEmployees, Employee employee) {
		
		if (!(employee instanceof Worker)) throw new IllegalArgumentException();
		
		// cast
		Worker w = (Worker) employee;
		
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.getEmploymentDate().before(w.getEmploymentDate()))
				.map(e -> {
					e.setSalary(w.getSalary());				
					return e;
				})
				.collect(Collectors.toList());
		
		return res;
	}

	//
	// * search for Workers whose seniority is between 2 and 4 years and whose age
	// is greater than given number of years
	
	// ** taking in the type of Employee and returning the same type makes 
	// more sense for me than returning the type of Worker
	public static List<Employee> seniorityBetweenTwoAndFourYearsAndAgeGreaterThan(List<Employee> allEmployees, int age) {
		List<Employee> res = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.isSeniorityLongerThanYears(2) && (!e.isSeniorityLongerThanYears(4)) )
				.filter(e -> e.getAgeInYears(new Date()) > age)
				.collect(Collectors.toList());
		
		return res;
	}
}