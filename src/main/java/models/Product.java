import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Product implements NorthwindData {

    int productID;
    String productName;
    int supplierID;
    int categoryID;
    String quantityPerUnit;
    double unitPrice;
    int unitsInStock;
    int reorderLevel;
    boolean isDiscontinued; //!<-- This will need to be a boolean

    public Product (int productID, String productName, int supplierID, int categoryID, String quantityPerUnit,
                    double unitPrice, int unitsInStock, int reorderLevel, boolean isDiscontinued) {
        this.productID = productID;
        this.productName = productName;
        this.supplierID = supplierID;
        this.categoryID = categoryID;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.reorderLevel = reorderLevel;
        this.isDiscontinued = isDiscontinued;
    }

    public void print() {
        System.out.println("-----PRODUCT-----");
        System.out.println("Product ID: " + this.productID);
        System.out.println("Product Name: " + this.productName);
        System.out.println("Category ID: " + this.categoryID);
        System.out.printf("Unit Price: $%.2f\n", this.unitPrice);
        System.out.println("Units In Stock: " + this.unitsInStock);
    }

}
