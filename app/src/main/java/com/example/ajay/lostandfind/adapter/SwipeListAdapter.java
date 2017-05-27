package com.example.ajay.lostandfind.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.activity.ItemDetails;
import com.example.ajay.lostandfind.model.find.FindItems;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ajay.lostandfind.R.id.imageView;
import static com.example.ajay.lostandfind.R.id.view_offset_helper;

/**
 * Created by ajay on 9/3/17.
 */

public class SwipeListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FindItems> mFindItemList;
    private FindItems items;

    public SwipeListAdapter(Context ctx,List<FindItems> movieList) {
        this.mFindItemList = movieList;
        this.mContext=ctx;
    }

    @Override
    public int getCount() {
        return mFindItemList.size();
    }

    @Override
    public Object getItem(int location) {
        return mFindItemList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (mInflater == null)
            mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_row, null);

        CircleImageView circleImageView=(CircleImageView)convertView.findViewById(R.id.profile_image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView contactName = (TextView) convertView.findViewById(R.id.contact_name);
        items=mFindItemList.get(position);
        name.setText(items.getmName());
        contactName.setText(items.getmContactName());
        Toast.makeText(mContext,items.getmDate().toString(),Toast.LENGTH_LONG).show();

        Glide.with(mContext).load(items.getmImageUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Position:"+position,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(mContext,ItemDetails.class);
                intent.putExtra("com.example.ajay.lostandfind.model.find.FindItems",items);
                Log.d("objValue",items.getmName());
                Log.d("objValue",items.getmContactName());
                Log.d("objValue",items.getmDescription());
                Log.d("objValue",items.getmImageUrl());
                Log.d("objValue",items.getmDate());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
