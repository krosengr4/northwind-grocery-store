import java.util.Date;

public class Employee implements NorthwindData {

   int employeeID;
    String firstName;
    String lastName;
    String title;
//	Date birthDate;
//	Date hireDate;
//	String address;
//	String city;
//	String Country;
//	String postalCode;
   String notes;

    public Employee(int employeeID, String firstName, String lastName, String title, String notes) {
	   this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
		this.notes = notes;
    }

    public void print() {
        System.out.println("-----EMPLOYEE-----");
	   System.out.println("Employee ID: " + this.employeeID);
        System.out.println("First Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Title: " + this.title);
    }
}
