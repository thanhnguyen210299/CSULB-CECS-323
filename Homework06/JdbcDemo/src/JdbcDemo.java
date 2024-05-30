import application.Customer;
import application.Employee;
import db.DbCustomer;
import db.DbEmployee;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

/**
 * This program demonstrates how to connect to an Apache Derby database.
 */
public class JdbcDemo {
	public static void main(String[] args) {
		try {
			/*
			 First, we define a "connection string", which indicates what type of database we want to connect to,
			 and where to find it.
			*/
            String dbURL = "jdbc:derby:C:/CECS323/CECS323LAB";
			/*
			 This database is a Derby database at the specified absolute directory; it will not work if your
			 database is somewhere else.
			 Second, we open a Connection with that connection string. This could fail with exceptions for any number
			 of reasons: a bad connection string, a refused connection, an out-of-date driver, blah blah blah...
			*/

            Connection conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				System.out.println("Connected to database!");
			}
			else
			{
				System.out.println("Cannot connect to database!");
				return;
			}

			/*
			 We can now construct a Statement, which represents one request to the database.
			 We must define this variable outside the try, so we can close it later in the finally.
			*/
			//Statement stmt = null;

			// Ask user to enter CustomerName and ProductName
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter a Customer name:");
			String customerName = scan.nextLine();
			System.out.println("Enter a Product name: ");
			String productName = scan.nextLine();

			/*
			The CORRECT way to include user input as part of a query is to *parameterize* the query. JDBC's
			PreparedStatement class allows ? as a placeholder for parameterized values that can be added to the query.
			JDBC handles sanitizing the inputs for you.
			*/
			PreparedStatement prep = null;

			try {
				// Build the query string using the user's input for the WHERE clause.
				//String query = "select * from CECS323LAB.CUSTOMERS where CUSTOMERNAME = ?";

				String query = "select * from CECS323LAB.CUSTOMERS C " +
						"inner join CECS323LAB.ORDERS O on C.CUSTOMERNUMBER = O.CUSTOMERNUMBER " +
						"inner join CECS323LAB.ORDERDETAILS D on O.ORDERNUMBER = D.ORDERNUMBER " +
						"inner join CECS323LAB.PRODUCTS P on D.PRODUCTCODE = P.PRODUCTCODE " +
						"where CUSTOMERNAME = ? and PRODUCTNAME = ?";

				// The Connection prepares a Statement object tied to that Connection.
				prep = conn.prepareStatement(query);

				prep.setString(1, customerName); // replace the first ? with the sanitized customer's name.
				prep.setString(2, productName); // replace the second ? with the sanitized product name.

				// A Statement can execute individual query given its SQL string.
				ResultSet data = prep.executeQuery();

				/*
				 executeQuery returns a ResultSet: an Iterator-like object that "walks" through the rows returned
				 by the query. Each column is converted to an appropriate Java type, like String, Date, int, etc.
				 rs.next() moves to the next result, and returns False if there was not another result to move to.
				*/
				System.out.println("The results are: ");
				System.out.println();
				System.out.println("Format: 'Order Number: Order Date'");
				System.out.println();

				while (data.next()) {
					/*
					 The various get*() methods return the value at a given column name for the *current row*,
					 converted to the specified type.
					*/
					String orderNumber = data.getString("ORDERNUMBER");
					String orderDate = data.getString("ORDERDATE");
					System.out.println(orderNumber + ": " + orderDate);
				}

			}
			catch (SQLException e) {
				throw new Error("Problem", e);
			}
			finally {
				/*
                 Statements need to be closed after we are done with them. This is often overlooked and can lead
                 to serious performance problems, including the inability to run more statements.
                 Putting this in the finally block means the statement will always be closed, even if we encounter
                 an exception.
                */
				if (prep != null) {
					prep.close();
				}
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}