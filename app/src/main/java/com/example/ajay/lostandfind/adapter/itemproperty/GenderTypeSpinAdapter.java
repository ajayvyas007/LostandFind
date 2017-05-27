package com.example.ajay.lostandfind.adapter.itemproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.itemproperty.Gender;

import java.util.ArrayList;

/**
 * Created by ajay on 18/3/17.
 */

public class GenderTypeSpinAdapter extends ArrayAdapter<Gender> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Gender> values;
    private Gender data=null;
    private LayoutInflater inflater;

    public GenderTypeSpinAdapter(Context ctx, int textViewResourceId,
                               ArrayList<Gender> values) {
        super(ctx, textViewResourceId, values);
        this.context = ctx;
        this.values = values;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return values.size();
    }

    public Gender getItem(int position) {
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
        data = null;
        data = (Gender) values.get(position);

        TextView label = (TextView)row.findViewById(R.id.weekofday);
        label.setTextSize(18);
        label.setText(data.getType());


        return row;
    }
}
