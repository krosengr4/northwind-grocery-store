import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;

public class UILogic {

    static BasicDataSource dataSource = new BasicDataSource();
    static ProductDao productDao = new ProductDao(dataSource);
    static CategoryDao categoryDao = new CategoryDao(dataSource);
    static CustomerDao customerDao = new CustomerDao(dataSource);
    static EmployeeDao employeeDao = new EmployeeDao(dataSource);
    static ShipperDao shipperDao = new ShipperDao(dataSource);
    static UserInterface ui = new UserInterface();

    static String url = "jdbc:mysql://localhost:3306/northwind";
    static String userName = "root";
    static String password = System.getenv("SQL_PASSWORD");

    public static void setDataSource() {
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }

    public static void processHomeScreen() {
        boolean ifContinue = true;

        while (ifContinue) {
            int userHomeAction = ui.displayHomeScreen();

            switch (userHomeAction) {
                case 1 -> processProductsScreen();
                case 2 -> processCustomerScreen();
                case 3 -> processCategoryScreen();
                case 4 -> processEmployeeScreen();
                case 5 -> processShippersScreen();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }
        }
    }

    public static void processProductsScreen() {
        boolean continueProductsScreen = true;

        while (continueProductsScreen) {
            int productsScreenAction = ui.displayProductScreen();

            switch (productsScreenAction) {
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

    public static void processCustomerScreen() {
        boolean continueCustomerScreen = true;

        while (continueCustomerScreen) {
            int customerScreenAction = ui.displayCustomerScreen();

            switch (customerScreenAction) {
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

    public static void processCategoryScreen() {
        boolean continueCategoryScreen = true;

        while (continueCategoryScreen) {
            int categoryScreenAction = ui.displayCategoriesScreen();

            switch (categoryScreenAction) {
                case 1 -> processAllCategories();
                case 2 -> processCategoryByName();
                case 3 -> processAddCategory();
                case 4 -> processUpdateCategory();
                case 5 -> processDeleteCategory();
                case 0 -> continueCategoryScreen = false;
                default -> System.err.println("ERROR! Please Enter a number that is listed!");
            }
        }
    }

    public static void processEmployeeScreen() {
        boolean continueEmployeeScreen = true;

        while (continueEmployeeScreen) {
            int employeeScreenAction = ui.displayEmployeeScreen();

            switch (employeeScreenAction) {
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

    public static void processShippersScreen() {
        boolean continueShipperScreen = true;

        while (continueShipperScreen) {
            int shipperScreenAction = ui.displayShippersScreen();

            switch (shipperScreenAction) {
                case 1 -> processAllShippers();
                case 2 -> processShipperByName();
                case 3 -> processShipperByPhone();
                case 4 -> processAddShipper();
                case 5 -> processUpdateShipper();
                case 6 -> processDeleteShipper();
                case 0 -> continueShipperScreen = false;
            }
        }
    }

    public static void processAllProducts() {
        ArrayList<NorthwindData> productsList = productDao.getAllProducts();
        printData(productsList);
    }

    public static void processProductByName() {
        String productName = Utils.getUserInput("\nPlease Enter the Product Name: ").trim();
        String query = "SELECT * FROM products WHERE ProductName LIKE ?;";

        Product product = productDao.getProduct(query, productName);

        if (product == null) {
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

        printData(productsList);
    }

    public static void processProductsByCategory() {
        processAllCategories();

        boolean ifRetry = true;

        while (ifRetry) {
            String userCatChoice = Utils.getUserInput("\nSelect a CategoryID Number (1-8): ").trim();
            int userIntCatChoice = Integer.parseInt(userCatChoice);

            if (userIntCatChoice < 1 || userIntCatChoice > 8) {
                System.err.println("ERROR! We only have 8 Categories! Enter a number between 1 and 8!");
            } else {
                ArrayList<NorthwindData> productsList = productDao.getProductsFromCategory(userCatChoice);
                printData(productsList);

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
        Product product = new Product(0, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, 0);

        productDao.addAProduct(product);
    }

    public static void processUpdateProduct() {
        System.out.println("\nPlease enter the Product ID of the product you wish to update.");
//            String productID = Utils.promptGetUserInput("Enter here: ").trim();
        int productIDInt = Utils.getUserInputInt("Enter Here: ");
        String productID = String.valueOf(productIDInt);

        String query = setUpdateProductQuery();

        if (!query.equalsIgnoreCase("back")) {

            String newValue = Utils.getUserInput("\nPlease enter the new value ");

            //! Fix Error "Data truncation: Data too long for column 'Discontinued' at row 1"
            productDao.updateAProduct(query, productID, newValue);
            displayProductAfterUpdate(productID);
        }

        Utils.pauseApp();
    }

    public static String setUpdateProductQuery() {
        int columnToUpdate = ui.displayUpdateProduct();
        String query = "UPDATE products SET ";

        switch (columnToUpdate) {
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

        if (product == null) {
            System.err.println("ERROR! Could not get product after update!!!");
        } else {
            product.print();
        }
        Utils.pauseApp();
    }

    public static void processDeleteProduct() {
        //todo create a method to check a password
    }

    public static void processAllCustomers() {
        ArrayList<NorthwindData> customersList = customerDao.getAllCustomers();
        printData(customersList);
    }

    public static void processCustomerByName() {
        String customerName = Utils.getUserInput("\nPlease Enter the customers First or Last Name: ").trim();
        String query = "SELECT * FROM customers where ContactName LIKE ?";

        ArrayList<NorthwindData> customerList = customerDao.getCustomersList(query, customerName);

        printData(customerList);
    }

    public static void processCustomerByCompany() {
        String companyName = Utils.getUserInput("\nPlease Enter the Company Name: ").trim();
        String query = "SELECT * FROM customers WHERE CompanyName LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, companyName);

        printData(customersList);
    }

    public static void processCustomerByCity() {
        String cityName = Utils.getUserInput("\nPlease Enter the name of the city: ").trim();
        String query = "SELECT * FROM customers WHERE City LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, cityName);

        printData(customersList);
    }

    public static void processCustomerByCountry() {
        String countryName = Utils.getUserInput("\nPlease Enter the Name of the Country: ").trim();
        String query = "SELECT * FROM customers WHERE Country LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, countryName);

        printData(customersList);
    }

    public static void processAddCustomer() {
        //Add customer
    }

    public static void processUpdateCustomer() {
        System.out.println("\nPlease Enter the Customer ID of the customer you wish to update.");
        String customerID = Utils.getUserInput("Enter Here: ");

        String query = setCustomerUpdateQuery();

        if (!query.equalsIgnoreCase("back")) {
            String newValue = Utils.getUserInput("Enter the New Value: ");

            customerDao.updateCustomer(query, customerID, newValue);
            displayCustomerAfterUpdate(customerID);
        }
    }

    private static void displayCustomerAfterUpdate(String customerID) {
        String query = "SELECT * FROM customers WHERE CustomerID = ?;";
        Customer customer = customerDao.getCustomer(query, customerID);

        if (customer == null) {
            System.err.println("Error! We could not get the customer after the update...");
        } else {
            customer.print();
        }
        Utils.pauseApp();
    }

    private static String setCustomerUpdateQuery() {
        int columnToUpdate = ui.displayUpdateCustomer();
        String query = "UPDATE customers SET ";

        switch (columnToUpdate) {
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
        //todo create a method to check a password
    }

    public static void processAllCategories() {
        ArrayList<NorthwindData> categoriesList = categoryDao.getAllCategories();
        printData(categoriesList);
    }

    public static void processCategoryByName() {
        String categoryName = Utils.getUserInput("Enter the name of category: ").trim();

        Category category = categoryDao.getCategory(categoryName);

        if (category == null) {
            System.out.println("There are no categories with that name...");
        } else {
            category.print();
        }

        Utils.pauseApp();
    }

    public static void processAddCategory() {
        //add a category
    }

    public static void processUpdateCategory() {
        //Update category
    }

    public static void processDeleteCategory() {
        //todo create a method to check a password
    }

    public static void processAllEmployees() {
        ArrayList<NorthwindData> employeesList = employeeDao.getAllEmployees();
        printData(employeesList);
    }

    public static void processEmployeeByName() {
        String employeeName = Utils.getUserInput("Enter the first or last name of an employee: ").trim();
        String query = "SELECT * FROM employees WHERE FirstName LIKE ? OR LastName LIKE ?;";

        Employee employee = employeeDao.getEmployee(query, employeeName);

        if (employee == null) {
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
        printData(employeesList);
    }

    public static void processAddEmployee() {
        //Add employee
    }

    public static void processUpdateEmployee() {
        //Update employee
    }

    public static void processDeleteEmployee() {
        //todo create a method to check a password
    }

    public static void processAllShippers() {
        ArrayList<NorthwindData> shipperList = shipperDao.getAllShippers();
        printData(shipperList);
    }

    public static void processShipperByName() {
        String companyName = Utils.getUserInput("Please Enter the Company Name of the Shipper: ");
        String query = "SELECT * FROM shippers WHERE CompanyName LIKE ?";

        Shipper shipper = shipperDao.getShipper(query, companyName);
        if (shipper == null) {
            System.out.println("There are no shippers with that name...");
        } else {
            shipper.print();
        }

        Utils.pauseApp();
    }

    public static void processShipperByPhone() {
        System.out.println("Please enter the phone number of the shipper like '(XXX) xxx-xxxx'");
        String phoneNumber = Utils.getUserInput("Enter here: ");
        String query = "SELECT * FROM shippers WHERE Phone LIKE ?";

        Shipper shipper = shipperDao.getShipper(query, phoneNumber);
        if (shipper == null) {
            System.out.println("There are no shippers with that phone number...");
        } else {
            shipper.print();
        }

        Utils.pauseApp();
    }

    public static void processAddShipper() {
        //Add a shipper
    }

    public static void processUpdateShipper() {
        //Update a shipper
    }

    public static void processDeleteShipper() {
        //todo Add a method that will check password
    }

    public static void printData(ArrayList<NorthwindData> northwindDataList) {
        if (northwindDataList.isEmpty()) {
            System.out.println("There is no data to display...");
        } else {
            for (NorthwindData column : northwindDataList) {
                column.print();
                System.out.println("-------------------------------------------------------------");
            }

            System.out.println("\nColumns Returned: " + northwindDataList.size());
        }

        Utils.pauseApp();
    }


}
