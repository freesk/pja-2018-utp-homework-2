package mypackage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mypackage.employee.Employee;
import mypackage.employee.Manager;
import mypackage.employee.Trainee;
import mypackage.employee.Worker;

public class Main {
	
	public static void main(String[] args) {
		
		Date now = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, -2);
		
		Date twoYearsAgo = c.getTime();
		
		c.setTime(now);
		c.add(Calendar.YEAR, -1);
		
		Date oneYearAgo = c.getTime();
		
		
		List<Employee> allEmployees = new ArrayList<Employee>();
		
		Manager m1 = new Manager("Simon", "Smith", now, null, new BigDecimal("3000.00"), twoYearsAgo, new BigDecimal("50"));
		
		allEmployees.add(new Worker("Joanna", "Johnson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Sam", "Simpson", now, m1, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Trainee("Kate", "Paul", now, m1, null, now, 365));
		
		allEmployees.add(m1);
		
		Manager m2 = new Manager("Tom", "Thompson", now, m1, new BigDecimal("2000.00"), oneYearAgo, new BigDecimal("0"));
		
		allEmployees.add(m2);
		allEmployees.add(new Worker("John", "Johnson", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Erica", "Caron", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("Jack", "Sparrow", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
		allEmployees.add(new Worker("James", "Jameson", now, m2, new BigDecimal("1000.00"), now, new BigDecimal("0")));
	
		
		System.out.println(m1 + " -> " +  m1.getAllSubordinates(allEmployees));
		
//		System.out.println(m2 + " -> " +  m2.getImmediateSubordinates(allEmployees));
//		System.out.println(m2 + " -> " +  m2.getAllSubordinates(allEmployees));
		
//		System.out.println(HumanResourcesStatistics.payroll(allEmployees));
//		System.out.println(HumanResourcesStatistics.subordinatesPayroll(m2, allEmployees));
//		System.out.println(HumanResourcesStatistics.bonusTotal(allEmployees));
//		
//		System.out.println(m1.getEmploymentDate().before(m2.getEmploymentDate())); 
		
		
		
		
//		System.out.println(HumanResourcesStatistics.getLongestSeniorityEmployee(allEmployees));
	}

}
