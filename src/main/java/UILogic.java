import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;

public class UILogic {

    static BasicDataSource dataSource = new BasicDataSource();
    static ProductDao productDao = new ProductDao(dataSource);
    static CategoryDao categoryDao = new CategoryDao(dataSource);
    static CustomerDao customerDao = new CustomerDao(dataSource);
    static EmployeeDao employeeDao = new EmployeeDao(dataSource);
    static UserInterface ui = new UserInterface();

    public static void setDataSource() {
        String password = System.getenv("SQL_PASSWORD");
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/northwind";

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
                case 0 -> continueProductsScreen = false;
                default -> System.err.println("ERROR! Please enter a number that is listed on the screen!");
            }
        }
    }

    public static void processCustomerScreen() {
        boolean continueCustomerScreen = true;

        while (continueCustomerScreen) {
            int customerScreenAction = ui.displayCustomerScreen();

            switch (customerScreenAction){
                case 1 -> processAllCustomers();
                case 2 -> processCustomerByName();
                case 3 -> processCustomerByCompany();
                case 4 -> processCustomerByCity();
                case 5 -> processCustomerByCountry();
                case 0 -> continueCustomerScreen = false;
                default -> System.err.println("ERROR! Please Enter a Number that is listed!");
            }
        }
    }

    public static void processCategoryScreen() {
        boolean continueCategoryScreen = true;

        while (continueCategoryScreen) {
            int categoryScreenAction = ui.displayCategoriesScreen();

            switch (categoryScreenAction){
                case 1 -> processAllCategories();
                case 2 -> processCategoryByName();
                case 3 -> processProductsFromCategory();
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
                case 3 -> processAddShipper();
                case 4 -> processUpdateShipper();
                case 5 -> processDeleteShipper();
                case 0 -> continueShipperScreen = false;
            }
        }
    }

    public static void processAllProducts() {
        ArrayList<NorthwindData> productsList = productDao.getAllProducts();
        printData(productsList);
    }

    public static void processProductByName() {
        String productName = Utils.promptGetUserInput("\nPlease Enter the Product Name: ").trim();
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
        double minPrice = Utils.messageAndResponseDouble("\nPlease Enter the Minimum Price: ");
        double maxPrice = Utils.messageAndResponseDouble("Please Enter the Maximum Price: ");
        String query = "SELECT * FROM products WHERE UnitPrice BETWEEN ? and ?;";

        ArrayList<NorthwindData> productsList = productDao.getProductsByPrice(query, minPrice, maxPrice);

        printData(productsList);
    }

    public static void processAllCustomers() {
        ArrayList<NorthwindData> customersList = customerDao.getAllCustomers();
        printData(customersList);
    }

    public static void processCustomerByName() {
        String customerName = Utils.promptGetUserInput("\nPlease Enter the customers First or Last Name: ").trim();
        String query = "SELECT * FROM customers where ContactName LIKE ?";

        ArrayList<NorthwindData> customerList = customerDao.getCustomersList(query, customerName);

        printData(customerList);
    }

    public static void processCustomerByCompany() {
        String companyName = Utils.promptGetUserInput("\nPlease Enter the Company Name: ").trim();
        String query = "SELECT * FROM customers WHERE CompanyName LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, companyName);

        printData(customersList);
    }

    public static void processCustomerByCity() {
        String cityName = Utils.promptGetUserInput("\nPlease Enter the name of the city: ").trim();
        String query = "SELECT * FROM customers WHERE City LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, cityName);

        printData(customersList);
    }

    public static void processCustomerByCountry() {
        String countryName = Utils.promptGetUserInput("\nPlease Enter the Name of the Country: ").trim();
        String query = "SELECT * FROM customers WHERE Country LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, countryName);

        printData(customersList);
    }

    public static void processAllCategories() {
        ArrayList<NorthwindData> categoriesList = categoryDao.getAllCategories();
        printData(categoriesList);
    }

    public static void processCategoryByName() {
        String categoryName = Utils.promptGetUserInput("Enter the name of category: ").trim();

        Category category = categoryDao.getCategory(categoryName);

        if (category == null) {
            System.out.println("There are no categories with that name...");
        } else {
            category.print();
        }

        Utils.pauseApp();
    }

    public static void processProductsFromCategory() {
        String userCatChoice = Utils.promptGetUserInput("\nSelect a CategoryID Number (1-8): ").trim();
        int userIntCatChoice = Integer.parseInt(userCatChoice);

        boolean ifRetry = true;

        while (ifRetry) {
            if (userIntCatChoice < 1 || userIntCatChoice > 8) {
                System.err.println("ERROR! We only have 8 Categories! Enter a number between 1 and 8!");
            } else {
                ArrayList<NorthwindData> productsList = productDao.getProductsFromCategory(userCatChoice);
                printData(productsList);

                ifRetry = false;
            }
        }
    }

    public static void processAllEmployees() {
        ArrayList<NorthwindData> employeesList = employeeDao.getAllEmployees();
        printData(employeesList);
    }

    public static void processEmployeeByName() {
        String employeeName = Utils.promptGetUserInput("Enter the first or last name of an employee: ").trim();
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
        String employeeTitle = Utils.promptGetUserInput("Please enter a title to search by: ").trim();
        String query = "SELECT * FROM employees WHERE Title LIKE ?";

        ArrayList<NorthwindData> employeesList = employeeDao.getEmployeesList(query, employeeTitle);
        printData(employeesList);
    }

    public static void processAllShippers() {

    }

    public static void processShipperByName() {

    }

    public static void processAddShipper() {

    }

    public static void processUpdateShipper() {

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
