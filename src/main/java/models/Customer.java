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

//	region Getters and Setters
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
//	endregion

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
