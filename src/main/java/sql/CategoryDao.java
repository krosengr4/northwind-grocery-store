package sql;

import models.Category;
import models.NorthwindData;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDao extends MySqlDaoBase{

   public CategoryDao(DataSource dataSource) {
	  super(dataSource);
   }

   public ArrayList<NorthwindData> getAllCategories() {
	  ArrayList<NorthwindData> categoriesList = new ArrayList<>();
	  String query = "SELECT * FROM categories ORDER BY CategoryID;";

	  try(Connection connection = getConnection()) {

		 PreparedStatement prepStatement = connection.prepareStatement(query);

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

	  try(Connection conn = getConnection()) {

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

	  try(Connection conn = getConnection()) {
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

	  try(Connection conn = getConnection()) {
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

   public void updateCategory(Category category, int categoryID, String newValue) {
	   String query = """
			   UPDATE categories
			   set CategoryName = ?,
			   Description = ?
			   WHERE CategoryID = ?;
			   """;

	   try(Connection connection = getConnection()) {
		   PreparedStatement statement = connection.prepareStatement(query);
		   statement.setString(1, category.name);
		   statement.setString(2, category.description);
		   statement.setInt(3, categoryID);

		   int rows = statement.executeUpdate();
		   if (rows > 0) {
			   System.out.println("Success the category was updated!");
			   category.print();
		   } else {
			   System.err.println("ERROR! Could not update the category");
		   }

	   } catch(SQLException e) {
		   throw new RuntimeException(e);
	   }
   }

   public void deleteCategory(int categoryId) {
	  String query = "DELETE FROM categories WHERE CategoryID = ?;";

	  try(Connection conn = getConnection()) {
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
