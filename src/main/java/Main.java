import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

public class Main {

    private static final Log log = LogFactory.getLog(Main.class);
    static BasicDataSource dataSource = new BasicDataSource();

    public static void main(String[] args) {

        System.out.println("\n\t\t----------------------WELCOME TO NORTHWIND GROCERIES!----------------------\n");
        UILogic.setDataSource();
        UILogic.processHomeScreen();

        System.out.println("\n\nHave a Nice Day! :)");
    }
}
