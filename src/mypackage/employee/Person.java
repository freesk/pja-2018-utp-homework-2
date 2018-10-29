package mypackage.employee;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Person {

	// To implement an attribute means that you provide a backing field and
	// getter or optionally setter for read-write properties/attributes
	// 
	// NO BACKING FIELDS SHOULD BE PROVIDED FOR DERIVED ATTRIBUTES
	// THOSE SHOULD BE COMPUTED ON-LINE
	//
	// attributes:
	// * first name (read-only)
	// * surname (read-only)
	// * birth date (read-only) --- date MUST BE represented by an instance of
	// type designed for date representation (either Date or LocalDate)
	//
	// * age (derived --- computed based on birth date) --- implemented as a
	// getter calculating the difference between the current date and birth date

	// (assignment 02)
	// attributes:
	// * first name
	// * surname
	// * birth date
	// * age (derived --- computed based on birth date)
	
	// (assignment 03)
	// methods:
	// * is older than other person
	// * is younger than other person
	// * compare age with other person's age
	
	private final String _firstName; // backing field
	private final String _surname;
	private final Date _birthDate; 
	
	public static int counter = 0;
	
	protected Person(String firstName, String surname, Date birthDate) {
		_firstName = firstName;
		_surname = surname;
		_birthDate = birthDate;
		counter++;
	}
	
	public boolean isOlderThan(Person p) {
		return _birthDate.before(p.getBirthDate());
	}
	
	public boolean isYoungerThan(Person p) {
		return _birthDate.after(p.getBirthDate());
	}
	
	public void compareAgeTo(Person p) {
		System.out.println(_birthDate.compareTo(p.getBirthDate()));
	}
	
	public int getAgeInYears(Date currentDate) {
		long ageInYearsLong = getAgeInDays(currentDate) / 365L;
		int ageInYearsInt = (int) (long) ageInYearsLong;
	    return ageInYearsInt;
	}
	
	private long getAgeInDays(Date currentDate) {
		long diffInMs = Math.abs(currentDate.getTime() - _birthDate.getTime());
	    long diffInDays = TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS);
		return diffInDays;
	}
	
	public Date getBirthDate() {
		return _birthDate;
	}
	
	public String getSurname() {
		return _surname;
	}

	public String getFirstName() { // getter
		return _firstName;
	}
	
	@Override
	public String toString() {
		return _firstName + " " + _surname;
	}
}