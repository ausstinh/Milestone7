package com.example.milestone5_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.PersonContact;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class newPerson extends AppCompatActivity {

Context context;
    ImageView iv_back;
    Button btn_add, btn_load, btn_take;
    EditText et_name, et_phone, et_address, et_city, et_state, et_zipCode, et_country, et_email, et_description, et_photo;
    ImageView iv_photo;
    int positionToEdit = -1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int SELECT_A_PHOTO = 2;
    String currentPhotoPath;
    DataService dataService = new DataService(context);
    PersonAdapter adapter;

    AddressBook addressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);

        btn_load = findViewById(R.id.btn_load);
        btn_take = findViewById(R.id.btn_take);


        et_name = findViewById(R.id.tv_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.tv_address);
        et_city = findViewById(R.id.et_city);
        et_state = findViewById(R.id.et_state);
        et_zipCode = findViewById(R.id.tv_zipCode);
        et_country = findViewById(R.id.et_country);
        et_email = findViewById(R.id.et_email);
        et_description = findViewById(R.id.et_description);
        et_photo = findViewById(R.id.et_photo);


        // get values from global variable in myapplication
        addressBook = ((MyApplication) getApplication()).getAddressBook();

        adapter = new PersonAdapter(newPerson.this, addressBook);



        Bundle incomingIntent = getIntent().getExtras();

        if(incomingIntent != null){
            String name = incomingIntent.getString("name");
            int phone = incomingIntent.getInt("phonenumber");
            String address = incomingIntent.getString("address");
            String city = incomingIntent.getString("city");
            String state = incomingIntent.getString("state");
            int zipCode = incomingIntent.getInt("zipcode");
            String country = incomingIntent.getString("country");
            String email = incomingIntent.getString("email");
            String description = incomingIntent.getString("description");
            String photo = incomingIntent.getString("photo");
            positionToEdit = incomingIntent.getInt("edit");

           et_name.setText(name);
           et_phone.setText(Integer.toString(phone));
           et_address.setText(address);
           et_city.setText(city);
           et_state.setText(state);
           et_zipCode.setText(Integer.toString(zipCode));
           et_country.setText(country);
           et_email.setText(email);
           et_description.setText(description);
           et_photo.setText(photo);



        }
        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent to select a photo from the gallery
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //start the intent with a request code
                startActivityForResult(i,2);
            }
        });

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BorP.class);
                startActivity(i);
            }
        });


        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        //takes inputs and defines properties
              String newName = et_name.getText().toString();
              String newPhone = et_phone.getText().toString();
              String newAddress = et_address.getText().toString();
              String newCity = et_city.getText().toString();
              String newState = et_state.getText().toString();
              String newZipCode = et_zipCode.getText().toString();
              String newCountry = et_country.getText().toString();
              String newEmail = et_email.getText().toString();
              String newDescription = et_description.getText().toString();
              String newPhoto = et_photo.getText().toString();



                PersonContact p = new PersonContact(newName, newAddress, newCity, newState,
                        newPhoto, newZipCode, newCountry,  newPhone, newEmail, newDescription);




                addressBook.getPersonList().add(p);
                adapter.notifyDataSetChanged();





                addressBook.getTheList().add(p);
                adapter.notifyDataSetChanged();



                DataService dataService = new DataService(v.getContext());
                dataService.writeAllData(addressBook, "contacts.txt");


                // intent to return to main activity
                Intent i = new Intent(v.getContext(), Home.class);

                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("address", newAddress);
                i.putExtra("city", newCity);
                i.putExtra("state", newState);
                i.putExtra("zipCode", newZipCode);
                i.putExtra("country", newCountry);
                i.putExtra("phone", newPhone);
                i.putExtra("email", newEmail);
                i.putExtra("description", newDescription);
                i.putExtra("photo", newPhoto);


                startActivity(i);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //set the image in the iv_photo view

        ImageView iv_photo;
        iv_photo = findViewById(R.id.iv_photo);
        addressBook = ( (MyApplication) this.getApplication() ).getAddressBook();
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Glide.with(this).load(currentPhotoPath).into(iv_photo);
            et_photo.setText(currentPhotoPath);

        }
        if( requestCode == SELECT_A_PHOTO && resultCode == RESULT_OK){
            Uri selectedPhoto = data.getData();
            Glide.with(this).load(selectedPhoto).into(iv_photo);



           et_photo.setText(selectedPhoto.toString());

        }

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.milestone5_2.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
