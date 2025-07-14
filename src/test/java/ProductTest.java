import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ProductTest {

    static BasicDataSource dataSource = new BasicDataSource();
    static ProductDao productDao = new ProductDao(dataSource);

    @Test
    void processProductByID() {

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String productID = "52";
        String query = "SELECT * FROM products WHERE ProductID = ?";

        Product product = productDao.getProduct(query, productID);

        String actual = product.productName;

        Assertions.assertEquals("Filo Mix", actual);

    }

    @Test
    void processProductByName() {

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String name = "Sasquatch Ale";
        String query = "SELECT * FROM products WHERE ProductName LIKE ?;";

        Product product = productDao.getProduct(query, name);
        int actual = product.productID;

        Assertions.assertEquals(34, actual);
    }

    @Test
    void processProductByPrice() {

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        double minPrice = 11.9999;
        double maxPrice = 12.0001;
        String query = "SELECT * FROM products WHERE UnitPrice BETWEEN ? and ?;";

        ArrayList<NorthwindData> productsList = productDao.getProductsByPrice(query, minPrice, maxPrice);

        Product product = (Product) productsList.getFirst();
        int actual = product.productID;

        Assertions.assertEquals(46, actual);

    }
}