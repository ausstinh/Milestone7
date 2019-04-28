package com.example.milestone5_2.businessServices;

import android.content.Context;

import com.example.milestone5_2.dataAccess.DataAccessService;
import com.example.milestone5_2.dataAccess.FileIOService;
import com.example.milestone5_2.model.AddressBook;


public class BusinessService{
	Context context;
	//holds the global variables and methods to persist data.
	//"list" is the main data storage structure for the entire application.

	public AddressBook list;
	
	public BusinessService(AddressBook list) {
		super();
		this.setList(list);
	}

	public BusinessService() {
		super();
		this.setList(new AddressBook());
	}
	
	public void saveAllLists() {
		//write all data to the file / database.
		DataAccessService das = new FileIOService(context);
		//das.writeAllData(this);
	}
	
	public void loadAllLists() {

		//read data from file / database.
		DataAccessService das = new FileIOService(context);
		this.list = das.readAllData("contacts.txt");
	}
	
	public AddressBook getList() {
		return list;
	}

	public void setList(AddressBook list) {
		this.list = list;
	}

	




}
