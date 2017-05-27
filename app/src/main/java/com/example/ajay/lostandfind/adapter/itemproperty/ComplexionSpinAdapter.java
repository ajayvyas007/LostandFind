package com.example.ajay.lostandfind.adapter.itemproperty;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.itemproperty.Complexion;

import java.util.ArrayList;

/**
 * Created by ajay on 16/3/17.
 */

public class ComplexionSpinAdapter extends ArrayAdapter<Complexion> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Complexion> values;
    private Complexion complexion=null;
    private LayoutInflater inflater;

    public ComplexionSpinAdapter(Context ctx, int textViewResourceId,
                                 ArrayList<Complexion> values) {
        super(ctx, textViewResourceId, values);
        this.context = ctx;
        this.values = values;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return values.size();
    }

    public Complexion getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
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
        complexion = null;
        complexion = (Complexion) values.get(position);

        TextView label = (TextView)row.findViewById(R.id.weekofday);
        label.setTextSize(18);
        label.setText(complexion.getmType());


        return row;
    }
}