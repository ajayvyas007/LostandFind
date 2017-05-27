package com.example.ajay.lostandfind.adapter.addressadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.model.address.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajay on 19/3/17.
 */

public class StateSpinAdapter extends ArrayAdapter<State> implements Filterable {
    private final Context mContext;
    private final List<State> mDepartments;
    private final List<State> mDepartments_All;
    private final List<State> mDepartments_Suggestion;
    private final int mLayoutResourceId;

    public StateSpinAdapter(Context context, int resource, List<State> departments) {
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

    public State getItem(int position) {
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
            State department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.weekofday);
            name.setText(department.getmName());
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
                return ((State) resultValue).getmName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mDepartments_Suggestion.clear();
                    for (State department : mDepartments_All) {
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
                    List<State> result = (List<State>) results.values;
                    for (State object : result) {
                        if (object instanceof State) {
                            mDepartments.add((State) object);
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
