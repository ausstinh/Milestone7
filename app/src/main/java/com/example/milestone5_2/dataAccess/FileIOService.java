package com.example.milestone5_2.dataAccess;

import android.content.Context;

import com.example.milestone5_2.model.AddressBook;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class FileIOService implements DataAccessService{

    Context context;
    ObjectMapper om = new ObjectMapper();

    public FileIOService(Context context){
        this.context = context;
    }
public AddressBook readAllData(String filename) {

        File path = context.getExternalFilesDir(null);
        File file = new File(path, filename);
        AddressBook returnList = new AddressBook();

        try {
           // theList = new ObjectMapper().readerFor(AddressBook.class).readValue(new File("contactList.json"));
            returnList = om.readValue(file, AddressBook.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public void writeAllData(AddressBook theList, String filename) {

       File path = context.getExternalFilesDir(null);
       File file = new File(path, filename);
       try{
           om.writerWithDefaultPrettyPrinter().writeValue(file, theList);
       }catch(IOException e){
           e.printStackTrace();
       }
    }

}