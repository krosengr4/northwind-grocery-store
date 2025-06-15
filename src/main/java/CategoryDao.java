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

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM categories ORDER BY CategoryID;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int catID = Integer.parseInt(results.getString("CategoryID"));
                String catName = results.getString("CategoryName");
                String catDescription = results.getString("Description");

                Category newCategory = new Category(catID, catName, catDescription);
                categoriesList.add(newCategory);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoriesList;
    }

    public Category getCategory(String userInput) {
        Category category = null;

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM categories WHERE CategoryName LIKE ?";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, "%" + userInput + "%");

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int catID = Integer.parseInt(results.getString("CategoryID"));
                String catName = results.getString("CategoryName");
                String catDescription = results.getString("Description");

                category = new Category(catID, catName, catDescription);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;
    }

    public void addCategory(Category category) {
        String categoryName = category.name;
        String description = category.description;

        try (Connection conn = dataSource.getConnection()) {
          String query = "INSERT INTO categories (CategoryName, Description)" +
                  "VALUES (?, ?);";
          PreparedStatement statement = conn.prepareStatement(query);
          statement.setString(1, categoryName);
          statement.setString(2, description);

          int rows = statement.executeUpdate();
          if (rows != 0) {
            System.out.println("Success! Added " + categoryName + " Category");
          } else {
            System.err.println("ERROR! Could not add Category to the database!!!");
          }

        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
    }
}
