package logic;

import models.Employee;
import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.EmployeeDao;
import ui.UserInterface;
import utils.Utils;

import java.util.ArrayList;

public class EmployeeLogic {

   static BasicDataSource dataSource = UILogic.dataSource;
   static EmployeeDao employeeDao = new EmployeeDao(dataSource);
   static UserInterface ui = new UserInterface();

   public static void processEmployeeScreen() {
	  boolean continueEmployeeScreen = true;

	  while(continueEmployeeScreen) {
		 int employeeScreenAction = ui.displayEmployeeScreen();

		 switch(employeeScreenAction) {
			case 1 -> processAllEmployees();
			case 2 -> processEmployeeByName();
			case 3 -> processEmployeeByTitle();
			case 4 -> processAddEmployee();
			case 5 -> processUpdateEmployee();
			case 6 -> processDeleteEmployee();
			case 0 -> continueEmployeeScreen = false;
			default -> System.err.println("ERROR! Please Enter a number that is listed!");
		 }
	  }
   }

   public static void processAllEmployees() {
	  ArrayList<NorthwindData> employeesList = employeeDao.getAllEmployees();
	  UILogic.printData(employeesList);
   }

   public static void processEmployeeByName() {
	  String employeeName = Utils.getUserInput("Enter the first or last name of an employee: ").trim();
	  String query = "SELECT * FROM employees WHERE FirstName LIKE ? OR LastName LIKE ?;";

	  Employee employee = employeeDao.getEmployee(query, employeeName);

	  if(employee == null) {
		 System.out.println("There are no employees with that name...");
	  } else {
		 employee.print();
	  }

	  Utils.pauseApp();
   }

   public static void processEmployeeByTitle() {
	  String employeeTitle = Utils.getUserInput("Please enter a title to search by: ").trim();
	  String query = "SELECT * FROM employees WHERE Title LIKE ?";

	  ArrayList<NorthwindData> employeesList = employeeDao.getEmployeesList(query, employeeTitle);
	  UILogic.printData(employeesList);
   }

   public static void processAddEmployee() {
	  String firstName = Utils.getUserInput("Enter the employees first name: ");
	  String lastName = Utils.getUserInput("Enter the employees last name: ");
	  String title = Utils.getUserInput("Enter the employees title: ");
	  String address = Utils.getUserInput("Enter the employee's address: ");
	  String city = Utils.getUserInput("Enter the employee city: ");
	  String country = Utils.getUserInput("Enter the employee's country: ");
	  String postalCode = Utils.getUserInput("Enter the employee's postal code: ");
	  String notes = Utils.getUserInput("Please enter any notes (if none, enter 'none'): ");


	  Employee employee = new Employee(0, firstName, lastName, title, address, city, country, postalCode, notes);
	  employeeDao.addEmployee(employee);
   }

   public static void processUpdateEmployee() {
	  System.out.println("Please enter the Employee ID of the employee you wish to update.");
	  int employeeID = Utils.getUserInputInt("Enter here: ");

	  String query = setEmployeeUpdateQuery();

	  if(!query.equals("back")) {
		 String newValue = Utils.getUserInput("Please enter the new value: ");

		 employeeDao.updateEmployee(query, employeeID, newValue);
		 displayEmployeeAfterUpdate(employeeID);
	  }

   }

   private static String setEmployeeUpdateQuery() {
	  int columnToUpdate = ui.displayUpdateEmployee();
	  String query = "UPDATE employees SET ";

	  switch(columnToUpdate) {
		 case 1 -> query += "FirstName = ?";
		 case 2 -> query += "LastName = ?";
		 case 3 -> query += "Title = ?";
		 case 0 -> {
			return "back";
		 }
	  }
	  query += " WHERE EmployeeID = ?;";

	  return query;
   }

   private static void displayEmployeeAfterUpdate(int employeeID) {
	  Employee employee = employeeDao.getEmployeeByID(employeeID);

	  if(employee != null) {
		 employee.print();
	  } else {
		 System.err.println("Error! Could not find employee with that ID!");
	  }
   }

   public static void processDeleteEmployee() {
	  String password = Utils.getUserInput("Enter the password: ");
	  boolean isCorrect = Utils.passwordCheck(password);

	  if(isCorrect) {
		 int employeeId = Utils.getUserInputInt("Enter the Employee ID to delete: ");
		 employeeDao.deleteEmployee(employeeId);
	  } else {
		 System.out.println("That password is incorrect!!!");
	  }
	  Utils.pauseApp();
   }

}
