import models.NorthwindData;
import models.Shipper;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.ShipperDao;
import utils.Utils;

import java.util.ArrayList;

public class ShipperLogic {

   static BasicDataSource dataSource = UILogic.dataSource;
   static ShipperDao shipperDao = new ShipperDao(dataSource);
   static UserInterface ui = new UserInterface();

   public static void processShippersScreen() {
	  boolean continueShipperScreen = true;

	  while(continueShipperScreen) {
		 int shipperScreenAction = ui.displayShippersScreen();

		 switch(shipperScreenAction) {
			case 1 -> processAllShippers();
			case 2 -> processShipperByName();
			case 3 -> processShipperByPhone();
			case 4 -> processAddShipper();
			case 5 -> processUpdateShipper();
			case 6 -> processDeleteShipper();
			case 0 -> continueShipperScreen = false;
		 }
	  }
   }

   public static void processAllShippers() {
	  ArrayList<NorthwindData> shipperList = shipperDao.getAllShippers();
	  UILogic.printData(shipperList);
   }

   public static void processShipperByName() {
	  String companyName = Utils.getUserInput("Please Enter the Company Name of the models.Shipper: ");
	  String query = "SELECT * FROM shippers WHERE CompanyName LIKE ?";

	  Shipper shipper = shipperDao.getShipper(query, companyName);
	  if(shipper == null) {
		 System.out.println("There are no shippers with that name...");
	  } else {
		 shipper.print();
	  }

	  Utils.pauseApp();
   }

   public static void processShipperByPhone() {
	  System.out.println("Please enter the phone number of the shipper like '(XXX) xxx-xxxx'");
	  String phoneNumber = Utils.getUserInput("Enter here: ");
	  String query = "SELECT * FROM shippers WHERE Phone LIKE ?";

	  Shipper shipper = shipperDao.getShipper(query, phoneNumber);
	  if(shipper == null) {
		 System.out.println("There are no shippers with that phone number...");
	  } else {
		 shipper.print();
	  }

	  Utils.pauseApp();
   }

   public static void processAddShipper() {
	  System.out.println("\n-------ADD A NEW SHIPPER-------");
	  String companyName = Utils.getUserInput("Enter the Company Name: ");
	  String phoneNumber = Utils.getUserInput("Enter the Phone Number: ");

	  Shipper shipper = new Shipper(0, companyName, phoneNumber);
	  shipperDao.addShipper(shipper);
   }

   public static void processUpdateShipper() {
	  System.out.println("\nEnter the ID of the models.Shipper you wish to update.");
	  int shipperId = Utils.getUserInputInt("Enter here: ");

	  String query = setUpdateShipperQuery();

	  if(!query.equals("back")) {
		 String newValue = Utils.getUserInput("Enter the new value: ");

		 shipperDao.updateShipper(query, shipperId, newValue);
		 displayShipperAfterUpdate(shipperId);
	  }
	  Utils.pauseApp();
   }

   private static String setUpdateShipperQuery() {
	  int columnToUpdate = ui.displayUpdateShipper();
	  String query = "UPDATE shippers SET ";

	  switch(columnToUpdate) {
		 case 1 -> query += "CompanyName = ?";
		 case 2 -> query += "Phone = ?";
		 case 0 -> {
			return "back";
		 }
	  }
	  query += "WHERE ShipperID = ?;";

	  return query;
   }

   public static void displayShipperAfterUpdate(int shipperId) {
	  Shipper shipper = shipperDao.getShipperById(shipperId);

	  if(shipper == null) {
		 System.out.println("Could not find a shipper with that ID...");
	  } else {
		 shipper.print();
	  }
   }

   public static void processDeleteShipper() {
	  String password = Utils.getUserInput("Please enter the password: ");
	  boolean isCorrect = Utils.passwordCheck(password);

	  if(isCorrect) {
		 int shipperId = Utils.getUserInputInt("Enter the models.Shipper ID you wish to delete: ");
		 shipperDao.deleteShipper(shipperId);
	  } else {
		 System.out.println("That password is incorrect!!!");
	  }
	  Utils.pauseApp();
   }
}
