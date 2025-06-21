import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ShipperDao {

    private final DataSource dataSource;

    public ShipperDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getAllShippers() {
        ArrayList<NorthwindData> shippersList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM shippers";
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int shipperID = results.getInt("ShipperID");
                String companyName = results.getString("CompanyName");
                String phoneNumber = results.getString("Phone");

                Shipper newShipper = new Shipper(shipperID, companyName, phoneNumber);
                shippersList.add(newShipper);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shippersList;
    }

    public Shipper getShipper(String query, String userInput) {
        Shipper shipper = null;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "%" + userInput + "%");

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int shipperID = results.getInt("ShipperID");
                String companyName = results.getString("CompanyName");
                String phoneNumber = results.getString("Phone");

                shipper = new Shipper(shipperID, companyName, phoneNumber);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shipper;
    }

	public Shipper getShipperById(int shipperId) {
	   Shipper shipper = null;

	   String query = "	SELECT * FROM shippers WHERE ShipperID = ?;";
	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setInt(1, shipperId);

		  ResultSet results = statement.executeQuery();
		  while(results.next()) {
			 int shipperID = results.getInt("ShipperID");
			 String categoryName = results.getString("CompanyName");
			 String phoneNumber = results.getString("Phone");

			 shipper = new Shipper(shipperID, categoryName, phoneNumber);
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	   return shipper;
	}

	public void addShipper(Shipper shipper) {
	   String companyName = shipper.companyName;
	   String phoneNumber = shipper.phoneNumber;

	   String query = "INSERT INTO shippers (CompanyName, Phone) " +
							  "VALUES (?, ?);";
	   try (Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setString(1, companyName);
		  statement.setString(2, phoneNumber);

		  int rows = statement.executeUpdate();
		  if(rows != 0){
			 System.out.println("Success! Shipper was added!!!");
		  } else {
			 System.err.println("ERROR! Could not add shipper!!!");
		  }

	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}

	public void updateShipper(String query, int shipperId, String newValue) {
	   try(Connection conn = dataSource.getConnection()) {
		  PreparedStatement statement = conn.prepareStatement(query);
		  statement.setString(1, newValue);
		  statement.setInt(2, shipperId);

		  int rows = statement.executeUpdate();
		  if(rows != 0) {
			 System.out.println("Success! Shipper information was updated!");
		  } else {
			 System.err.println("Error! Could not update shipper information!!!");
		  }
	   } catch (SQLException e) {
		  throw new RuntimeException(e);
	   }
	}

}
