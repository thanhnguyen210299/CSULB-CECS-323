package db;

import application.Employee;

import java.sql.*;

public class DbEmployee {
	public static Employee getEmployee(Connection connection, int employeeId) {
		try (PreparedStatement st = connection.prepareStatement("select * from CECS323LAB.employees where EMPLOYEENUMBER = ?")) {
			st.setInt(1, employeeId); // 1-based indices :(
			ResultSet data = st.executeQuery();

			if (data.next()) {
				Employee emp = new Employee(data.getInt("EMPLOYEENUMBER"),
						data.getString("LASTNAME"),
						data.getString("FIRSTNAME"));
				return emp;
			}
		}
		catch (SQLException e) {

		}
		return null;
	}
}
