package logic;

import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import ui.UserInterface;
import utils.Utils;

import java.util.ArrayList;

public class UILogic {

   static BasicDataSource dataSource = new BasicDataSource();
   static UserInterface ui = new UserInterface();

   static String url = "jdbc:mysql://localhost:3306/northwind";
   static String userName = "root";
   static String password = System.getenv("SQL_PASSWORD");

   public static void setDataSource() {
	  dataSource.setUrl(url);
	  dataSource.setUsername(userName);
	  dataSource.setPassword(password);
   }

   public static void processHomeScreen() {
	  boolean ifContinue = true;

	  while(ifContinue) {
		 int userHomeAction = ui.displayHomeScreen();

		 switch(userHomeAction) {
			case 1 -> ProductLogic.processProductsScreen();
			case 2 -> CustomerLogic.processCustomerScreen();
			case 3 -> CategoryLogic.processCategoryScreen();
			case 4 -> EmployeeLogic.processEmployeeScreen();
			case 5 -> ShipperLogic.processShippersScreen();
			case 0 -> ifContinue = false;
			default -> System.err.println("ERROR! Please enter a number listed on the screen!");
		 }
	  }
   }

   public static void printData(ArrayList<NorthwindData> northwindDataList) {
	  if(northwindDataList.isEmpty()) {
		 System.out.println("There is no data to display...");
	  } else {
		 for(NorthwindData column : northwindDataList) {
			column.print();
			System.out.println("-------------------------------------------------------------");
		 }

		 System.out.println("\nColumns Returned: " + northwindDataList.size());
	  }

	  Utils.pauseApp();
   }
}
