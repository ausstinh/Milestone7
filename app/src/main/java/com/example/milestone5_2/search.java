package com.example.milestone5_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.BaseContact;
import com.example.milestone5_2.model.PersonContact;

public class search extends AppCompatActivity {


   EditText et_search;
   Button btn_load;
   ListView lv_listOfNames;
   PersonAdapter adapter;
   ImageView iv_back;

   AddressBook addressBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        et_search = findViewById(R.id.et_search);
        lv_listOfNames = findViewById(R.id.lv_listOfNames);


        addressBook = ((MyApplication) this.getApplication()).getAddressBook();

        //Toast.makeText(this, "Length of list=" + addressBook.getTheList().size(), Toast.LENGTH_SHORT).show();

        adapter = new PersonAdapter(search.this, addressBook);
        lv_listOfNames.setAdapter(new PersonAdapter(search.this, addressBook));
        adapter.notifyDataSetChanged();


        //read from file
//        btn_load = findViewById(R.id.btn_load);
//        btn_load.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataService dataService = new DataService(v.getContext());
//                addressBook = dataService.readAllData("contacts.txt");
//                lv_listOfNames.setAdapter(new PersonAdapter(search.this, addressBook));
//            }
//        });
//               adapter.notifyDataSetChanged();



            iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Home.class);
                    startActivity(i);
                }
            });

             et_search.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 }

                 @Override
                 public void onTextChanged(CharSequence s, int start, int before, int count) {
                       AddressBook searchResults =  addressBook.search(s.toString());
                     adapter = new PersonAdapter(search.this, searchResults);
                     lv_listOfNames.setAdapter(adapter);
                    // Log.d("austin","searchResultsList = " + searchResults);
                    // Log.d("austin","results found number = " + searchResults.getTheList().size());

                     adapter.notifyDataSetChanged();
                 }

                 @Override
                 public void afterTextChanged(Editable s) {

                 }
             });
            lv_listOfNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // position = item clicked

                    //Toast.makeText(search.this, "position=" + position, Toast.LENGTH_SHORT).show();

                    BaseContact gen = addressBook.getTheList().get(position);
                    int indexOfP = 0;

                    for (int x=0; x < addressBook.getTheList().size(); x++ ){
                        if ( gen == addressBook.getTheList().get(x)) {
                            indexOfP = x ;
                        }
                    }







                    if(gen.getClass() == PersonContact.class){

                        Intent i = new Intent(view.getContext(), personContactForm.class);
                        i.putExtra("indexNumber", indexOfP);
                        startActivity(i);
                    }else{

                        Intent i = new Intent(view.getContext(), businessContactForm.class);
                        i.putExtra("indexNumber", indexOfP);
                        startActivity(i);
                    }



                }
            });




    }

}
