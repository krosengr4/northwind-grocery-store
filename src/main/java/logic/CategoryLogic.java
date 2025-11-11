package logic;

import models.Category;
import models.NorthwindData;
import org.apache.commons.dbcp2.BasicDataSource;
import sql.CategoryDao;
import ui.UserInterface;
import utils.Utils;

import java.util.ArrayList;

public class CategoryLogic {

   static BasicDataSource dataSource = UILogic.dataSource;
   static CategoryDao categoryDao = new CategoryDao(dataSource);
   static UserInterface ui = new UserInterface();

   public static void processCategoryScreen() {
	  boolean continueCategoryScreen = true;

	  while(continueCategoryScreen) {
		 int categoryScreenAction = ui.displayCategoriesScreen();

		 switch(categoryScreenAction) {
			case 1 -> processAllCategories();
			case 2 -> processCategoryByName();
			case 3 -> processAddCategory();
			case 4 -> processUpdateCategory();
			case 5 -> processDeleteCategory();
			case 0 -> continueCategoryScreen = false;
			default -> System.err.println("ERROR! Please Enter a number that is listed!");
		 }
	  }
   }

   public static void processAllCategories() {
	  ArrayList<NorthwindData> categoriesList = categoryDao.getAllCategories();
	  UILogic.printData(categoriesList);
   }

   public static void processCategoryByName() {
	  String categoryName = Utils.getUserInput("Enter the name of category: ").trim();

	  Category category = categoryDao.getCategoryByName(categoryName);

	  if(category == null) {
		 System.out.println("There are no categories with that name...");
	  } else {
		 category.print();
	  }

	  Utils.pauseApp();
   }

   public static void processUpdateCategory() {
	  System.out.println("\nPlease Enter the Category ID of the Category you wish to Update.");
	  int categoryID = Utils.getUserInputInt("Enter here: ");

	  Category category = categoryDao.getCategoryByID(categoryID);
	  if(category == null) {
		  System.out.println("There are no categories with that ID...");
	  } else {
		  int userChoice = ui.displayUpdateCategory();

		  switch(userChoice) {
			  case 1 -> {
				  String newName = Utils.getUserInput("Enter the new category name: ");
				  category.setName(newName.trim());
				  categoryDao.updateCategory(category, categoryID);
			  }
			  case 2 -> {
				  String newDescription = Utils.getUserInput("Enter the new description: ");
				  category.setDescription(newDescription.trim());
				  categoryDao.updateCategory(category, categoryID);
			  }
		  }
	  }
   }

   public static void processDeleteCategory() {
	  String password = Utils.getUserInput("Enter the password: ");
	  boolean isCorrect = Utils.passwordCheck(password);

	  if(isCorrect) {
		 int categoryId = Utils.getUserInputInt("Enter the Category ID to delete: ");
		 categoryDao.deleteCategory(categoryId);
	  } else {
		 System.out.println("That password is incorrect!!!");
	  }
	  Utils.pauseApp();
   }

   public static void processAddCategory() {
	  System.out.println("\n---ADD NEW CATEGORY---\n");
	  String categoryName = Utils.getUserInput("Enter a Category Name: ");
	  String description = Utils.getUserInput("Enter a Category description: ");

	  Category category = new Category(0, categoryName, description);
	  categoryDao.addCategory(category);
   }
}
