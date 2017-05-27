package com.example.ajay.lostandfind.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.adapter.CustomAdapter;
import com.example.ajay.lostandfind.adapter.SpinnerModel;
import com.example.ajay.lostandfind.adapter.itemproperty.ComplexionSpinAdapter;
import com.example.ajay.lostandfind.library.Config;
import com.example.ajay.lostandfind.library.VolleySingleton;
import com.example.ajay.lostandfind.model.itemproperty.Complexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<Complexion> complexionList=new ArrayList<Complexion>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String[] DayOfWeek = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};

    public LostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostFragment newInstance(String param1, String param2) {
        LostFragment fragment = new LostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private RequestQueue volley;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volley = VolleySingleton.getInstance(getActivity()).getRequestQueue();


    }


    public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();
    public void setListData()
    {

        // Now i have taken static values by loop.
        // For further inhancement we can take data by webservice / json / xml;

        for (int i = 0; i < 11; i++) {

            final SpinnerModel sched = new SpinnerModel();

            /******* Firstly take data in model object ******/
            sched.setCompanyName("Company "+i);
            sched.setImage("image"+i);
            sched.setUrl("http:\\www."+i+".com");

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add(sched);
        }

    }
private Spinner mySpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lost, container, false);
        new BackGroundTask().execute();
        Log.d("Called","Secound");
        mySpinner = (Spinner)view.findViewById(R.id.spinner);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SELECTEDValue",complexionList.get(position).getmType());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
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


    private class BackGroundTask extends AsyncTask<Void,Void,Void>
    {
        private ProgressDialog mProgress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... arg) {

            getComplexion();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mProgress.isShowing())
                mProgress.dismiss();

            Toast.makeText(getContext(),"Called",Toast.LENGTH_LONG).show();
            Log.d("Called","First"+complexionList.size());
        }
    }




    private void populateSpinner() {

    }












    /*public class MyCustomAdapter extends ArrayAdapter<String> {

        private LostFragment context;
        // Your custom values for the spinner (User)
        private ArrayList<Complexion> values;
        public Resources ress;
        LayoutInflater inflater;
        Complexion complexion=null;
        public MyCustomAdapter(LostFragment context, int textViewResourceId,
                                     ArrayList values,Resources res) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
            ress=res;
            inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(),"selected",Toast.LENGTH_LONG).show();
            return getCustomView(position, convertView, parent);

        }



        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);
            complexion = null;
            complexion = values.get(position);
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.weekofday);
            label.setText(complexion.getmType());
            if(position==0){

                // Default selected Spinner item
                label.setText("Please select Complection");

            }
            else
            {
                // Set values for spinner each row
                label.setText(complexion.getmType());

            }


            return row;
        }
    }*/

    private void getComplexion()
    {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.COMPLEXION_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VAlue",response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d("Value","True");
                        JSONArray data=response.getJSONArray("complexion");
                        Log.d("Value",data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String id = Obj.getString("id");
                                    String type=Obj.getString("text");
                                    Log.d("ValuesGET",id+type);

                                    Complexion complexion=new Complexion(id,type);

                                    complexionList.add(i,complexion);

                                } catch (JSONException e) {
                                    Log.e("Value", "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Size",String.valueOf(complexionList.size()));
                mySpinner.setAdapter(new ComplexionSpinAdapter(getActivity(), R.layout.row, complexionList));

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }
}
