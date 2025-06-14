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
                case 2 -> processProductsByID();
                case 3 -> processProductByName();
                case 4 -> processProductByPrice();
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
    }

    public static void processCustomerByName() {
    }

    public static void processCustomerByCompany() {
    }

    public static void processCustomerByCity() {
    }

    public static void processCustomerByCountry() {
    }

    public static void processAllCategories() {
    }

    public static void processCategoryByName() {
    }

    public static void processProductsFromCategory() {
    }

    public static void processAllEmployees() {
    }

    public static void processEmployeeByName() {
    }

    public static void processEmployeeByTitle() {
    }

    public static void printData(ArrayList<NorthwindData> northwindDataList) {
    }


}
