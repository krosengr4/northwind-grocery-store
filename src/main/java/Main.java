import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;

public class Main {

    static BasicDataSource dataSource = new BasicDataSource();
    static ProductDao productDao = new ProductDao(dataSource);
    static CategoryDao categoryDao = new CategoryDao(dataSource);
    static CustomerDao customerDao = new CustomerDao(dataSource);
    static EmployeeDao employeeDao = new EmployeeDao(dataSource);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {

            System.out.println("\n___________________NORTHWIND GROCERIES___________________");
            System.out.println("-----OPTIONS-----");
            System.out.println("1 - View Products Screen\n2 - View Customers Screen\n3 - View Categories Screen\n4 - View Employees Screen\n0 - Exit");
//            System.out.println("1 - Display All Products\n2 - Display All Customers\n3 - Display All Categories\n4 - Display All Employees\n0 - Exit");
            int userQueryChoice = Utils.messageAndResponseInt("Please select an option: ");

            switch (userQueryChoice) {
                case 1 -> displayProductsScreen();
                case 2 -> processAllCustomers();
                case 3 -> processAllCategories();
                case 4 -> processAllEmployees();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }
        }

        System.out.println("\n\nHave a Nice Day! :)");
    }

    public static void setDataSource() {
        String password = System.getenv("SQL_PASSWORD");
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/northwind";

        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }

    public static void displayProductsScreen() {

        boolean ifContinueProductScreen = true;

        while (ifContinueProductScreen) {
            System.out.println("\n____________PRODUCTS SCREEN____________");
            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See All Products\n2 - Search Product By ID\n3 - Search Product By Name\n4 - Search Products By Price Range\n0 - Go Back");
            int productScreenChoice = Utils.messageAndResponseInt("Enter your option: ");

            switch (productScreenChoice) {
                case 1 -> processAllProducts();
                case 2 -> processProductByID();
                case 3 -> processProductByName();
                case 4 -> processProductByPrice();
                case 0 -> ifContinueProductScreen = false;
                default -> System.err.println("ERROR! Please enter a number that is listed on the screen!");
            }
        }
    }

    public static void displayCustomerScreen() {
        boolean ifContinueCustomerScreen = true;

        while (ifContinueCustomerScreen) {
            System.out.println("\n____________CUSTOMERS SCREEN____________");
            System.out.println("-----OPTIONS-----");
            System.out.println("""
                    1 - See All Customers
                    2 - Search Customers by Contact Name
                    4 - Search Customers by Company Name
                    5 - Search Customers by City
                    6 - Search Customers by Country
                    0 - Go Back""");
            int customerScreenChoice = Utils.messageAndResponseInt("Enter your option: ");
        }
    }

    public static void displayCategoriesScreen() {
        boolean ifContinueCategoryScreen = true;

        while (ifContinueCategoryScreen) {
            System.out.println("\n____________CATEGORIES SCREEN____________");
            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See All Categories\n2 - Search Category By Name\n3 - See All Products In a Category\n0 - Go Back");
            int categoryScreenChoice = Utils.messageAndResponseInt("Enter your option: ");
        }
    }

    public static void displayEmployeeScreen() {
        boolean ifContinueEmployeeScreen = true;

        while (ifContinueEmployeeScreen) {
            System.out.println("\n____________EMPLOYEES SCREEN____________");
            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See All Employees\n2 - Search Employee By Name\n3 - Search Employees by Title\n0 - Go Back");
            int employeeScreenChoice = Utils.messageAndResponseInt("Enter your option: ");
        }
    }

    public static void processAllProducts() {
        ArrayList<NorthwindData> productsList = productDao.getAllProducts();
        printData(productsList);

    }

    public static void processProductByID() {

        String productID = Utils.promptGetUserInput("\nPlease enter the ProductID: ");
        String query = "SELECT * FROM products WHERE ProductID = ?";

        Product product = productDao.getProduct(query, productID);

        if (product == null) {
            System.out.println("There was no product found with that ID...");
        } else {
            System.out.println("------------------------------");
            product.print();
        }

        Utils.pauseApp();
    }

    public static void processProductByName() {
        String productName = Utils.promptGetUserInput("\nPlease Enter the Product Name: ");
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
        String customerName = Utils.promptGetUserInput("\nPlease Enter the customers First or Last Name: ");
        String query = "SELECT * FROM customers where ContactName LIKE ?";

        Customer customer = customerDao.getCustomer(query, customerName);

        if (customer == null){
            System.out.println("No customers found with that Contact Name...");
        } else {
            customer.print();
        }
    }

    public static void processCustomerByCompany() {
        String companyName = Utils.promptGetUserInput("Please Enter the Company Name: ");
        String query = "SELECT * FROM customers WHERE CompanyName LIKE ?";

        ArrayList<NorthwindData> customersList = customerDao.getCustomersList(query, companyName);

        printData(customersList);
    }

    public static void processAllCategories() {
        ArrayList<NorthwindData> categoriesList = categoryDao.getAllCategories();
        printData(categoriesList);

        System.out.println("\nWould you like to view all products in a certain category? (Y or N)");
        String userChoice = Utils.promptGetUserInput("Enter here: ").trim();

        if (userChoice.equalsIgnoreCase("y")) {
            processProductsFromCategory();
        }
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

    public static void printData(ArrayList<NorthwindData> northwindDataList) {

        if (northwindDataList.isEmpty()) {
            System.out.println("There is no data to display...");
        } else {
            for (NorthwindData column : northwindDataList) {
                column.print();
                System.out.println("-------------------------------------------------------------");
            }
        }

        Utils.pauseApp();
    }

}
