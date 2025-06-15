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
                int supplierID = Integer.parseInt(results.getString("SupplierID"));
                int categoryID = Integer.parseInt(results.getString("CategoryID"));
                String quantityPerUnit = results.getString("QuantityPerUnit");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));
                int reorderLevel = Integer.parseInt(results.getString("ReorderLevel"));
                boolean isDiscontinued = Boolean.parseBoolean(results.getString("Discontinued"));

                Product newProduct = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, isDiscontinued);
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
                int supplierID = Integer.parseInt(results.getString("SupplierID"));
                int categoryID = Integer.parseInt(results.getString("CategoryID"));
                String quantityPerUnit = results.getString("QuantityPerUnit");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));
                int reorderLevel = Integer.parseInt(results.getString("ReorderLevel"));
                boolean isDiscontinued = Boolean.parseBoolean(results.getString("Discontinued"));

                Product newProduct = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, isDiscontinued);

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
                int supplierID = Integer.parseInt(results.getString("SupplierID"));
                int categoryID = Integer.parseInt(results.getString("CategoryID"));
                String quantityPerUnit = results.getString("QuantityPerUnit");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));
                int reorderLevel = Integer.parseInt(results.getString("ReorderLevel"));
                boolean isDiscontinued = Boolean.parseBoolean(results.getString("Discontinued"));

                product = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, isDiscontinued);
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
                int supplierID = Integer.parseInt(results.getString("SupplierID"));
                int categoryID = Integer.parseInt(results.getString("CategoryID"));
                String quantityPerUnit = results.getString("QuantityPerUnit");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));
                int reorderLevel = Integer.parseInt(results.getString("ReorderLevel"));
                boolean isDiscontinued = Boolean.parseBoolean(results.getString("Discontinued"));

                Product newProduct = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, isDiscontinued);
                productsList.add(newProduct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

    //String productName, int supplierID, int categoryID, String quantityPerUnit, double unitPrice,
    //                            int unitsInStock, int unitsOnOrder, int reorderLevel, boolean isDiscontinued

    public void addAProduct (Product product) {

        String productName = product.productName;
        int supplierID = product.supplierID;
        int categoryID = product.categoryID;
        String quantityPerUnit = product.quantityPerUnit;
        double unitPrice = product.unitPrice;
        int unitsInStock = product.unitsInStock;
        int reorderLevel = product.reorderLevel;
        boolean isDiscontinued = product.isDiscontinued;

        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO products (ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, ReorderLevel, Discontinued) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, productName);
            statement.setString(2, String.valueOf(supplierID));
            statement.setString(3, String.valueOf(categoryID));
            statement.setString(4, quantityPerUnit);
            statement.setString(5, String.valueOf(unitPrice));
            statement.setString(6, String.valueOf(unitsInStock));
            statement.setString(7, String.valueOf(reorderLevel));
//            statement.setString(8, String.valueOf(isDiscontinued));
            statement.setBoolean(8, isDiscontinued);

            int rows = statement.executeUpdate();

            if (rows != 0) {
                System.out.println("Success! The new product was added to the database!");
            } else {
                System.out.println("ERROR! The new product was not added to the database!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAProduct(String query, String productID, String newValue) {

        //! Fix Error "Data truncation: Data too long for column 'Discontinued' at row 1"
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newValue);
            statement.setString(2, String.valueOf(productID));

            int rows = statement.executeUpdate();

            if (rows != 0) {
                System.out.println("\nSuccess! Information Updated for Product with an ID of: " + productID);
            } else {
                System.out.println("\nCould not find that product...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}