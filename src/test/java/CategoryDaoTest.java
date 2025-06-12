import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
class CategoryDaoTest {

    BasicDataSource dataSource = new BasicDataSource();
    CategoryDao categoryDao = new CategoryDao(dataSource);

    @Test
    void getCategory() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String categoryName = "Dairy";

        Category category = categoryDao.getCategory(categoryName);
        int actual = category.categoryID;

        Assertions.assertEquals(4, actual);
    }
}