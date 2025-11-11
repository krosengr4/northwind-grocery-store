package logic;

import models.NorthwindData;
import models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.ProductDao;
import ui.UserInterface;
import utils.Utils;

import java.util.ArrayList;

public class ProductLogic {

   static BasicDataSource dataSource = UILogic.dataSource;
   static ProductDao productDao = new ProductDao(dataSource);
   static UserInterface ui = new UserInterface();

   public static void processProductsScreen() {
	  boolean continueProductsScreen = true;

	  while(continueProductsScreen) {
		 int productsScreenAction = ui.displayProductScreen();

		 switch(productsScreenAction) {
			case 1 -> processAllProducts();
			case 2 -> processProductByName();
			case 3 -> processProductByPrice();
			case 4 -> processProductsByCategory();
			case 5 -> processAddProduct();
			case 6 -> processUpdateProduct();
			case 7 -> processDeleteProduct();
			case 0 -> continueProductsScreen = false;
			default -> System.err.println("ERROR! Please enter a number that is listed on the screen!");
		 }
	  }
   }

   public static void processAllProducts() {
	  ArrayList<NorthwindData> productsList = productDao.getAllProducts();
	  UILogic.printData(productsList);
   }

   public static void processProductByName() {
	  String productName = Utils.getUserInput("\nPlease Enter the Product Name: ").trim();
	  String query = "SELECT * FROM products WHERE ProductName LIKE ?;";

	  Product product = productDao.getProduct(query, productName);

	  if(product == null) {
		 System.out.println("There was no product found with that Name...");
	  } else {
		 System.out.println("------------------------------");
		 product.print();
	  }

	  Utils.pauseApp();
   }

   public static void processProductByPrice() {
	  double minPrice = Utils.getUserInputDouble("\nPlease Enter the Minimum Price: ");
	  double maxPrice = Utils.getUserInputDouble("Please Enter the Maximum Price: ");
	  String query = "SELECT * FROM products WHERE UnitPrice BETWEEN ? and ?;";

	  ArrayList<NorthwindData> productsList = productDao.getProductsByPrice(query, minPrice, maxPrice);

	  UILogic.printData(productsList);
   }

   public static void processProductsByCategory() {
	  CategoryLogic.processAllCategories();

	  boolean ifRetry = true;

	  while(ifRetry) {
		 String userCatChoice = Utils.getUserInput("\nSelect a CategoryID Number (1-8): ").trim();
		 int userIntCatChoice = Integer.parseInt(userCatChoice);

		 if(userIntCatChoice < 1 || userIntCatChoice > 8) {
			System.err.println("ERROR! We only have 8 Categories! Enter a number between 1 and 8!");
		 } else {
			ArrayList<NorthwindData> productsList = productDao.getProductsFromCategory(userCatChoice);
			UILogic.printData(productsList);

			ifRetry = false;
		 }
	  }
   }

   public static void processAddProduct() {
	  System.out.println("---ADD A NEW PRODUCT---");
	  String productName = Utils.getUserInput("Enter the Product Name: ");
	  int supplierID = Utils.getUserInputInt("Enter the Supplier ID: ");
	  int categoryID = Utils.getUserInputInt("Enter the Category ID: ");
	  String quantityPerUnit = Utils.getUserInput("Enter the Quantity Per Unit: ");
	  double unitPrice = Utils.getUserInputDouble("Enter the Price of 1 Unit:");
	  int unitsInStock = Utils.getUserInputInt("Enter the Units In Stock:");
	  int reorderLevel = Utils.getUserInputInt("Enter the Number of Units to Reorder At:");

	  Product product = new Product(0, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, false);
	  productDao.addAProduct(product);
   }

   public static void processUpdateProduct() {
	  System.out.println("\nPlease enter the Product ID of the product you wish to update.");
//            String productID = utils.Utils.promptGetUserInput("Enter here: ").trim();
	  int productIDInt = Utils.getUserInputInt("Enter Here: ");
	  String productID = String.valueOf(productIDInt);

	  String query = setUpdateProductQuery();

	  if(!query.equalsIgnoreCase("back")) {

		 String newValue = Utils.getUserInput("\nPlease enter the new value ");

		 productDao.updateAProduct(query, productID, newValue);
		 displayProductAfterUpdate(productID);
	  }

	  Utils.pauseApp();
   }

   public static String setUpdateProductQuery() {
	  int columnToUpdate = ui.displayUpdateProduct();
	  String query = "UPDATE products SET ";

	  switch(columnToUpdate) {
		 case 1 -> query += "ProductName = ?";
		 case 2 -> query += "SupplierID = ?";
		 case 3 -> query += "CategoryID = ?";
		 case 4 -> query += "QuantityPerUnit = ?";
		 case 5 -> query += "UnitPrice = ?";
		 case 6 -> query += "UnitsInStock = ?";
		 case 7 -> query += "UnitsOnOrder = ?";
		 case 8 -> query += "ReorderLevel = ?";
		 case 9 -> query += "Discontinued = ?";
		 case 0 -> {
			return "back";
		 }
		 default -> System.err.println("ERROR! Please Enter a number that is listed!");
	  }
	  query += " WHERE ProductID = ?;";

	  return query;
   }

   public static void displayProductAfterUpdate(String productID) {
	  String getProductQuery = "SELECT * FROM products WHERE ProductID = ?;";
	  Product product = productDao.getProduct(getProductQuery, productID);

	  if(product == null) {
		 System.err.println("ERROR! Could not get product after update!!!");
	  } else {
		 product.print();
	  }
	  Utils.pauseApp();
   }

   public static void processDeleteProduct() {
	  String password = Utils.getUserInput("Enter the password: ");
	  boolean isPassword = Utils.passwordCheck(password);

	  if(isPassword) {
		 int productId = Utils.getUserInputInt("Enter the Product ID to delete: ");
		 productDao.deleteProduct(productId);
	  } else {
		 System.out.println("That password is incorrect!");
	  }
	  Utils.pauseApp();
   }
}
