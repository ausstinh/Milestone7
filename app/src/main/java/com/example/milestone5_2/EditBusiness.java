package com.example.milestone5_2;

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
import com.example.milestone5_2.model.BusinessContact;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditBusiness extends AppCompatActivity {

    ImageView iv_back;
    Button btn_add, btn_take, btn_load;
    EditText et_name, et_phone, et_address, et_city, et_state, et_zipCode, et_country, et_opening, et_closing, et_URL, et_photo;
    ImageView iv_photo;
    int positionToEdit = -1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int SELECT_A_PHOTO = 2;
    String currentPhotoPath;

    PersonAdapter adapter;

    AddressBook addressBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_business);

        btn_take = findViewById(R.id.btn_take);
        btn_load = findViewById(R.id.btn_load);

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        et_city = findViewById(R.id.et_city);
        et_state = findViewById(R.id.et_state);
        et_zipCode = findViewById(R.id.et_zipCode);
        et_country = findViewById(R.id.et_country);
        et_opening = findViewById(R.id.et_opening);
        et_closing = findViewById(R.id.et_closing);
        et_URL = findViewById(R.id.et_URL);
        et_photo = findViewById(R.id.et_photo);

        addressBook = ((MyApplication) getApplication()).getAddressBook();
        adapter = new PersonAdapter(EditBusiness.this, addressBook);



        Bundle incomingIntent = getIntent().getExtras();

        if (incomingIntent != null) {
            String name = incomingIntent.getString("name");
            String phone = incomingIntent.getString("phonenumber");
            final String address = incomingIntent.getString("address");
            String photo = incomingIntent.getString("photo");
            String city = incomingIntent.getString("city");
            String state = incomingIntent.getString("state");
            String zipCode = incomingIntent.getString("zipcode");
            String country = incomingIntent.getString("country");
            String opening = incomingIntent.getString("opening");
            String closing = incomingIntent.getString("closing");
            String URL = incomingIntent.getString("URL");
            positionToEdit = incomingIntent.getInt("edit");

            et_name.setText(name);
            et_phone.setText(phone);
            et_address.setText(address);
            et_city.setText(city);
            et_state.setText(state);
            et_zipCode.setText(zipCode);
            et_country.setText(country);
            et_opening.setText(opening);
            et_closing.setText(closing);
            et_URL.setText(URL);
            et_photo.setText(photo);


            if(photo.startsWith("/storage")){
                Glide.with(EditBusiness.this).load(new File(photo)).into(iv_photo);
            }else
                Glide.with(EditBusiness.this).load(Uri.parse(photo)).into(iv_photo);

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

                    String newName = et_name.getText().toString();
                    String newPhone = et_phone.getText().toString();
                    String newAddress = et_address.getText().toString();
                    String newCity = et_city.getText().toString();
                    String newState = et_state.getText().toString();
                    String newZipCode = et_zipCode.getText().toString();
                    String newCountry = et_country.getText().toString();
                    String newOpeing = et_opening.getText().toString();
                    String newClosing = et_closing.getText().toString();
                    String newURL = et_URL.getText().toString();
                    String newPhoto = et_photo.getText().toString();

                    BusinessContact b = new BusinessContact(newName, newPhone, newAddress, newCity, newState, newPhoto,
                            newZipCode, newCountry, newOpeing, newClosing, newURL );

                    Intent mIntent = getIntent();
                    int intValue = mIntent.getIntExtra("contactIndex", 0);



                    addressBook.deleteContact(addressBook.getTheList().get(intValue));
                    addressBook.getTheList().add(b);
                    adapter.notifyDataSetChanged();

                    Intent i = new Intent(v.getContext(), Home.class);

                    i.putExtra("edit", positionToEdit);
                    i.putExtra("name", newName);
                    i.putExtra("address", newAddress);
                    i.putExtra("city", newCity);
                    i.putExtra("state", newState);
                    i.putExtra("zipCode", newZipCode);
                    i.putExtra("country", newCountry);
                    i.putExtra("phone", newPhone);
                    i.putExtra("opening", newOpeing);
                    i.putExtra("closing", newClosing);
                    i.putExtra("URL", newURL);

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