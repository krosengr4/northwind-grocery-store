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

                Employee newEmployee = new Employee(employeeID, firstName, lastName, title);

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

                employee = new Employee(employeeID, firstName, lastName, title);
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

                Employee newEmployee = new Employee(employeeID, firstName, lastName, title);
                employeesList.add(newEmployee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeesList;
    }
}
