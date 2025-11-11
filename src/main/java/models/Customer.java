package models;

public class Customer implements NorthwindData {

   int customerId;
    public String companyName;
    public String contactName;
    public String contactTitle;
    public String address;
    public String region;
    public String postalCode;
    public String city;
    public String country;
    public String phoneNumber;
    public String fax;

    public Customer (int customerId, String companyName, String contactName, String contactTitle, String address, String region, String postalCode,
                     String city, String country, String phoneNumber, String fax) {
	   this.customerId = customerId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.region = region;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
    }

    public void print() {
        System.out.println("-----CUSTOMER-----");
	   System.out.println("Customer ID: " + this.customerId);
        System.out.println("Contact Name: " + this.contactName);
        System.out.println("Company Name: " + this.companyName);
        System.out.println("Title: " + this.contactTitle);
        System.out.println("City: " + this.city);
        System.out.println("Country: " + this.country);
        System.out.println("Phone Number: " + this.phoneNumber);
    }

}
