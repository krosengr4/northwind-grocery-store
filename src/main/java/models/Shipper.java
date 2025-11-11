public class Shipper implements NorthwindData{

    int shipperID;
    String companyName;
    String phoneNumber;

    public Shipper (int shipperID, String companyName, String phoneNumber) {
        this.shipperID = shipperID;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public void print() {
        System.out.println("-----SHIPPER-----");
        System.out.println("Shipper ID: " + this.shipperID);
        System.out.println("Company Name: " + this.companyName);
        System.out.println("Phone Number: " + this.phoneNumber);
    }
}
