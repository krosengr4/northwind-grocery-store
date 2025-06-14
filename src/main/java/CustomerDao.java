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
                //region getting fields for Customer object
                String companyName = results.getString("CompanyName");
                String customerName = results.getString("ContactName");
                String contactTitle = results.getString("ContactTitle");
                String address = results.getString("Address");
                String region = results.getString("Region");
                String postalCode = results.getString("PostalCode");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");
                String fax = results.getString("Fax");
                //endregion


                Customer newCustomer = new Customer(companyName, customerName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);
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
                //region getting fields for Customer object
                String companyName = results.getString("CompanyName");
                String customerName = results.getString("ContactName");
                String contactTitle = results.getString("ContactTitle");
                String address = results.getString("Address");
                String region = results.getString("Region");
                String postalCode = results.getString("PostalCode");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");
                String fax = results.getString("Fax");
                //endregion


                customer = new Customer(companyName, customerName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);
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
                //region getting fields for Customer object
                String companyName = results.getString("CompanyName");
                String customerName = results.getString("ContactName");
                String contactTitle = results.getString("ContactTitle");
                String address = results.getString("Address");
                String region = results.getString("Region");
                String postalCode = results.getString("PostalCode");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phoneNumber = results.getString("Phone");
                String fax = results.getString("Fax");
                //endregion


                Customer newCustomer = new Customer(companyName, customerName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);

                customersList.add(newCustomer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customersList;
    }

}
