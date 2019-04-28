package com.example.milestone5_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.milestone5_2.dataAccess.FileIOService;
import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.BusinessContact;
import com.example.milestone5_2.model.PersonContact;

public class Home extends AppCompatActivity {
    Context context;

    DataService dataService = new DataService(context);
    Button btn_newContact, btn_searchContact, btn_save, btn_load;

AddressBook addressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        addressBook = ((MyApplication) getApplication()).getAddressBook();


        btn_newContact = findViewById(R.id.btn_newContact);
        btn_newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BorP.class);
                startActivity(i);
            }
        });

        btn_searchContact = findViewById(R.id.btn_searchcontact);
        btn_searchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), search.class);
                startActivity(i);
            }
        });







    }
}
