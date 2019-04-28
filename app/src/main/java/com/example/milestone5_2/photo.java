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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.milestone5_2.model.AddressBook;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class photo extends AppCompatActivity {

    Button btn_take, btn_load, btn_add;
    ImageView iv_photo;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int SELECT_A_PHOTO = 2;
    String currentPhotoPath;
  AddressBook addressBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        btn_load = findViewById(R.id.btn_load);
        btn_take = findViewById(R.id.btn_take);
        iv_photo = findViewById(R.id.iv_photo);
        btn_add = findViewById(R.id.btn_add);



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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), newPerson.class);
                String newPhoto = iv_photo.toString();
                i.putExtra("photo", newPhoto);
            }
        });




    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //set the image in the iv_photo view
//
//        ImageView iv_photo;
//        iv_photo = findViewById(R.id.iv_photo);
//        addressBook = ( (MyApplication) this.getApplication() ).getAddressBook();
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Glide.with(this).load(currentPhotoPath).into(iv_photo);
//
//            addressBook.add(Uri.fromFile(new File(currentPhotoPath)));
//        }
//        if( requestCode == SELECT_A_PHOTO && resultCode == RESULT_OK){
//            Uri selectedPhoto = data.getData();
//            Glide.with(this).load(selectedPhoto).into(iv_photo);
//
//
//
//            addressBook.add(selectedPhoto);
//
//        }

//    }
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
