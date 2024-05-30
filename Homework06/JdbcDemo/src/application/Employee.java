package application;

public class Employee {
	private int employeeNumber;
	private String lastName;
	private String firstName;

	public Employee(int employeeNumber, String lastName, String firstName) {
		this.employeeNumber = employeeNumber;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
