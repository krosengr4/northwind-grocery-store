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

    public Product getProduct(String query, String userInput) {

        Product product = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userInput);

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

    public ArrayList<NorthwindData> getProductsByPrice(String query, double minPrice, double maxPrice) {
        ArrayList<NorthwindData> productsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setDouble(1, minPrice);
            prepStatement.setDouble(2, maxPrice);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product product = new Product(productID, productName, unitPrice, unitsInStock);
                productsList.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

    public int addAProduct (String productName, int supplierID, int categoryID, String quantityPerUnit, double unitPrice,
                            int unitsInStock, int unitsOnOrder, int reorderLevel) {
        int rows = 0;

        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO products (ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, productName);
            statement.setString(2, String.valueOf(supplierID));
            statement.setString(3, String.valueOf(categoryID));
            statement.setString(4, quantityPerUnit);
            statement.setString(5, String.valueOf(unitPrice));
            statement.setString(6, String.valueOf(unitsInStock));
            statement.setString(7, String.valueOf(unitsOnOrder));
            statement.setString(8, String.valueOf(reorderLevel));

            rows = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rows;
    }

    public void updateAProduct(String query, String productID, String newValue) {

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newValue);
            statement.setString(2, String.valueOf(productID));

            int rows = statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if (rows == 0) {
                System.out.println("We could not find that product...");
            } else {
                while (keys.next()) {
                    System.out.println("Success! Updated Product with the ID of " + keys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
