package com.example.milestone5_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.PersonContact;

import java.io.File;

public class personContactForm extends AppCompatActivity {
    ImageView iv_back, iv_delete, iv_edit, iv_navigate, iv_call, iv_messages, iv_pic;
    TextView tv_name, tv_address, tv_phone, tv_city, tv_state, tv_zipCode, tv_country, tv_email, tv_description, tv_pic;

    AddressBook addressBook;
    PersonAdapter adapter;
    int positionnumber = -1;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_contact);
        addressBook = ((MyApplication) this.getApplication()).getAddressBook();
        adapter = new PersonAdapter(personContactForm.this, addressBook);


        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_state = findViewById(R.id.tv_state);
        tv_zipCode = findViewById(R.id.tv_zipCode);
        tv_phone = findViewById(R.id.tv_phone);
        tv_country = findViewById(R.id.tv_country);
        tv_email = findViewById(R.id.tv_email);
        tv_description = findViewById(R.id.tv_description);
        iv_call = findViewById(R.id.iv_call);
        iv_navigate = findViewById(R.id.iv_navigate);
        iv_messages = findViewById(R.id.iv_messages);
        iv_pic = findViewById(R.id.iv_pic);


        Bundle incomingIntent = getIntent().getExtras();


        if (incomingIntent != null) {

            positionnumber = incomingIntent.getInt("indexNumber");

           // Log.d("austinsapp", "persontoshow = " + positionnumber);
           // Log.d("austinsapp", "addressbook = " + addressBook.toString());


        }
        else
            positionnumber = -1;



        if(positionnumber == -1){
            return;
        }
        PersonContact p = (PersonContact) addressBook.getTheList().get(positionnumber);


        tv_name.setText(p.getName());
        tv_address.setText(p.getStreet());
        tv_city.setText(p.getCity());
        tv_state.setText(p.getState());
        tv_zipCode.setText(p.getZip());
        tv_phone.setText(p.getPhoneNumber());
        tv_country.setText(p.getCountry());
        tv_email.setText(p.getEmail());
        tv_description.setText(p.getDescription());




        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), search.class);
                startActivity(i);
            }
        });

        iv_delete = findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), deletePerson.class);
                i.putExtra("contactIndex", positionnumber);
                startActivity(i);
            }
        });

        if(p.getPhoto().startsWith("/storage")){
            Glide.with(personContactForm.this).load(new File(p.getPhoto())).into(iv_pic);
        }else
            Glide.with(personContactForm.this).load(Uri.parse(p.getPhoto())).into(iv_pic);

        iv_edit = findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ){
                Intent i = new Intent(getApplicationContext(), EditPerson.class);

                //get the contents of person at position
                PersonContact p = addressBook.getPersonList().get(positionnumber);




                i.putExtra("name", p.getName());
                i.putExtra("phonenumber", p.getPhoneNumber().toString());
                i.putExtra("address", p.getStreet());
                i.putExtra("city", p.getCity());
                i.putExtra("state", p.getState());
                i.putExtra("photo", p.getPhoto());
                i.putExtra("zipcode", p.getZip().toString());
                i.putExtra("country", p.getCountry());
                i.putExtra("email", p.getEmail());
                i.putExtra("description", p.getDescription());
                i.putExtra("edit", positionnumber);




                startActivity(i);

            }
        });

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(tv_phone.getText().toString());
            }
        });

        iv_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapsQuery = "geo:0,0?q=" + tv_address.getText().toString() +""+ tv_city.getText().toString() +""+ tv_state.getText().toString();
                Uri mapuri = Uri.parse((mapsQuery));
                showMap(mapuri);
            }
        });
        iv_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeMmsMessage(tv_phone.getText().toString(), "Hello I would like to talk");
            }
        });

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] addresses = new String[1];
                addresses[0] = tv_email.getText().toString();
                composeEmail(addresses, "Hello from Shad");
            }
        });



    }
    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(personContactForm.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(personContactForm.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else{

            startActivity(intent);
        }
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeMmsMessage(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber(tv_phone.getText().toString());
            }
        }
    }

}