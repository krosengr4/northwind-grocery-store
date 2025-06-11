import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ProductDao {

    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getAllProducts() {
        ArrayList<NorthwindData> productsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM products;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);
                productsList.add(newProduct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

    public ArrayList<NorthwindData> getProductsFromCategory(String userCatChoice) {
        ArrayList<NorthwindData> productsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM products WHERE CategoryID = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userCatChoice);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);

                productsList.add(newProduct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

    public Product getProductByID(int userProductChoice) {

        Product product = null;

        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * from products WHERE ProductID = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, userProductChoice);

            ResultSet results = prepStatement.executeQuery();
            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                product = new Product(productID, productName, unitPrice, unitsInStock);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

}
