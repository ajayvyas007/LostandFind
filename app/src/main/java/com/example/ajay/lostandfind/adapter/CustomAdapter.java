package com.example.ajay.lostandfind.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.itemproperty.Complexion;

import java.util.ArrayList;

/**
 * Created by ajay on 17/3/17.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    private Context activity;
    private ArrayList data;
    public Resources res;
    Complexion tempValues=null;
    LayoutInflater inflater;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(
            Context activitySpinner,
            int textViewResourceId,
            ArrayList objects
    )
    {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data     = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.row, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (Complexion) data.get(position);

        TextView label        = (TextView)row.findViewById(R.id.weekofday);
        label.setText(tempValues.getmType());


        return row;
    }
}
