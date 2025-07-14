public class UserInterface {

    public int displayHomeScreen() {
        System.out.println("\n___________________NORTHWIND GROCERIES___________________");
        System.out.println("-----OPTIONS-----");

        System.out.println("""
                1 - View Products Screen
                2 - View Customers Screen
                3 - View Categories Screen
                4 - View Employees Screen
                5 - View Shippers Screen
                0 - Exit
                """);

        return Utils.getUserInputInt("Please select an option: ");
    }

    public int displayProductScreen() {
        System.out.println("\n____________PRODUCTS SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - See All Products
                2 - Search Product By Name
                3 - Search Products By Price Range
                4 - Search Products By Category
                5 - Add A New Product
                6 - Update an Existing Product
                7 - Remove A Product (requires password)
                0 - Go Back
                """);

        return Utils.getUserInputInt("Enter your option: ");
    }

    public int displayCustomerScreen() {
        System.out.println("\n____________CUSTOMERS SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                    1 - See All Customers
                    2 - Search Customers by Contact Name
                    3 - Search Customers by Company Name
                    4 - Search Customers by City
                    5 - Search Customers by Country
                    6 - Add A New Customer
                    7 - Update an Existing Customer
                    8 - Remove A Customer (requires password)
                    0 - Go Back""");

        return Utils.getUserInputInt("Enter your option: ");
    }

    public int displayCategoriesScreen() {
        System.out.println("\n____________CATEGORIES SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - See All Categories
                2 - Search Category By Name
                3 - Add A New Category
                4 - Update an Existing Category
                5 - Remove A Category (requires password)
                0 - Go Back
                """);

        return Utils.getUserInputInt("Enter your option: ");
    }

    public int displayEmployeeScreen() {
        System.out.println("\n____________EMPLOYEES SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - See All Employees
                2 - Search Employee By Name
                3 - Search Employees by Title
                4 - Add A New Employee
                5 - Update an Existing Employee
                6 - Remove an Employee (requires password)
                0 - Go Back
                """);

        return Utils.getUserInputInt("Enter your option: ");
    }

    public int displayShippersScreen() {
        System.out.println("\n____________SHIPPER SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - View All Shippers
                2 - Search Shippers by Name
                3 - Search Shipper by Phone
                4 - Add a New Shipper
                5 - Update an Existing Shipper
                6 - Remove a Shipper (requires password)
                0 - Go back
                """);

        return Utils.getUserInputInt("Enter your option: ");
    }

    public int displayUpdateProduct() {
        System.out.println("\nPlease choose which column you would like to update.");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - Update Product Name
                2 - Update Supplier ID
                3 - Update Category ID
                4 - Update Quantity Per Unit
                5 - Update Unit Price
                6 - Update Units in Stock
                7 - Update Units on Order
                8 - Update Reorder Level
                9 - Update if product is discontinued
                0 - Go Back
                """);

        return Utils.getUserInputIntMinMax("Enter your option: ", 0, 9);
    }

    public int displayUpdateCustomer() {
        System.out.println("\nPlease choose which column you would like to update.");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - Update Company Name
                2 - Update Contact Name
                3 - Update Contact Title
                4 - Update Address
                5 - Update Region
                6 - Update Postal Code
                7 - Update City
                8 - Update Country
                9  - Update Phone Number
                10 - Update Fax
                0 - Go Back
                """);

        return Utils.getUserInputIntMinMax("Enter your option: ", 0, 10);
    }

    public int displayUpdateCategory() {
      System.out.println("\nPlease choose which column you would like to update.");
      System.out.println("-----OPTIONS-----");
      System.out.println("""
              1 - Update Category Name
              2 - Update Description
              0 - Go Back
              """);

      return Utils.getUserInputIntMinMax("Enter your option: ", 0, 2);
    }

	//! Add new fields that a user can update!
	public int displayUpdateEmployee() {
	   System.out.println("\nPlease choose which column you would like to update.");
	   System.out.println("-----OPTIONS-----");
	   System.out.println("""
						1 - Update First Name
						2 - Update Last Name
						3 - Update Title
						0 - Go Back
						""");
	   return Utils.getUserInputInt("Enter your option: ");
	}

	public int displayUpdateShipper() {
	   System.out.println("\nPlease choose which column you would like to update.");
	   System.out.println("-----OPTIONS-----");
	   System.out.println("""
						1 - Update Company Name
						2 - Update Phone Number
						0 - Go Back
						""");
	   return Utils.getUserInputInt("Enter your option: ");
	}

}
