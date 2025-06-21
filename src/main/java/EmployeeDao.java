import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {

    private final DataSource dataSource;

    public EmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getAllEmployees() {

        ArrayList<NorthwindData> employeesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM employees;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
			   int employeeID = results.getInt("EmployeeID");
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                String title = results.getString("Title");
//				Date birthDate = results.getDate("BirthDate");
				String notes = results.getString("Notes");

                Employee newEmployee = new Employee(employeeID, firstName, lastName, title, notes);

                employeesList.add(newEmployee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeesList;
    }

    public Employee getEmployee(String query, String userInput) {
        Employee employee = null;

        try(Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");
            prepStatement.setString(2, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
			   int employeeID = results.getInt("EmployeeID");
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                String title = results.getString("Title");
//			   Date birthDate = results.getDate("BirthDate");
			   String notes = results.getString("Notes");

                employee = new Employee(employeeID, firstName, lastName, title, notes);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

	public Employee getEmployeeByID(int employeeId) {
	   Employee employee = null;
	   String query = "SELECT * FROM employees WHERE EmployeeID = ?;";

	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setInt(1, employeeId);

		  ResultSet results = statement.executeQuery();
		  while (results.next()) {
			 String firstName = results.getString("FirstName");
			 String lastName = results.getString("LastName");
			 String title = results.getString("Title");
//			 Date birthDate = results.getDate("BirthDate");
			 String notes = results.getString("Notes");

			 employee = new Employee(employeeId, firstName, lastName, title, notes);
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	   return employee;
	}

    public ArrayList<NorthwindData> getEmployeesList(String query, String userInput) {
        ArrayList<NorthwindData> employeesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();
            while (results.next()) {
			   int employeeID = results.getInt("EmployeeID");
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                String title = results.getString("Title");
//			   Date birthDate = results.getDate("BirthDate");
			   String notes = results.getString("Notes");

                Employee newEmployee = new Employee(employeeID, firstName, lastName, title, notes);
                employeesList.add(newEmployee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeesList;
    }

	public void addEmployee(Employee employee) {
	   int employeeID = employee.employeeID;
	   String firstName = employee.firstName;
	   String lastName = employee.lastName;
	   String employeeTitle = employee.title;
	   String notes = employee.notes;

	   try(Connection connection = dataSource.getConnection()) {
		  String query = "INSERT INTO employees (FirstName, LastName, Title, Notes) " +
								 "VALUES (?, ?, ?, ?);";
		  PreparedStatement statement = connection.prepareStatement(query);
		  statement.setString(1, firstName);
		  statement.setString(2, lastName);
		  statement.setString(3, employeeTitle);
		  statement.setString(4, notes);

		  int rows = statement.executeUpdate();
		  if(rows != 0) {
			 System.out.println("Success! The new employee has been added!");
		  } else {
			 System.err.println("ERROR! The new employee has not been added!!!");
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}

	public void updateEmployee(String query, int employeeId, String newValue) {
	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setString(1, newValue);
		  statement.setInt(2, employeeId);

		  int rows = statement.executeUpdate();
		  if (rows != 0) {
			 System.out.println("Success! Employee successfully updated!");
		  } else {
			 System.err.println("ERROR! The Employee Information was not updated!");
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}

	public void deleteEmployee(int employeeId) {
	   String query = "DELETE FROM employees WHERE EmployeeID = ?;";

	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setInt(1, employeeId);

		  int rows = statement.executeUpdate();
		  if (rows != 0) {
			 System.out.println("Success! Employee was deleted!");
		  } else {
			 System.err.println("ERROR! Could not delete employee!");
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}
}
