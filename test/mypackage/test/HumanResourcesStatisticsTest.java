package mypackage.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import mypackage.HumanResourcesStatistics;
import mypackage.employee.Employee;
import mypackage.employee.Manager;
import mypackage.employee.Trainee;
import mypackage.employee.Worker;
import mypackage.payroll.PayrollEntry;

public class HumanResourcesStatisticsTest {
	
	// Create a HR structure which resembles the below one:
	//
	// Director (an instance of Manager class (Director does not have a manager)
	//   |- Manager01
	//        |- Worker
	//        |- Worker
	//        |- Trainee
	//        |- ...
	//   |- Manager02
	//        |- ...
	//   |- ...
	//   |- Worker
	//   |- Worker
	//   |- Trainee
	
	private static Manager m1;
	private static Manager m2;
	
	private static List<Employee> allEmployees; // all employees ---  i.e. Workers, Trainees and their Managers and top Director (also an instance of Manager class)
	
	@BeforeClass
    public static void setup() {
       // here comes the data 
		
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, -2);
		
		Date twoYearsAgo = c.getTime();
		
		c.setTime(now);
		c.add(Calendar.YEAR, -1);
		
		Date oneYearAgo = c.getTime();
		
		allEmployees = new ArrayList<Employee>();
		
		m1 = new Manager("Simon", "Smith", now, null, new BigDecimal("3000.00"), twoYearsAgo, new BigDecimal("50"));
		
		allEmployees.add(new Worker("Joanna", "Johnson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Sam", "Simpson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("50")));
		allEmployees.add(new Trainee("Kate", "Paul", now, m1, null, now, 365));
		
		allEmployees.add(m1);
		
		m2 = new Manager("Tom", "Thompson", now, m1, new BigDecimal("2000.00"), oneYearAgo, new BigDecimal("0"));
		
		allEmployees.add(m2);
		allEmployees.add(new Worker("Adam", "Johnson", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Erica", "Caron", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Jack", "Sparrow", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("James", "Jameson", now, m2, new BigDecimal("999.00"), now, new BigDecimal("0")));
	
    }
	
	@Test
	public void payroll() {
		List<PayrollEntry> res = HumanResourcesStatistics.payroll(allEmployees);
		
		Assert.assertEquals(res.size(), 9);
		
		for (PayrollEntry pe : res) {
			Assert.assertNotNull(pe);
			Assert.assertNotNull(pe.getSalaryPlusBonus());
		}
	}
	
	@Test
	public void subordinatesPayroll() {				
		List<PayrollEntry> res = HumanResourcesStatistics.subordinatesPayroll(m2, allEmployees);
		
		Assert.assertEquals(res.size(), 4);
		
		for (PayrollEntry pe : res) {
			Assert.assertNotNull(pe);
			Assert.assertNotNull(pe.getSalaryPlusBonus());
		}
		
		res = HumanResourcesStatistics.subordinatesPayroll(m1, allEmployees);
		
		Assert.assertEquals(res.size(), 8);
		
		for (PayrollEntry pe : res) {
			Assert.assertNotNull(pe);
			Assert.assertNotNull(pe.getSalaryPlusBonus());
		}
		
	}

	@Test
	public void bonusTotal() {
		BigDecimal total = HumanResourcesStatistics.bonusTotal(allEmployees);
		Assert.assertEquals(new BigDecimal("100"), total);
	}
	
	@Test
	public void getLongestSeniorityEmployee() {
		Employee e = HumanResourcesStatistics.getLongestSeniorityEmployee(allEmployees);
		String name = e.getFirstName() + " " + e.getSurname();
		Assert.assertEquals("Simon Smith", name);
	}
	
	@Test
	public void getTheHighestSalary() {
		BigDecimal sal = HumanResourcesStatistics.getTheHighestSalary(allEmployees);		
		Assert.assertEquals(0, sal.compareTo(new BigDecimal("3000.00")));
	}
	
	@Test
	public void getTheHighestSalaryPlusBonus() {
		BigDecimal sal = HumanResourcesStatistics.getTheHighestSalaryPlusBonus(allEmployees);
		Assert.assertEquals(0, sal.compareTo(new BigDecimal("3050.00")));
	}
	
	@Test
	public void searchSubordinatesByFirstLetter() {
		List<Employee> res = HumanResourcesStatistics.searchSubordinatesByFirstLetter(m2, allEmployees);
		Assert.assertEquals(1, res.size());
		Assert.assertNotNull(res.get(0));
	}
	
	@Test
	public void searchForEmployeesWhoEarnMoreThan() {
		List<Employee> res = HumanResourcesStatistics.searchForEmployeesWhoEarnMoreThan(new BigDecimal("1000"), allEmployees);
		Assert.assertEquals(2, res.size());	
	}
	

	/// ...
	// rest of the methods specified in the assignment description
}