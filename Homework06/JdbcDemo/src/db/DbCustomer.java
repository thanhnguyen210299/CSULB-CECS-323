package db;

import application.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbCustomer {
	public static List<Customer> getAllCustomers(Connection connection) {
		ArrayList<Customer> results = new ArrayList<>();
		try (Statement st = connection.createStatement()) {
			ResultSet data = st.executeQuery("select * from CECS323LAB.customers");

			while (data.next()) {
				Customer c = new Customer(data.getInt("CUSTOMERNUMBER"),
						data.getString("CUSTOMERNAME"),
						data.getString("PHONE"),
						data.getInt("SALESREPEMPLOYEENUMBER")); // will select 0 if the integer is NULL.
				results.add(c);
			}
		}
		catch (SQLException e) {

		}
		return results;
	}
}
