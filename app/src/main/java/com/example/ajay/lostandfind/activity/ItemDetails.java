package com.example.ajay.lostandfind.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.find.FindItems;

public class ItemDetails extends AppCompatActivity {
    private final static String TAG=ItemDetails.class.getName();
    private TextView name,description,contactName,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FindItems items=(FindItems)getIntent().getParcelableExtra("com.example.ajay.lostandfind.model.find.FindItems");
        name=(TextView)findViewById(R.id.name);
        description=(TextView)findViewById(R.id.description);
        contactName=(TextView)findViewById(R.id.contact_name);
        date=(TextView)findViewById(R.id.date);
        Log.d("name",items.getmName());
        Log.d("contactName",items.getmContactName());
        Log.d("description",items.getmDescription());
        Log.d("URL",items.getmImageUrl());
        name.setText(items.getmName());
        description.setText(items.getmDescription());
        contactName.setText(items.getmContactName());
        date.setText(items.getmDate());
        Toast.makeText(getApplicationContext(),items.getmName()+items.getmContactName()+items.getmDescription()+items.getmImageUrl(),Toast.LENGTH_LONG).show();

    }

}
