package mypackage.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mypackage.employee.Employee;
import mypackage.employee.Manager;
import mypackage.employee.Trainee;
import mypackage.employee.Worker;

public class ManagerTest {
	
	private static List<Employee> allEmployees;
	private static Manager m1;
	private static Manager m2;


	@BeforeClass
	public static void setup() {
		
		allEmployees = new ArrayList<Employee>();
		
		m1 = new Manager("Simon", "Smith", null, null, new BigDecimal("3000.00"), null, new BigDecimal("50"));
		
		allEmployees.add(new Worker("Joanna", "Johnson", null, m1, new BigDecimal("1000.00"), null, new BigDecimal("0")));
		allEmployees.add(m1);
		
		m2 = new Manager("Tom", "Thompson", null, m1, new BigDecimal("2000.00"), null, new BigDecimal("0"));
		
		allEmployees.add(new Worker("Sam", "Simpson", null, m2, new BigDecimal("1000.00"), null, new BigDecimal("50")));
		allEmployees.add(new Trainee("Kate", "Paul", null, m2, null, null, 365));
		
		allEmployees.add(m2);
		
	}
	
	@Test
	public void isSalaryGreaterThanBigger() {
		BigDecimal n = new BigDecimal("8000");
		Assert.assertEquals(false, m1.isSalaryGreaterThan(n));
	}
	
	@Test
	public void isSalaryGreaterThanSmaller() {
		BigDecimal n = new BigDecimal("1000");
		Assert.assertEquals(true, m1.isSalaryGreaterThan(n));
	}
	
	@Test
	public void getAgeInYearsZeroYears() {
		Date date = new Date();
		Manager m = new Manager("", "", date, null, null, date, null);
		Assert.assertEquals(m.getAgeInYears(date), 0);
	}
	
	@Test
	public void getAgeInYears20Years() {
		Date date = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -20);
		Date birthDate = c.getTime();
		
		Manager m = new Manager("", "", birthDate, null, null, date, null);
		Assert.assertEquals(m.getAgeInYears(date), 20);
	}
	
	@Test 
	public void getImmediateSubordinates() {
		Assert.assertEquals(m1.getImmediateSubordinates(allEmployees).size(), 2);
	}
	
	@Test 
	public void getAllSubordinates() {
		Assert.assertEquals(m1.getAllSubordinates(allEmployees).size(), 4);
	}
	
	@Test 
	public void isSeniorityLongerThanYears() {
		Date date = new Date();
		
		// employment date 3 years ago 
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -2);
		Date employmentDate = c.getTime();
		
		Manager m = new Manager("", "", null, null, null, employmentDate, null);
		
		// 3 years is longer than 2
		Assert.assertEquals(m.isSeniorityLongerThanYears(1), true);
		
		// employment date 1 years ago 
		c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -1);
		employmentDate = c.getTime();
		
		m = new Manager("", "", null, null, null, employmentDate, null);
		
		// 1 years is not longer than 2
		Assert.assertEquals(m.isSeniorityLongerThanYears(2), false);
	}
	
	@Test 
	public void isSeniorityLongerThanMonths() {
		Date date = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -2);
		Date employmentDate = c.getTime();
		
		Manager m = new Manager("", "", null, null, null, employmentDate, null);
	
		Assert.assertEquals(m.isSeniorityLongerThanMonths(1), true);
		
		c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		employmentDate = c.getTime();
		
		m = new Manager("", "", null, null, null, employmentDate, null);
	
		Assert.assertEquals(m.isSeniorityLongerThanMonths(2), false);
	}

}
