package com.example.ajay.lostandfind.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.adapter.SwipeListAdapter;
import com.example.ajay.lostandfind.library.VolleySingleton;
import com.example.ajay.lostandfind.model.find.FindItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    private String TAG = FindFragment.class.getSimpleName();

    private String URL_TOP_250 = "http://zouple.com/lostfind/services/item.php?flag=L";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<FindItems> findList;

    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_find, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);

        findList = new ArrayList<>();
        adapter = new SwipeListAdapter(getActivity(), findList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getData();
                                    }
                                }
        );
        return view;
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        getData();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Fetching movies json by making http call
     */
    private void getData()
    {
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = URL_TOP_250 /*+ offSet*/;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("item");
                        Log.d(TAG,data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String name = Obj.getString("name");
                                    String description=Obj.getString("description");
                                    String contact_name=Obj.getString("contact_name");
                                    String image=Obj.getString("image");
                                    String date=Obj.getString("date");
                                    Log.d("ValuesGET",name+description+contact_name+image+date);

                                    FindItems m = new FindItems(0, name,contact_name,description,image,date);

                                    findList.add(0, m);

                                    // updating offset value to highest value
                                    /*if (rank >= offSet)
                                        offSet = rank;*/

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*if (response.length() > 0) {

                    // looping through json and adding to movies list
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject movieObj = response.getJSONObject(i);

                            int rank = movieObj.getInt("rank");
                            String title = movieObj.getString("title");

                            Movie m = new Movie(rank, title);

                            movieList.add(0, m);

                            // updating offset value to highest value
                            if (rank >= offSet)
                                offSet = rank;

                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                        }
                    }

                    adapter.notifyDataSetChanged();
                }*/

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        // Adding request to request queue
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjReq);
    }

}
