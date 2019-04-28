package com.example.milestone5_2;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.milestone5_2.R;
import com.example.milestone5_2.model.AddressBook;
import com.example.milestone5_2.model.BaseContact;
import com.example.milestone5_2.model.BusinessContact;
import com.example.milestone5_2.model.PersonContact;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonAdapter extends BaseAdapter {

    Activity mActivity;
    AddressBook addressBook;


    public PersonAdapter(Activity mActivity, AddressBook addressBook) {
        this.mActivity = mActivity;
        this.addressBook = addressBook;
    }

    @Override
    public int getCount() {
        return addressBook.getTheList().size();
    }

    @Override
    public BaseContact getItem(int position) {
        return addressBook.getTheList().get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View onePersonLine;


        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onePersonLine = inflater.inflate(R.layout.contact_one_line, parent, false);



        ImageView iv_person = onePersonLine.findViewById(R.id.iv_person);
        TextView tv_name = onePersonLine.findViewById(R.id.tv_name);



        BaseContact b = this.getItem(position);
        if(b.getPhoto().startsWith("/storage")){
            Glide.with(mActivity).load(new File(b.getPhoto())).into(iv_person);

        }else {
            Glide.with(mActivity).load(Uri.parse(b.getPhoto())).into(iv_person);
        }


       tv_name.setText(b.getName());








        return onePersonLine;
    }

}
