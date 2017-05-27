package com.example.ajay.lostandfind.adapter.addressadapter;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.address.Country;

/**
 * Created by ajay on 19/3/17.
 */

public class CountrySpinAdapter extends ArrayAdapter<Country> implements Filterable {

    private final Context mContext;
    private final List<Country> mDepartments;
    private final List<Country> mDepartments_All;
    private final List<Country> mDepartments_Suggestion;
    private final int mLayoutResourceId;

    public CountrySpinAdapter(Context context, int resource, List<Country> departments) {
        super(context, resource, departments);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mDepartments = new ArrayList<>(departments);
        this.mDepartments_All = new ArrayList<>(departments);
        this.mDepartments_Suggestion = new ArrayList<>();
    }

    public int getCount() {
        return mDepartments.size();
    }

    public Country getItem(int position) {
        return mDepartments.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row, parent, false);
            }
            Country department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.weekofday);
            name.setText(department.getmName()+"-"+department.getmShortName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                StringBuffer result=new StringBuffer(((Country) resultValue).getmName());
                result.append("-");
                result.append(((Country) resultValue).getmShortName());
                return String.valueOf(result);
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mDepartments_Suggestion.clear();
                    for (Country department : mDepartments_All) {
                        if (department.getmName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mDepartments_Suggestion.add(department);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mDepartments_Suggestion;
                    filterResults.count = mDepartments_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDepartments.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    List<Country> result = (List<Country>) results.values;
                    for (Country object : result) {
                        if (object instanceof Country) {
                            mDepartments.add((Country) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mDepartments.addAll(mDepartments_All);
                }
                notifyDataSetChanged();
            }
        };
    }

}
