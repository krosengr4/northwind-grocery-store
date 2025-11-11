package models;

public class Category implements NorthwindData {

	public int categoryID;
	public String name;
	public String description;

	public Category(int categoryID, String name, String description) {
		this.categoryID = categoryID;
		this.name = name;
		this.description = description;
	}

	//	region Getters and Setters
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
//	endregion

	public void print() {
		System.out.println("-----CATEGORY-----");
		System.out.println("Category ID: " + this.categoryID);
		System.out.println("Category Name: " + this.name);
		System.out.println("Category Description: " + this.description);
	}
}
