public class Product implements NorthwindData {

    int productID;
    String name;
    double unitPrice;
    int unitsInStock;

    public Product (int productID, String name, double unitPrice, int unitsInStock) {
        this.productID = productID;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }

    //region getters
    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }
    //endregion

    public void print() {
        System.out.println("Product ID: " + this.productID);
        System.out.println("Product Name: " + this.name);
        System.out.printf("Unit Price: $%.2f\n", this.unitPrice);
        System.out.println("Units In Stock: " + this.unitsInStock);
    }

}
