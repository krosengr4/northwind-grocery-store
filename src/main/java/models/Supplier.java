public class Supplier implements NorthwindData{

   int supplierId;
   String companyName;
   String contactName;
   String contactTitle;
   String address;
   String city;
   String country;
   String phoneNumber;

   public Supplier(int supplierId, String companyName, String contactName, String contactTitle, String address, String city, String country, String phoneNumber) {
	  this.supplierId = supplierId;
	  this.companyName = companyName;
	  this.contactName = contactName;
	  this.contactTitle = contactTitle;
	  this.address = address;
	  this.city = city;
	  this.country = country;
	  this.phoneNumber = phoneNumber;
   }

   public void print() {
	  System.out.println("-----SUPPLIER-----");
	  System.out.println("Supplier ID: " + this.supplierId);
	  System.out.println("Company Name: " + this.companyName);
	  System.out.println("Contact Name: " + this.contactName);
	  System.out.println("Contact Title: " + this.contactTitle);
	  System.out.println("City: " + this.city);
	  System.out.println("Country: " + this.country);
	  System.out.println("Phone Number: " + this.phoneNumber);
   }

}
