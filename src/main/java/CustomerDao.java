import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDao {
    
    private final DataSource dataSource;
    
    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getAllCustomers() {

        ArrayList<NorthwindData> customersList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM customers ORDER BY Country;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String customerName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");

                Customer newCustomer = new Customer(customerName, companyName, city, country, phoneNumber);
                customersList.add(newCustomer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customersList;
    }

    public Customer getCustomer(String query, String userInput) {
        Customer customer = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String customerName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");

                customer = new Customer(customerName, companyName, city, country, phoneNumber);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public ArrayList<NorthwindData> getCustomersList(String query, String userInput) {
        ArrayList<NorthwindData> customersList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String customerName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");

                Customer newCustomer = new Customer(customerName, companyName, city, country, phoneNumber);

                customersList.add(newCustomer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customersList;
    }

}
