package com.example.milestone5_2;

import android.app.Application;
import android.net.Uri;

import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.BusinessContact;
import com.example.milestone5_2.model.PersonContact;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {




    private AddressBook addressBook = new AddressBook();

    public AddressBook getAddressBook(){
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public void onCreate() {
        super.onCreate();

        // load existing json data here
              DataService dataService = new DataService(this);
        addressBook = dataService.readAllData("contacts.txt");

    }


}
