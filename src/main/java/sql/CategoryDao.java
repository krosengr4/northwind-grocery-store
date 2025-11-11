package sql;

import models.Category;
import models.NorthwindData;

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
	  String query = "SELECT * FROM categories ORDER BY CategoryID;";

	  try(Connection conn = dataSource.getConnection()) {

		 PreparedStatement prepStatement = conn.prepareStatement(query);

		 ResultSet results = prepStatement.executeQuery();

		 while(results.next()) {
			Category newCategory = mapRow(results);
			categoriesList.add(newCategory);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return categoriesList;
   }

   public Category getCategoryByName(String userInput) {
	  String query = "SELECT * FROM categories WHERE CategoryName LIKE ?";

	  try(Connection conn = dataSource.getConnection()) {

		 PreparedStatement prepStatement = conn.prepareStatement(query);
		 prepStatement.setString(1, "%" + userInput + "%");

		 ResultSet results = prepStatement.executeQuery();

		 if(results.next()) {
			return mapRow(results);
		 }

	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }

	  return null;
   }

   public Category getCategoryByID(int userCategoryID) {
	  String query = "SELECT * FROM categories WHERE CategoryID = ?";

	  try(Connection conn = dataSource.getConnection()) {
		 PreparedStatement statement = conn.prepareStatement(query);
		 statement.setInt(1, userCategoryID);

		 ResultSet results = statement.executeQuery();

		 if(results.next()) {
			return mapRow(results);
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
	  return null;
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
		 if(rows != 0) {
			System.out.println("Success! The category was deleted!");
		 } else {
			System.err.println("ERROR! The category could not be deleted!");
		 }
	  } catch(SQLException e) {
		 throw new RuntimeException(e);
	  }
   }

   private Category mapRow(ResultSet row) throws SQLException {
	  int categoryId = row.getInt("CategoryID");
	  String categoryName = row.getString("CategoryName");
	  String description = row.getString("Description");

	  return new Category(categoryId, categoryName, description);
   }
}
