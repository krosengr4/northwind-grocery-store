public class Employee implements NorthwindData {

    String firstName;
    String lastName;
    String title;

    public Employee(String firstName, String lastName, String title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
    }

    public void print() {
        System.out.println("-----EMPLOYEE-----");
        System.out.println("First Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Title: " + this.title);
    }
}
