package com.example.milestone5_2.model;

import android.app.Person;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnore;




public class AddressBook {
	private static Scanner scanner = new Scanner(System.in);
	//main data storage structure for the entire application
	private ArrayList<BaseContact> theList;
//	@JsonIgnore
//	String firstName;
//	@JsonIgnore
//	String lastName;

	//default constructor creates an empty list of type BaseContact
	public AddressBook()
	{
		this.theList = new ArrayList<BaseContact>();
	}
	
	//add one person/newBusiness contact to the list
	public <T extends BaseContact> void addOne(T contact) {
		this.theList.add(contact);
	}
	 public <T extends BaseContact> boolean deleteOne(T contact) {
	        if (this.theList.contains(contact)) {
	            this.theList.remove(contact);
	            return true;
	        } else {
	            return false;
	        }
	    }

	public void sort() {
        Collections.sort(theList);
    }
	
//	public int compareTo(AddressBook contact) {
//
//		 if ( this.lastName == contact.lastName) {
//			 return this.firstName.compareTo(contact.firstName);
//		 }
//		 return (this.lastName.compareTo(contact.lastName));
//
//
//
//}
	
	public void setTheList(ArrayList<BaseContact> theList) {
		this.theList = theList;
	}




	@JsonIgnore
	public int getSize() {
        return theList.size();
    }
	
	public BaseContact getContact(int i) {
        return theList.get(i);
    }
	//search method to find and display contact
	public AddressBook search(String name) {
		name = name.toLowerCase();
		// returns a partial list of the original addressbook
		AddressBook returnedAddressBook = new AddressBook();

		for (int i = 0; i < theList.size(); i++) {
			Log.d("austin", "in search loop " + theList.get(i).getLastName().toLowerCase());
			if (theList.get(i).getLastName().toLowerCase().contains(name)) {

				returnedAddressBook.getTheList().add(theList.get(i));
			}else
			if (theList.get(i).getFirstName().toLowerCase().contains(name)) {
				returnedAddressBook.getTheList().add(theList.get(i));
			}


		}
		return returnedAddressBook;
	}
	//method to find one contact in the main contact list
//	public boolean searchTheList(String name) {
//
//        for (int i = 0; i < theList.size(); i++) {
//        	Log.d("austin", "in search loop " + theList.get(i).getLastName().toLowerCase());
//            if (theList.get(i).getLastName().toLowerCase().contains (name)) {
//
//               return true;
//            }
//            if (theList.get(i).getFirstName().toLowerCase().contains(name)) {
//               return true;
//            }
//        }
//        return false;
//    }



	//method to display all contacts
	public void displayContacts() {
        System.out.println("List of Contacts: ");
        for (int i = 0; i < theList.size(); i++) {
            System.out.println(theList.get(i).toString());
        }
    }
	//method that displays one contact 

	
    //edit method used in the main program
	public void editContact(BaseContact x, int pos) {
		this.theList.remove(pos); 
		this.theList.add(x);
		
	}
	//method to get the main list of contacts
	public List<BaseContact> getTheList(){
		return theList;
	}

	//Separate newBusiness contact list
	@JsonIgnore
	public List<BusinessContact> getBusinessList() {
		List<BusinessContact> business = new ArrayList<>();
		
		for(int i =0; i < theList.size(); i++) {
			if(theList.get(i).getClass() ==  BusinessContact.class ){
				business.add((BusinessContact) theList.get(i));
			}
		}
	
		return business;
	}
//	Separate person contact list
	@JsonIgnore
	public List<PersonContact> getPersonList() {
		List<PersonContact> persons = new ArrayList<>();
		
		for(int i =0; i < theList.size(); i++) {
			if(theList.get(i).getClass() ==  PersonContact.class ) {
				persons.add((PersonContact) theList.get(i));
			}
		}
	
		return persons;
	}
	//this method searches the main list to find contact to delete
	public void deleteContact(BaseContact c) {
        for (int i = 0; i < theList.size(); i++) {
            if (theList.get(i).getFirstName().equals(c.getFirstName())) {
                theList.remove(theList.get(i));
                break;
            } else if (theList.get(i).getLastName().equals(c.getLastName())) {
                theList.remove(theList.get(i));
                break;
            }
        }

    }
//	//actual method to delete contact from the delteContact(BaseContact c) method
//	 public void deleteContact(AddressBook list) {
//		 Scanner scanner = new Scanner(System.in);
//	        String deleteContact = scanner.nextLine();
//	        if (list.searchTheList(deleteContact) == true) {
//	            for (int i = 0; i < list.getSize(); i++) {
//	                if (list.getContact(i).getFirstName().equals(deleteContact)
//	                        || list.getContact(i).getLastName().equals(deleteContact)) {
//	                    System.out.println(list.getContact(i).getFirstName() + " has been deleted!");
//	                    list.deleteContact(list.getContact(i));
//	                    break;
//
//	                }
//
//	            }
//	        } else {
//	            System.out.println("There is no contact with that first or last name.");
//	        }
//	    }
//
	@Override
	public String toString() {
		return "AddressBook ContactList: "+ "\n" + theList + "\n";
				
	}
	
	
	
}
