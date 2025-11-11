import models.Customer;
import models.NorthwindData;

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
                Customer newCustomer = mapRow(results);
                customersList.add(newCustomer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customersList;
    }

    public Customer getCustomer(String query, String userInput) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userInput);

            ResultSet results = prepStatement.executeQuery();

            if(results.next()) {
			   return mapRow(results);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return null;
    }

    public ArrayList<NorthwindData> getCustomersList(String query, String userInput) {
        ArrayList<NorthwindData> customersList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                Customer newCustomer = mapRow(results);
                customersList.add(newCustomer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customersList;
    }

    public void addCustomer (Customer customer) {
        //region Setting each field of object into its own variable
        String companyName = customer.companyName;
        String contactName = customer.contactName;
        String contactTitle = customer.contactTitle;
        String address = customer.address;
        String region = customer.region;
        String postalCode = customer.postalCode;
        String city = customer.city;
        String country = customer.country;
        String phoneNumber = customer.phoneNumber;
        String fax = customer.fax;
        //endregion

        try (Connection conn = dataSource.getConnection()) {

            String query = "INSERT INTO customers (CompanyName, ContactName, ContactTitle, Address, City, Region, PostalCode, Country, Phone, Fax) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, companyName);
            statement.setString(2, contactName);
            statement.setString(3, contactTitle);
            statement.setString(4, address);
            statement.setString(5, region);
            statement.setString(6, postalCode);
            statement.setString(7, city);
            statement.setString(8, country);
            statement.setString(9, phoneNumber);
            statement.setString(10, fax);

            int rows = statement.executeUpdate();

            if (rows != 0) {
                System.out.println("Success! The new customer was added to the database!");
            } else {
                System.err.println("ERROR! The new customer was not added!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer (String query, String customerID, String newValue) {

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newValue);
            statement.setString(2, customerID);

            int rows = statement.executeUpdate();

            if (rows != 0) {
                System.out.println("Success! Information Updated for models.Customer with an ID of: " + customerID);
            } else {
                System.err.println("ERROR! The models.Customer Information was not updated!!!");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

	public void deleteCustomer(int customerId) {
	   String query = "DELETE FROM customers WHERE CustomerID = ?;";

	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setInt(1, customerId);

		  int rows = statement.executeUpdate();
		  if (rows != 0) {
			 System.out.println("Success! models.Customer was deleted!");
		  } else {
			 System.err.println("ERROR! models.Customer was not deleted!!!");
		  }
	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}

	private Customer mapRow(ResultSet results) throws SQLException {
	   int customerId = results.getInt("CustomerID");
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

	   return new Customer(customerId, companyName, customerName, contactTitle, address, region, postalCode, city, country, phoneNumber, fax);
	}

}
