package logic;

import models.Customer;
import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.CustomerDao;
import ui.UserInterface;
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
	  String city = Utils.getUserInput("Enter the Customer's City: ");
	  String country = Utils.getUserInput("Enter the Customer's Country: ");
	  String phoneNumber = Utils.getUserInput("Enter the Customer's Phone Number: ");
	  String fax = Utils.getUserInput("Enter the Customer's Phone Number: ");

	  Customer customer = new Customer(0, companyName, contactName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);
	  customerDao.addCustomer(customer);
   }

   public static void processUpdateCustomer() {
	  System.out.println("\nPlease Enter the Customer ID of the customer you wish to update.");
	  int customerID = Utils.getUserInputInt("Enter Here: ");

	  Customer customer = customerDao.getCustomerById(customerID);
	  if (customer == null) {
		  System.out.println("There are no customers with that id...");
	  } else {
		  Customer updatedCustomer = customerUpdateField(customer);
		  customerDao.updateCustomer(updatedCustomer, customerID);
	  }
   }


   private static Customer customerUpdateField(Customer customer) {
	   int userChoice = ui.displayUpdateCustomer();
	   switch(userChoice) {
		   case 1: {
			   String newCompanyName = Utils.getUserInput("Enter the new company name: ");
			   customer.setCompanyName(newCompanyName);
			   return customer;
		   }
		   case 2: {
			   String newContactName = Utils.getUserInput("Enter the new contact name: ");
			   customer.setContactName(newContactName);
			   return customer;
		   }
		   case 3: {
			   String newContactTitle = Utils.getUserInput("Enter the new contact title: ");
			   customer.setContactTitle(newContactTitle);
			   return customer;
		   }
		   case 4: {
			   String newAddress = Utils.getUserInput("Enter the new address: ");
			   customer.setAddress(newAddress);
			   return customer;
		   }
		   case 5: {
			   String newRegion = Utils.getUserInput("Enter the new region: ");
			   customer.setRegion(newRegion);
			   return customer;
		   }
		   case 6: {
			   String newPostal = Utils.getUserInput("Enter the new postal code: ");
			   customer.setPostalCode(newPostal);
			   return customer;
		   }
		   case 7: {
			   String city = Utils.getUserInput("Enter the new city: ");
			   customer.setCity(city);
			   return customer;
		   }
		   case 8: {
			   String country = Utils.getUserInput("Enter the new country: ");
			   customer.setCountry(country);
			   return customer;
		   }
		   case 9: {
			   String phoneNumber = Utils.getUserInput("Enter the new phone number: ");
			   customer.setPhoneNumber(phoneNumber);
			   return customer;
		   }
		   case 10: {
			   String fax = Utils.getUserInput("Enter the new fax: ");
			   customer.setFax(fax);
			   return customer;
		   }
		   default: {
			   System.err.println("ERROR! Something went wrong with your selection.");
		   }
	   }
	   return customer;
   }

   public static void processDeleteCustomer() {
	  String password = Utils.getUserInput("Enter the password: ");
	  boolean isCorrect = Utils.passwordCheck(password);

	  if(isCorrect) {
		 int customerId = Utils.getUserInputInt("Enter the Customer ID to delete: ");
		 customerDao.deleteCustomer(customerId);
	  } else {
		 System.out.println("That password is incorrect!");
	  }
	  Utils.pauseApp();
   }
}
