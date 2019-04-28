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
import com.example.milestone5_2.model.BusinessContact;

import java.io.File;

public class businessContactForm extends AppCompatActivity {

    ImageView iv_back, iv_delete, iv_edit, iv_call, iv_navigate, iv_messages, iv_pic;
    AddressBook addressBook;
    TextView tv_name, tv_address, tv_phone, tv_city, tv_state, tv_zipCode, tv_country, tv_am, tv_pm, tv_URL;
    int positionnumber = -1;
    PersonAdapter adapter;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_contact);
        addressBook = ((MyApplication) this.getApplication()).getAddressBook();
        adapter = new PersonAdapter(businessContactForm.this, addressBook);


        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_state = findViewById(R.id.tv_state);
        tv_zipCode = findViewById(R.id.tv_zipCode);
        tv_phone = findViewById(R.id.tv_phone);
        tv_country = findViewById(R.id.tv_country);
        tv_am = findViewById(R.id.tv_am);
        tv_pm = findViewById(R.id.tv_pm);
        tv_URL = findViewById(R.id.tv_description);
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


        BusinessContact b = (BusinessContact) addressBook.getTheList().get(positionnumber);


        tv_name.setText(b.getName());
        tv_address.setText(b.getStreet());
        tv_city.setText(b.getCity() + ", " + b.getState());
        tv_zipCode.setText(b.getZip());
        tv_phone.setText(b.getPhoneNumber());
        tv_country.setText(b.getCountry());
        tv_am.setText(b.getOpening());
        tv_pm.setText(b.getClosing());
        tv_URL.setText(b.getUrl());

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
                Intent i = new Intent(v.getContext(), deleteBusiness.class);
                i.putExtra("contactIndex", positionnumber);
                startActivity(i);
            }
        });

        if(b.getPhoto().startsWith("/storage")){
            Glide.with(businessContactForm.this).load(new File(b.getPhoto())).into(iv_pic);
        }else
            Glide.with(businessContactForm.this).load(Uri.parse(b.getPhoto())).into(iv_pic);

        iv_edit = findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditBusiness.class);

                //get the contents of person at position
                BusinessContact b = addressBook.getBusinessList().get(positionnumber);




                i.putExtra("name", b.getName());
                i.putExtra("phonenumber", b.getPhoneNumber().toString());
                i.putExtra("address", b.getStreet());
                i.putExtra("city", b.getCity());
                i.putExtra("state", b.getState());
                i.putExtra("photo", b.getPhoto());
                i.putExtra("zipcode", b.getZip().toString());
                i.putExtra("country", b.getCountry());
                i.putExtra("opening", b.getOpening());
                i.putExtra("closing", b.getClosing());
                i.putExtra("URL", b.getUrl());
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
                String mapsQuery = "geo:0,0?q=" + tv_address.getText().toString() + "" + tv_state.getText().toString() + "" +tv_city.getText().toString();
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

        tv_URL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(tv_URL.getText().toString());
            }
        });



    }
    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(businessContactForm.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(businessContactForm.this,
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

    public void openWebPage(String url) {

        if (!url.startsWith("http://") || !url.startsWith("https://"))
            url = "https://" + url;
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
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
