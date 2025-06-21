import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDao {

   private final DataSource dataSource;

   public CategoryDao(DataSource dataSource) {
	  this.dataSource = dataSource;
   }

   public ArrayList<NorthwindData> getAllCategories() {

	  ArrayList<NorthwindData> categoriesList = new ArrayList<>();

	  try(Connection conn = dataSource.getConnection()) {

		 String query = "SELECT * FROM categories ORDER BY CategoryID;";
		 PreparedStatement prepStatement = conn.prepareStatement(query);

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			int catID = results.getInt("CategoryID");
			String catName = results.getString("CategoryName");
			String catDescription = results.getString("Description");

			Category newCategory = new Category(catID, catName, catDescription);
			categoriesList.add(newCategory);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return categoriesList;
   }

   public Category getCategoryByName(String userInput) {
	  Category category = null;

	  try(Connection conn = dataSource.getConnection()) {

		 String query = "SELECT * FROM categories WHERE CategoryName LIKE ?";
		 PreparedStatement prepStatement = conn.prepareStatement(query);
		 prepStatement.setString(1, "%" + userInput + "%");

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			int catID = results.getInt("CategoryID");
			String catName = results.getString("CategoryName");
			String catDescription = results.getString("Description");

			category = new Category(catID, catName, catDescription);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return category;
   }

   public Category getCategoryByID(int userCategoryID) {
	  Category category = null;

	  try(Connection conn = dataSource.getConnection()) {

		 String query = "SELECT * FROM categories WHERE CategoryID = ?";
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setInt(1, userCategoryID);

		 ResultSet results = statement.executeQuery();
		 while(results.next()) {
			int categoryID = results.getInt("CategoryID");
			String categoryName = results.getString("CategoryName");
			String description = results.getString("Description");

			category = new Category(categoryID, categoryName, description);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return category;
   }

   public void addCategory(Category category) {
	  String categoryName = category.name;
	  String description = category.description;

	  try(Connection conn = dataSource.getConnection()) {
		 String query = "INSERT INTO categories (CategoryName, Description)" +
								"VALUES (?, ?);";
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setString(1, categoryName);
		 statement.setString(2, description);

		 int rows = statement.executeUpdate();
		 if(rows != 0) {
			System.out.println("Success! Added " + categoryName + " Category");
		 } else {
			System.err.println("ERROR! Could not add Category to the database!!!");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   public void updateCategory(String query, int categoryID, String newValue) {
	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setString(1, newValue);
		 statement.setInt(2, categoryID);

		 int rows = statement.executeUpdate();
		 if(rows != 0) {
			System.out.println("\nSuccess! Information Updated for Category with an ID of: " + categoryID);
		 } else {
			System.out.println("\nCould not find Category with that ID...");
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   public void deleteCategory(int categoryId) {
	  String query = "DELETE FROM categories WHERE CategoryID = ?;";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setInt(1, categoryId);

		 int rows = statement.executeUpdate();
		 if (rows != 0){
			System.out.println("Success! The category was deleted!");
		 } else {
			System.err.println("ERROR! The category could not be deleted!")
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }
}
