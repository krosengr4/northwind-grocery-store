public class UserInterface {

    public int displayHomeScreen() {
        System.out.println("\n___________________NORTHWIND GROCERIES___________________");
        System.out.println("-----OPTIONS-----");

        System.out.println("""
                1 - View Products Screen
                2 - View Customers Screen
                3 - View Categories Screen
                4 - View Employees Screen
                0 - Exit
                """);

        return Utils.messageAndResponseInt("Please select an option: ");
    }

    public int displayProductScreen() {
        System.out.println("\n____________PRODUCTS SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("1 - See All Products\n2 - Search Product By ID\n3 - Search Product By Name\n4 - Search Products By Price Range\n0 - Go Back");
        System.out.println("""
                1 - See All Products
                2 - Search Product By ID
                3 - Search Product By Name
                4 - Search Products By Price Range
                0 - Go Back
                """);

        return Utils.messageAndResponseInt("Enter your option: ");
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
                    0 - Go Back""");

        return Utils.messageAndResponseInt("Enter your option: ");
    }

    public int displayCategoriesScreen() {
        System.out.println("\n____________CATEGORIES SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - See All Categories
                2 - Search Category By Name
                3 - See All Products In a Category
                0 - Go Back
                """);

        return Utils.messageAndResponseInt("Enter your option: ");
    }

    public int displayEmployeeScreen() {
        System.out.println("\n____________EMPLOYEES SCREEN____________");
        System.out.println("-----OPTIONS-----");
        System.out.println("""
                1 - See All Employees
                2 - Search Employee By Name
                3 - Search Employees by Title
                0 - Go Back
                """);

        return Utils.messageAndResponseInt("Enter your option: ");
    }

}
