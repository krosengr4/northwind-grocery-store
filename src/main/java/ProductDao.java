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
	  String query = "SELECT * FROM products;";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement prepStatement = conn.prepareStatement(query);

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			Product newProduct = mapRow(results);
			productsList.add(newProduct);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return productsList;
   }

   public ArrayList<NorthwindData> getProductsFromCategory(String userCatChoice) {
	  ArrayList<NorthwindData> productsList = new ArrayList<>();
	  String query = "SELECT * FROM products WHERE CategoryID = ?;";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement prepStatement = conn.prepareStatement(query);
		 prepStatement.setString(1, userCatChoice);

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			Product newProduct = mapRow(results);
			productsList.add(newProduct);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return productsList;
   }

   public Product getProduct(String query, String userInput) {
	  try(Connection conn = dataSource.getConnection()) {

		 PreparedStatement prepStatement = conn.prepareStatement(query);
		 prepStatement.setString(1, userInput);

		 ResultSet results = prepStatement.executeQuery();
		 if(results.next()) {
			return mapRow(results);
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
   }

   public ArrayList<NorthwindData> getProductsByPrice(String query, double minPrice, double maxPrice) {
	  ArrayList<NorthwindData> productsList = new ArrayList<>();

	  try(Connection conn = dataSource.getConnection()) {

		 PreparedStatement prepStatement = conn.prepareStatement(query);
		 prepStatement.setDouble(1, minPrice);
		 prepStatement.setDouble(2, maxPrice);

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			Product newProduct = mapRow(results);
			productsList.add(newProduct);
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return productsList;
   }

   //String productName, int supplierID, int categoryID, String quantityPerUnit, double unitPrice,
   //                            int unitsInStock, int unitsOnOrder, int reorderLevel, boolean isDiscontinued

   public void addAProduct(Product product) {
	  String productName = product.productName;
	  int supplierID = product.supplierID;
	  int categoryID = product.categoryID;
	  String quantityPerUnit = product.quantityPerUnit;
	  double unitPrice = product.unitPrice;
	  int unitsInStock = product.unitsInStock;
	  int reorderLevel = product.reorderLevel;
	  boolean isDiscontinued = product.isDiscontinued;

	  String query = "INSERT INTO products (ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, ReorderLevel, Discontinued) " +
							 "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setString(1, productName);
		 statement.setInt(2, supplierID);
		 statement.setInt(3, categoryID);
		 statement.setString(4, quantityPerUnit);
		 statement.setDouble(5, unitPrice);
		 statement.setInt(6, unitsInStock);
		 statement.setInt(7, reorderLevel);
		 statement.setBoolean(8, isDiscontinued);

		 int rows = statement.executeUpdate();

		 if(rows != 0) {
			System.out.println("Success! The new product was added to the database!");
		 } else {
			System.err.println("ERROR! The new product was not added to the database!!!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   public void updateAProduct(String query, String productID, String newValue) {

	  //! Fix Error "Data truncation: Data too long for column 'Discontinued' at row 1"
	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setString(1, newValue);
		 statement.setString(2, String.valueOf(productID));

		 int rows = statement.executeUpdate();

		 if(rows != 0) {
			System.out.println("\nSuccess! Information Updated for Product with an ID of: " + productID);
		 } else {
			System.out.println("\nCould not find that product...");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   public void deleteProduct(int productId) {
	  String query = "DELETE FROM products WHERE ProductID = ?";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setInt(1, productId);

		 int rows = statement.executeUpdate();
		 if(rows != 0) {
			System.out.println("Success! The product was deleted!");
		 } else {
			System.err.println("ERROR! Couldn't delete the product!!!");
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   private Product mapRow(ResultSet results) throws SQLException {
	  int productID = results.getInt("ProductID");
	  String productName = results.getString("ProductName");
	  int supplierID = results.getInt("SupplierID");
	  int categoryID = results.getInt("CategoryID");
	  String quantityPerUnit = results.getString("QuantityPerUnit");
	  double unitPrice = results.getDouble("UnitPrice");
	  int unitsInStock = results.getInt("UnitsInStock");
	  int reorderLevel = results.getInt("ReorderLevel");
	  boolean isDiscontinued = results.getBoolean("Discontinued");

	  return new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, unitsInStock, reorderLevel, isDiscontinued);
   }

}