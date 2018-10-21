package mypackage.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class Manager extends Worker {

	// attributes
	// * subordinates (a list of immediate subordinates)
	// * all subordinates (a list of subordinates in all hierarchy)
	
	// ** by getting all subordinates I understand all the employees that are binded to this particular manager through one or more managers. 
	// ** storing all the workers in a static variable inside this class doesn't make sense to me  
	
	// ** since the class Employee holds a variable for the manager, I assume that the suggested attributes 
	// (e.g. subordinates, all subordinates) must be collected from the global list rather than stored in arrays 
	
	public Manager(String firstName, String surname, Date birthDate, Manager manager, BigDecimal salary, Date employmentDate, BigDecimal bonus) {
		super(firstName, surname, birthDate, manager, salary, employmentDate, bonus);
	}
	
	private List<Employee> getImmediateSubordinatesOf(Manager m, List<Employee> allEmployees) {
//		ArrayList foo = new ArrayList<Employee>();
//		for (Employee e : allEmployees)
//			if (e.isSubordinatedTo(m))
//				foo.add(e);
//		
//		return foo;
		
		List<Employee> res = allEmployees.stream()
				.filter(e -> e.isSubordinatedTo(m))
				.collect(Collectors.toList());
		
		return res;
	}
	
	public List<Employee> getImmediateSubordinates(List<Employee> allEmployees) {
		return getImmediateSubordinatesOf(this, allEmployees);
	}
	
	// instead of that recursion below probably flatMap could do the job but there 
	// was no such example in the lecture so I thought recursion would be fine 
	
	public ArrayList<Employee> getAllSubordinates(List<Employee> allEmployees) {
		ArrayList<Employee> foo = new ArrayList<Employee>();
		getAllSubordinatesOf(this, foo, allEmployees);
		return foo;
	}

	private void getAllSubordinatesOf(Manager m, List<Employee> res, List<Employee> allEmployees) {
		for (Employee e : m.getImmediateSubordinates(allEmployees)) {
			res.add(e);
			if (e instanceof Manager) 
				getAllSubordinatesOf((Manager) e, res, allEmployees);
		}
	}
	
}