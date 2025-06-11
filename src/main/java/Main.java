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

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - Display All Products\n2 - Display All Customers\n3 - Display All Categories\n4 - Display All Employees\n0 - Exit");
            int userQueryChoice = Utils.messageAndResponseInt("Please select an option: ");

            switch (userQueryChoice) {
                case 1 -> processAllProducts();
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

    public static void processAllProducts() {
        System.out.println("Process All Products");
    }

    public static void processAllCustomers(){
        System.out.println("Process All Customers");
    }

    public static void processAllCategories() {
        System.out.println("Process All Categories");
    }

    public static void processAllEmployees() {
        System.out.println("Process All Employees");
    }

    public static void printData(ArrayList<NorthwindData> northwindDataList) {

        for (NorthwindData column : northwindDataList) {
            column.print();
            System.out.println("-------------------------------------------------------------");
        }

        Utils.pauseApp();
    }

}
