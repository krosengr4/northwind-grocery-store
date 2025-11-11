import models.Employee;
import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sql.EmployeeDao;

import java.util.ArrayList;

class EmployeeDaoTest {

    BasicDataSource dataSource = new BasicDataSource();
    EmployeeDao employeeDao = new EmployeeDao(dataSource);

    @Test
    void getEmployee() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String employeeName = "Steven";
        String query = "SELECT * FROM employees WHERE FirstName LIKE ? OR LastName LIKE ?;";
        Employee employee = employeeDao.getEmployee(query, employeeName);

        String actual = employee.lastName;

        Assertions.assertEquals("Buchanan", actual);
    }

    @Test
    void getEmployeesList() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String title = "Sales Representative";
        String query = "SELECT * FROM employees WHERE Title LIKE ?";
        ArrayList<NorthwindData> employeeList = employeeDao.getEmployeesList(query, title);

        Employee employee = (Employee) employeeList.getFirst();
        String actual = employee.firstName;

        Assertions.assertEquals("Nancy", actual);
    }
}