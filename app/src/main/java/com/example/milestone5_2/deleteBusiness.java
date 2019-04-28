package com.example.milestone5_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.milestone5_2.model.AddressBook;

public class deleteBusiness extends AppCompatActivity {
    Button btn_yes, btn_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_business);
        final AddressBook addressBook = ((MyApplication) this.getApplication()).getAddressBook();
        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = getIntent();
                int intValue = mIntent.getIntExtra("contactIndex", 0);
                addressBook.deleteContact(addressBook.getTheList().get(intValue));
                Intent i = new Intent(v.getContext(), Home.class);

                startActivity(i);
            }
        });

        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), businessContactForm.class);
                startActivity(i);
            }
        });

    }
    View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
