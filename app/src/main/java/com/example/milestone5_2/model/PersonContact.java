package com.example.milestone5_2.model;

import android.support.annotation.NonNull;

public class PersonContact extends BaseContact{
	//Person Contact construction. Creating the properties for the Person Contact

	private String description;
	private String email;
	
	public PersonContact(String name, String street, String city, String state, String photo, String zip,
			String country, String phoneNumber, String email,String description) {
		super(name, street, city, state, photo, zip, country, phoneNumber);

		this.description = description;
		this.email = email;
	}

	public PersonContact(String name) {
		super();

		this.name = name;
	}

	public PersonContact() {
		super("Austin Harvey",  "514 Broad St.", "Menasha", "WI","mypicture.jpg", "54952", "United States", "920-509-1053");
		this.description = "Cool, Funny, and Smart";
		this.email = "austinharvey49@gmail.com";
		
	}




	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public int compareTo(@NonNull BaseContact o) {
		{
			return this.name.compareTo(o.name);
		}
	}

	public String toString() {
	       String Return = "----------Person Contact--------------\n"
	     	                  + "Name = " + this.name + "\n"
	                          + "Street = " + super.street + "\n"
	                          + "City = " + super.city + "\n"
	                          + "State = " + super.state + "\n"
	                          + "Zip Code = " + super.zip + "\n"
	                          + "Country = " + super.country + "\n"
	                          + "Phone # = " + super.phoneNumber + "\n"
	                          + "Email = " + this.email + "\n"
	                          + "Photo = " + super.photo + "\n"
	                          + "Description = " + this.description + "\n"
	                     + "--------------------------------------\n";
	                          

	       return Return;
	}

}
