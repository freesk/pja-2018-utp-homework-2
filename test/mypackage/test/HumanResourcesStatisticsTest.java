package mypackage.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
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
	
	private Manager m1;
	private Manager m2;
	private Date twoYearsAgo;
	private Date oneYearAgo;
	
	private static List<Employee> allEmployees; // all employees ---  i.e. Workers, Trainees and their Managers and top Director (also an instance of Manager class)
	
	@Before
    public void setup() {
       // here comes the data 
		
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, -2);
		
		twoYearsAgo = c.getTime();
		
		c.setTime(now);
		c.add(Calendar.YEAR, -1);
		
		oneYearAgo = c.getTime();
		
		allEmployees = new ArrayList<Employee>();
		
		m1 = new Manager("Simon", "Smith", now, null, new BigDecimal("3000.00"), twoYearsAgo, new BigDecimal("50"));
		
		System.out.println(m1.getEmploymentDate());
		
		allEmployees.add(new Worker("Joanna", "Johnson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Sam", "Simpson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("50")));
		allEmployees.add(new Trainee("Kate", "Paul", now, m1, new BigDecimal("500.00"), now, 365));
		
		allEmployees.add(m1);
		
		m2 = new Manager("Tom", "Thompson", twoYearsAgo, m1, new BigDecimal("2000.00"), oneYearAgo, new BigDecimal("0"));
		
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
	
	@Test
	public void practiceLengthLongerThan() {
		List<Trainee> res = HumanResourcesStatistics.practiceLengthLongerThan(allEmployees, 10);
		// we know that 500 is the base, so %5 rise is 525
		final BigDecimal n = new BigDecimal("525.00");
		Assert.assertEquals(n, res.get(0).getSalary());	
	}
	
	@Test 
	public void seniorityLongerThanPlusBonus() {
		List<Employee> res = HumanResourcesStatistics.seniorityLongerThan(allEmployees, 1);

		// only two such workers
		Assert.assertEquals(res.size(), 2);	
		
		for(Employee e : res) {
			if (e instanceof Worker) {
				Worker w = (Worker) e;
				Assert.assertEquals(w.getBonus(), new BigDecimal("300"));	
			}				
		}
		
	}
	
	@Test
	public void olderThenAndEarnMore() {
		List<Employee> res = HumanResourcesStatistics.olderThenAndEarnMore(allEmployees, m1);
		// we expect just one which is m2 
		Assert.assertEquals(res.size(), 1);			
	}
	
	@Test 
	public void seniorityBetweenOneAndThreeYears() {
		List<Employee> res = HumanResourcesStatistics.seniorityBetweenOneAndThreeYears(allEmployees);
		// we expect just two 
		// System.out.println(res.size());
		Assert.assertEquals(res.size(), 2);
		// System.out.println(res.get(0).getSalary());
		Assert.assertEquals(res.get(0).getSalary(), new BigDecimal("3300.00"));
	}
	
	@Test 
	public void seniorityLongerThan() {
		
		// modify initial data for this particular case
		
		m1 = new Manager("Simon", "Smith", null, null, new BigDecimal("3000.00"), oneYearAgo, null);
		m2 = new Manager("Tom", "Thompson", null, null, new BigDecimal("2000.00"), twoYearsAgo, null);
		
		allEmployees = new ArrayList<Employee>();
		
		allEmployees.add(m1);
		allEmployees.add(m2);
		
		List<Employee> res = HumanResourcesStatistics.seniorityLongerThan(allEmployees, m1);
		
		Assert.assertEquals(m2.getSalary(), m1.getSalary());
	}
	
	@Test 
	public void seniorityBetweenTwoAndFourYearsAndAgeGreaterThan() {
		
		// modify initial data for this particular case
		
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, -3);
		
		Date threeYearsAgo = c.getTime();
		
		m1 = new Manager("Simon", "Smith", threeYearsAgo, null, new BigDecimal("3000.00"), threeYearsAgo, null);
		m2 = new Manager("Tom", "Thompson", threeYearsAgo, null, new BigDecimal("2000.00"), oneYearAgo, null);
		
		allEmployees = new ArrayList<Employee>();
		
		allEmployees.add(m1);
		allEmployees.add(m2);
		
		List<Employee> res = HumanResourcesStatistics.seniorityBetweenTwoAndFourYearsAndAgeGreaterThan(allEmployees, 1);
		
		// expect just one 
		Assert.assertEquals(res.size(), 1);
	}

}