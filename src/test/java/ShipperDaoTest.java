import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShipperDaoTest {
    BasicDataSource dataSource = new BasicDataSource();
    ShipperDao shipperDao = new ShipperDao(dataSource);

    @Test
    void getAllShippers() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        ArrayList<NorthwindData> shippersList = shipperDao.getAllShippers();
        Shipper shipper = (Shipper) shippersList.getFirst();
        String actual = shipper.companyName;

        Assertions.assertEquals("Speedy Express", actual);
    }

    @Test
    void getShipperByPhone() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String query = "SELECT * FROM shippers WHERE Phone LIKE ?";
        String phoneNumber = "(503) 555-9931";

        Shipper shipper = shipperDao.getShipper(query, phoneNumber);
        int actual = shipper.shipperID;

        Assertions.assertEquals(3, actual);
    }
}