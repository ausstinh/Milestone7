package com.example.milestone5_2.model;

import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type")
		@JsonSubTypes({
		  @Type(value = BaseContact.class, name = "BaseContact"),
		  @Type(value = PersonContact.class, name = "Person Contact"), 
		  @Type(value = BusinessContact.class, name = "Business Contact") 
		})
public abstract class BaseContact implements Comparable<BaseContact> {
	
	 private static Scanner scanner = new Scanner(System.in);
	//Base Contact construction. Creating the properties of any contact. 
	protected String name;
	protected String street;
	protected String city;
	protected String state;
	protected String photo;
	protected String zip;
	protected String country;
	protected String phoneNumber;

	
	
	
	public BaseContact(String name,  String street, String city, String state, String photo, String zip, String country,
			String phoneNumber) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.photo = photo;
		this.zip = zip;
		this.country = country;
		this.phoneNumber = phoneNumber;

	}

	public BaseContact() {
		this.name = "Austin Harvey";
		this.street = "514 Broad St";
		this.city = "Menasha";
		this.state = "Wisconsin";
		this.photo = "mypicture.jpg";
		this.zip = "54952";
		this.country = "United States";
		this.phoneNumber = "920-509-10530";

				
	}

	public String getName(){
		return name;
	}
	public void setName(){
		this.name = name;
	}

	public String getFirstName() {
		return name;
	}

	public void setFirstName(String name) {
		this.name = name;
	}

	public String getLastName() {return name;}

	public void setLastName(String name) {this.name = name;}



	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
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



	 


	
	
}


