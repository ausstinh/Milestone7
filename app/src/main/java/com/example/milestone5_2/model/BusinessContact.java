package com.example.milestone5_2.model;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class BusinessContact extends BaseContact{
	 private static Scanner scanner = new Scanner(System.in);
	
	//Business Contact constructs. Creating the Business Contact properties
	private String opening;
	private String closing;
	private String url;

	
	public BusinessContact(String name, String phoneNumber, String street, String city, String state, String photo,
			String zip, String country,  String opening, String closing,
			String url) {
		super(name, street, city, state, photo, zip, country, phoneNumber);
		this.opening = opening;
		this.closing = closing;
		this.url = url;

		
	}

	public BusinessContact() {
		super("Name", "Another street", "Middletown", "AZ", "BusinessStoreFront.jpg", "82312", "United States", "923-324-4912");
		this.opening = "12:00";
		this.closing = "12:00";
		this.url = "http://www.somewhere.com";

	}

	public BusinessContact(String name) {
		super();

		this.name = name;
	}



	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public String getClosing() {
		return closing;
	}
	
	public void setClosing(String closing) {
		this.closing = closing;
	}
	


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public int compareTo(@NonNull BaseContact other) {
		{
			return this.name.compareTo(other.name);
		}
	}

	public String openURLPage() {
		System.out.println("Chrome is now opening to " + this.url);
		return this.url;
		
	}


	public String toString() {

	       String Return = "----------Business Contact--------------\n"
	                          + "Name = " + super.name + "\n"
	                          + "Street = " + super.street + "\n"
	                          + "City = " + super.city + "\n"
	                          + "State = " + super.state + "\n"
	                          + "Zip Code = " + super.zip + "\n"
	                          + "Country = " + super.country + "\n"
	                          + "Phone #: = " + super.phoneNumber + "\n"
	                          + "Photo = " + super.photo + "\n"
	                          + "Business Opening Hours = " + this.opening + "\n"
	                          + "Business Closing Hours = " + this.closing + "\n"
	                          + "Website = " + this.url + "\n"
	       				 + "---------------------------------------\n";

	       return Return;
	}

}
