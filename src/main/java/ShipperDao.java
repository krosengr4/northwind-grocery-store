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
                int shipperID = Integer.parseInt(results.getString("ShipperID"));
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

}
