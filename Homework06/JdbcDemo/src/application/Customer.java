package application;

public class Customer {
	private int customerNumber;
	private String customerName;
	private String phone;
	private int salesRepEmployeeNumber;


	public Customer(int customerNumber, String customerName, String phone, int salesRepEmployeeNumber) {
		this.customerNumber = customerNumber;
		this.customerName = customerName;
		this.phone = phone;
		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSalesRepEmployeeNumber() {
		return salesRepEmployeeNumber;
	}

	public void setSalesRepEmployeeNumber(int salesRepEmployeeNumber) {
		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
	}
}
