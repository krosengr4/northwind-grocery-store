import models.Customer;
import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.CustomerDao;
import utils.Utils;

import java.util.ArrayList;

public class CustomerLogic {

   static BasicDataSource dataSource = UILogic.dataSource;
   static CustomerDao customerDao = new CustomerDao(dataSource);
   static UserInterface ui = new UserInterface();

   public static void processCustomerScreen() {
	  boolean continueCustomerScreen = true;

	  while(continueCustomerScreen) {
		 int customerScreenAction = ui.displayCustomerScreen();

		 switch(customerScreenAction) {
			case 1 -> processAllCustomers();
			case 2 -> processCustomerByName();
			case 3 -> processCustomerByCompany();
			case 4 -> processCustomerByCity();
			case 5 -> processCustomerByCountry();
			case 6 -> processAddCustomer();
			case 7 -> processUpdateCustomer();
			case 8 -> processDeleteCustomer();
			case 0 -> continueCustomerScreen = false;
			default -> System.err.println("ERROR! Please Enter a Number that is listed!");
		 }
	  }
   }

   public static void processAllCustomers() {
	  ArrayList<NorthwindData> customersList = customerDao.getAllCustomers();
	  UILogic.printData(customersList);
   }

   public static void processCustomerByName() {
	  String customerName = Utils.getUserInput("\nPlease Enter the customers First or Last Name: ").trim();
	  String query = "SELECT * FROM customers where ContactName LIKE ?";

	  ArrayList<NorthwindData> customerList = customerDao.getCustomersList(query, customerName);

	  UILogic.printData(customerList);
   }

   public static void processCustomerByCompany() {
	  String companyName = Utils.getUserInput("\nPlease Enter the Company Name: ").trim();
	  String query = "SELECT * FROM customers WHERE CompanyName LIKE ?";

	  ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, companyName);

	  UILogic.printData(customersList);
   }

   public static void processCustomerByCity() {
	  String cityName = Utils.getUserInput("\nPlease Enter the name of the city: ").trim();
	  String query = "SELECT * FROM customers WHERE City LIKE ?";

	  ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, cityName);

	  UILogic.printData(customersList);
   }

   public static void processCustomerByCountry() {
	  String countryName = Utils.getUserInput("\nPlease Enter the Name of the Country: ").trim();
	  String query = "SELECT * FROM customers WHERE Country LIKE ?";

	  ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, countryName);

	  UILogic.printData(customersList);
   }

   public static void processAddCustomer() {
	  System.out.println("\n---ADD NEW CUSTOMER---");
	  String companyName = Utils.getUserInput("Enter the Company Name: ");
	  String contactName = Utils.getUserInput("Enter the Contact Name: ");
	  String contactTitle = Utils.getUserInput("Enter the Contact Title: ");
	  String address = Utils.getUserInput("Enter the Address: ");
	  String region = Utils.getUserInput("Enter the Region: ");
	  String postalCode = Utils.getUserInput("Enter the Postal Code: ");
	  String city = Utils.getUserInput("Enter the models.Customer's City: ");
	  String country = Utils.getUserInput("Enter the models.Customer's Country: ");
	  String phoneNumber = Utils.getUserInput("Enter the models.Customer's Phone Number: ");
	  String fax = Utils.getUserInput("Enter the models.Customer's Phone Number: ");

	  Customer customer = new Customer(0, companyName, contactName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);
	  customerDao.addCustomer(customer);
   }

   public static void processUpdateCustomer() {
	  System.out.println("\nPlease Enter the models.Customer ID of the customer you wish to update.");
	  String customerID = Utils.getUserInput("Enter Here: ");

	  String query = setCustomerUpdateQuery();

	  if(!query.equalsIgnoreCase("back")) {
		 String newValue = Utils.getUserInput("Enter the New Value: ");

		 customerDao.updateCustomer(query, customerID, newValue);
		 displayCustomerAfterUpdate(customerID);
	  }
   }

   private static void displayCustomerAfterUpdate(String customerID) {
	  String query = "SELECT * FROM customers WHERE CustomerID = ?;";
	  Customer customer = customerDao.getCustomer(query, customerID);

	  if(customer == null) {
		 System.err.println("Error! We could not get the customer after the update...");
	  } else {
		 customer.print();
	  }
	  Utils.pauseApp();
   }

   private static String setCustomerUpdateQuery() {
	  int columnToUpdate = ui.displayUpdateCustomer();
	  String query = "UPDATE customers SET ";

	  switch(columnToUpdate) {
		 case 1 -> query += "CompanyName = ?";
		 case 2 -> query += "ContactName = ?";
		 case 3 -> query += "ContactTitle = ?";
		 case 4 -> query += "Address = ?";
		 case 5 -> query += "Region = ?";
		 case 6 -> query += "PostalCode = ?";
		 case 7 -> query += "City = ?";
		 case 8 -> query += "Country = ?";
		 case 9 -> query += "Phone = ?";
		 case 10 -> query += "Fax = ?";
		 case 0 -> {
			return "back";
		 }
		 default -> System.out.println("ERROR! Please Select One of the listed number!");
	  }

	  query += " WHERE CustomerID = ?;";

	  return query;
   }

   public static void processDeleteCustomer() {
	  String password = Utils.getUserInput("Enter the password: ");
	  boolean isCorrect = Utils.passwordCheck(password);

	  if(isCorrect) {
		 int customerId = Utils.getUserInputInt("Enter the models.Customer ID to delete: ");
		 customerDao.deleteCustomer(customerId);
	  } else {
		 System.out.println("That password is incorrect!");
	  }
	  Utils.pauseApp();
   }
}
