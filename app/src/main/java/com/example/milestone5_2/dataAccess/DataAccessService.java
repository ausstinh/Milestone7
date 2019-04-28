package com.example.milestone5_2.dataAccess;

import com.example.milestone5_2.model.AddressBook;

public interface DataAccessService {
	//read and save all the data on the app
	public AddressBook readAllData(String name);
	
	public void writeAllData(AddressBook contactApp, String name);
}
