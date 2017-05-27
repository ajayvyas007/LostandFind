package com.example.ajay.lostandfind.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ajay.lostandfind.R;
import com.example.ajay.lostandfind.adapter.addressadapter.CitySpinAdapter;
import com.example.ajay.lostandfind.adapter.addressadapter.CountrySpinAdapter;
import com.example.ajay.lostandfind.adapter.addressadapter.StateSpinAdapter;
import com.example.ajay.lostandfind.adapter.itemproperty.BodyTypeSpinAdapter;
import com.example.ajay.lostandfind.adapter.itemproperty.CardTypeSpinAdapter;
import com.example.ajay.lostandfind.adapter.itemproperty.ColorTypeSpinAdapter;
import com.example.ajay.lostandfind.adapter.itemproperty.ComplexionSpinAdapter;
import com.example.ajay.lostandfind.adapter.itemproperty.GenderTypeSpinAdapter;
import com.example.ajay.lostandfind.library.Config;
import com.example.ajay.lostandfind.library.VolleySingleton;
import com.example.ajay.lostandfind.model.address.City;
import com.example.ajay.lostandfind.model.address.Country;
import com.example.ajay.lostandfind.model.address.State;
import com.example.ajay.lostandfind.model.itemproperty.BodyType;
import com.example.ajay.lostandfind.model.itemproperty.CardType;
import com.example.ajay.lostandfind.model.itemproperty.Color;
import com.example.ajay.lostandfind.model.itemproperty.Complexion;
import com.example.ajay.lostandfind.model.itemproperty.Gender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG=AddItemFragment.class.getSimpleName();
    private RadioGroup radioGroup;
    private RadioButton lostButton,findButton;
    private static String mCountryId;
    private static String mStateId;
    private ImageView mImage;
    private Button mAddFromGallery,mAddFromCamera;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int RESULT_LOAD_IMAGE = 1;
    private OnFragmentInteractionListener mListener;
    private RequestQueue volley;
    private ArrayList<Color> colorList;
    private ArrayList<Gender> genderList;
    private ArrayList<Complexion> complexionList;
    private ArrayList<BodyType> bodyTypeList;
    private ArrayList<CardType> cardTypeList;
    private List<Country> countryList;
    private List<State> stateList;
    private List<City> cityList;
    private static final int CAMERA_REQUEST = 1888;

    private ProgressDialog mProgress;
    View missing_candidate_human_being_layout_section;
    View contact_person_human_being_layout_section,mAddImageSection;
    View violet_section,vehicle_section,id_card_section,phone_section,electronic_section,keys_section,money_section,missing_place_section;
    private Spinner mLostFindSpinner,mItemSpinner,mAgeSpinner,mHeightSpinner,mComplectionSpinner,mBodySpinner,mGenderSpinner,mCountrySpinner,mStateSpinner,mCitySpinner;
    private AutoCompleteTextView mCountry,mState,mCity;
    private Spinner mColorTypVioletSpinner,mColorTypeVehicleSpinner,mIdCardSpinner,mPhoneColourSpinner,mElectronicColorSpinner,mKeysColourSpinner;
    private static StateSpinAdapter mStateAdapter;
    private static GenderTypeSpinAdapter mGenderAdapter;
    private static ComplexionSpinAdapter mComplectionAdapter;
    private static BodyTypeSpinAdapter mBodyTypeAdapter;
    private static CountrySpinAdapter mCountryAdapter;
    private static CitySpinAdapter mCityAdapter;
    private static ColorTypeSpinAdapter mColorAdapter;
    private static CardTypeSpinAdapter mCardAdapter;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
        colorList = new ArrayList<Color>();
        complexionList=new ArrayList<Complexion>();
        bodyTypeList=new ArrayList<BodyType>();
        cardTypeList=new ArrayList<CardType>();
        genderList=new ArrayList<Gender>();
        countryList=new ArrayList<Country>();
        stateList=new ArrayList<State>();
        cityList=new ArrayList<City>();
        volley = VolleySingleton.getInstance(getActivity()).getRequestQueue();


    }

    private static final String[] CATEGORY = new String[] {
            "Choose Missing Thing","Human Being", "Violet/Purse/Bags/Attache", "Vehicle", "Id Card/Payment", "Phone/Mobile","Laptop/Electronic","Keys","Money","Other"
    };
    private Spinner spin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_item, container, false);

        missing_candidate_human_being_layout_section=(View)view.findViewById(R.id.missing_candidate_human_being_layout_section);
        missing_place_section=(View)view.findViewById(R.id.missing_place_details_layout_section);
        violet_section=(View)view.findViewById(R.id.voilet_missing_layout_section);
        contact_person_human_being_layout_section=(View)view.findViewById(R.id.contact_person_human_being_layout_section);
        vehicle_section=(View)view.findViewById(R.id.vehicle_missing_layout_section);
        id_card_section=(View)view.findViewById(R.id.missing_id_card_layout_section);
        phone_section=(View)view.findViewById(R.id.missing_phone_layout_section);
        electronic_section=(View)view.findViewById(R.id.missing_electronic_layout_section);
        keys_section=(View)view.findViewById(R.id.missing_keys_layout_section);
        mAddImageSection=(View)view.findViewById(R.id.add_image_section);


        radioGroup = (RadioGroup)view.findViewById(R.id.lost_find_radiogroup);
        lostButton=(RadioButton)view.findViewById(R.id.lost_radio);
        findButton=(RadioButton)view.findViewById(R.id.find_radio);
        money_section=(View)view.findViewById(R.id.missing_money_layout_section);
        mKeysColourSpinner = (Spinner)view.findViewById(R.id.color_type_of_key_spinner);
        mElectronicColorSpinner=(Spinner)view.findViewById(R.id.electronic_color_type_spinner);
        mPhoneColourSpinner=(Spinner)view.findViewById(R.id.missing_phone_colour_type_spinner);
        mComplectionSpinner=(Spinner)view.findViewById(R.id.comlexion_spinner);
        mBodySpinner=(Spinner)view.findViewById(R.id.body_type_spinner);
        mGenderSpinner=(Spinner)view.findViewById(R.id.gender_type_spinner);
        mColorTypVioletSpinner=(Spinner)view.findViewById(R.id.color_type_voilet_spinner);
        mColorTypeVehicleSpinner=(Spinner)view.findViewById(R.id.color_type_vehicle_spinner);
        mComplectionSpinner.setOnItemSelectedListener(this);
        mBodySpinner.setOnItemSelectedListener(this);
        mIdCardSpinner=(Spinner)view.findViewById(R.id.missing_id_card_type_spinner);
        mImage=(ImageView)view.findViewById(R.id.image_show);
        mAddFromCamera=(Button)view.findViewById(R.id.camera_button);
        mAddFromCamera.setOnClickListener(this);
        mAddFromGallery=(Button)view.findViewById(R.id.gallery_button);
        mAddFromGallery.setOnClickListener(this);
        mCountry=(AutoCompleteTextView)view.findViewById(R.id.country_autocomplete);
        mCountry.setThreshold(1);
        mCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),mCountryAdapter.getItem(position).getmName().toString(),Toast.LENGTH_LONG).show();
                Log.d("CountryIdOnclick",mCountryAdapter.getItem(position).getmID().toString());
                mCountryId=mCountryAdapter.getItem(position).getmID().toString();
                new BackGroundStateTask().execute();
            }
        });
        mCountry.setOnItemSelectedListener(this);
        mState=(AutoCompleteTextView)view.findViewById(R.id.state_autocomplete);
        mState.setThreshold(1);
        mState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStateId=mStateAdapter.getItem(position).getmID().toString();
                new BackGroundCityTask().execute();
            }
        });
        mCity=(AutoCompleteTextView)view.findViewById(R.id.city_autocomplete);
        mCity.setThreshold(1);
        mCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("City",mCityAdapter.getItem(position).getmID().toString());
            }
        });


        spin = (Spinner)view.findViewById(R.id.spinner2);

        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,CATEGORY);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);



        if((complexionList.size() ==0) && (bodyTypeList.size()==0) && (countryList.size()==0))
        {
            Log.d("EMPTY","Called");
            new BackGroundTask().execute();
        }
        else {
            mComplectionSpinner.setAdapter(mComplectionAdapter);
            mBodySpinner.setAdapter(mBodyTypeAdapter);
            mGenderSpinner.setAdapter(mGenderAdapter);
            mCountry.setAdapter(mGenderAdapter);
            mColorTypVioletSpinner.setAdapter(mColorAdapter);
            mColorTypeVehicleSpinner.setAdapter(mColorAdapter);
            mIdCardSpinner.setAdapter(mCardAdapter);
            mPhoneColourSpinner.setAdapter(mColorAdapter);
            mElectronicColorSpinner.setAdapter(mColorAdapter);
            mKeysColourSpinner.setAdapter(mColorAdapter);
        }
        return view;
    }



    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // This check which radio button was clicked
        switch (view.getId()) {
            case R.id.lost_radio:
                if (checked)
                    //Do something when radio button is clicked
                    Toast.makeText(getActivity(), "It seems like you feeal RelativeLayout easy", Toast.LENGTH_SHORT).show();
                break;

            case R.id.find_radio:
                //Do something when radio button is clicked
                Toast.makeText(getActivity(), "It seems like you feel LinearLayout easy", Toast.LENGTH_SHORT).show();
                break;
        }
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.camera_button)
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        else if(v.getId()==R.id.gallery_button)
        {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mImage.setImageBitmap(bmp);

        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImage.setImageBitmap(photo);
        }


    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.spinner2)
        {
            Log.d("MSG",String.valueOf(CATEGORY[position].toString()));
            switch (CATEGORY[position].toString())
            {
                case "Human Being":
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),CATEGORY[position] ,Toast.LENGTH_LONG).show();
                    missing_candidate_human_being_layout_section.setVisibility(View.VISIBLE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    break;
                case "Violet/Purse/Bags/Attache":
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    violet_section.setVisibility(View.VISIBLE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    break;
                case "Vehicle":
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    break;
                case "Id Card/Payment":
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.VISIBLE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);

                    break;
                case "Phone/Mobile":
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.VISIBLE);
                    electronic_section.setVisibility(View.GONE);
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    break;
                case "Laptop/Electronic":
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.VISIBLE);
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.GONE);
                    break;
                case "Keys":
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);
                    keys_section.setVisibility(View.VISIBLE);
                    money_section.setVisibility(View.GONE);
                    break;
                case "Money":
                    missing_candidate_human_being_layout_section.setVisibility(View.GONE);
                    missing_place_section.setVisibility(View.VISIBLE);
                    mAddImageSection.setVisibility(View.VISIBLE);
                    contact_person_human_being_layout_section.setVisibility(View.VISIBLE);
                    violet_section.setVisibility(View.GONE);
                    vehicle_section.setVisibility(View.GONE);
                    id_card_section.setVisibility(View.GONE);
                    phone_section.setVisibility(View.GONE);
                    electronic_section.setVisibility(View.GONE);
                    keys_section.setVisibility(View.GONE);
                    money_section.setVisibility(View.VISIBLE);
                    break;
            }
        }
        else if(parent.getId()==R.id.comlexion_spinner)
        {
            Log.d("MSG",String.valueOf(complexionList.get(position).toString()));
            Toast.makeText(getActivity(), complexionList.get(position).getmType(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    private void getColor()
    {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.COLOUR_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    Log.d(TAG,response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("colour");
                        Log.d(TAG,data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String id = Obj.getString("id");
                                    String type=Obj.getString("text");
                                    Log.d("ValuesGET",id+type);

                                    Color color=new Color(id,type);

                                    colorList.add(i,color);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }

                    }
                    mColorAdapter=new ColorTypeSpinAdapter(getActivity(),android.R.layout.simple_spinner_item,colorList);
                    mColorTypVioletSpinner.setAdapter(mColorAdapter);
                    mColorTypeVehicleSpinner.setAdapter(mColorAdapter);


                    mPhoneColourSpinner.setAdapter(mColorAdapter);
                    mElectronicColorSpinner.setAdapter(mColorAdapter);
                    mKeysColourSpinner.setAdapter(mColorAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }

    private void getComplexion()
    {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.COMPLEXION_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("complexion");
                        Log.d(TAG,data.toString());


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
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }
                        Log.d("Value","COMPLETION_SIZE"+complexionList.size());
                        mComplectionAdapter=new ComplexionSpinAdapter(getActivity(),android.R.layout.simple_spinner_item,complexionList);
                        mComplectionSpinner.setAdapter(mComplectionAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }

    private void getBodyType()
    {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.BODY_TYPE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("body_type");
                        Log.d(TAG,data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String id = Obj.getString("id");
                                    String type=Obj.getString("text");
                                    Log.d("ValuesGET",id+type);

                                    BodyType bodyType=new BodyType(id,type);

                                    bodyTypeList.add(i,bodyType);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }

                    }
                    Log.d("Value","BODY_TYPE_SIZE"+bodyTypeList.size());
                    mBodyTypeAdapter=new BodyTypeSpinAdapter(getActivity(),android.R.layout.simple_spinner_item,bodyTypeList);
                    mBodySpinner.setAdapter(mBodyTypeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }

    private void getCardType()
    {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.CARD_TYPE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("card_type");
                        Log.d(TAG,data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String id = Obj.getString("id");
                                    String type=Obj.getString("text");
                                    Log.d("ValuesGET",id+type);

                                    CardType cardType=new CardType(id,type);

                                    cardTypeList.add(i,cardType);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mCardAdapter=new CardTypeSpinAdapter(getActivity(),android.R.layout.simple_spinner_item,cardTypeList);
                mIdCardSpinner.setAdapter(mCardAdapter);

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }

    private class BackGroundTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            getComplexion();
            getCardType();
            getBodyType();
            getColor();
            getGenderType();
            getCountry();

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (mProgress.isShowing())
                mProgress.dismiss();
        }
    }

    private class BackGroundStateTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();*/
            mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            getState();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (mProgress.isShowing())
                mProgress.dismiss();

        }
    }

    private void getState() {
        if(mCountryId != null) {
            Log.d("getState",Config.STATE_NAMES.concat(mCountryId));
            stateList.clear();
            volley.add(new JsonObjectRequest(Request.Method.POST, Config.STATE_NAMES.concat(mCountryId), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {
                        if (response.getString("status").toString().equals("success")) {
                            //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                            Log.d(TAG, "True");
                            JSONArray data = response.getJSONArray("states");
                            Log.d(TAG, data.toString());


                            if (data.length() > 0) {

                                // looping through json and adding to movies list
                                for (int i = 0; i < data.length(); i++) {
                                    try {
                                        JSONObject Obj = data.getJSONObject(i);

                                        String id = Obj.getString("id");
                                        String name = Obj.getString("name");
                                        String countryid=Obj.getString("country_id");
                                        Log.d("StateGET", id +name);

                                        State state = new State(id, name,countryid);

                                        stateList.add(i, state);

                                    } catch (JSONException e) {
                                        Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                    }
                                }

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mStateAdapter = new StateSpinAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, stateList);
                    mState.setAdapter(mStateAdapter);

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
        }

    }

    private void getCountry() {
        volley.add(new JsonObjectRequest(Request.Method.POST, Config.COUNTRY_NAMES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,response.toString());

                try {
                    if(response.getString("status").toString().equals("success"))
                    {
                        //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"True");
                        JSONArray data=response.getJSONArray("countries");
                        Log.d(TAG,data.toString());


                        if (data.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject Obj = data.getJSONObject(i);

                                    String id = Obj.getString("id");
                                    String shortName=Obj.getString("sortname");
                                    String name=Obj.getString("name");
                                    String phonecode=Obj.getString("phonecode");
                                    Log.d("CountryGET",id+shortName+phonecode+name);

                                    Country country=new Country(id,name,shortName,phonecode);

                                    countryList.add(i,country);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mCountryAdapter=new CountrySpinAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line,countryList);
                mCountry.setAdapter(mCountryAdapter);

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

    }

    private void getGenderType() {


        genderList.add(new Gender("1","Male"));
        genderList.add(new Gender("2","Femail"));


        Log.d("Value","GENDER_SIZE"+genderList.size());
        mGenderAdapter=new GenderTypeSpinAdapter(getActivity(),android.R.layout.simple_spinner_item,genderList);
        mGenderSpinner.setAdapter(mGenderAdapter);


    }


    private class BackGroundCityTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();*/
            mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Please Wait...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            getCity();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (mProgress.isShowing())
                mProgress.dismiss();

        }
    }

    private void getCity() {

        if(mStateId != null) {
            Log.d("getState",Config.CITY_NAMES.concat(mStateId));
            if(cityList.size()!= 0)
            {
                cityList.clear();
            }
            volley.add(new JsonObjectRequest(Request.Method.POST, Config.CITY_NAMES.concat(mStateId), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {
                        if (response.getString("status").toString().equals("success")) {
                            //Toast.makeText(getActivity(),"Value Gat",Toast.LENGTH_LONG).show();
                            Log.d(TAG, "True");
                            JSONArray data = response.getJSONArray("cities");
                            Log.d(TAG, data.toString());


                            if (data.length() > 0) {

                                // looping through json and adding to movies list
                                for (int i = 0; i < data.length(); i++) {
                                    try {
                                        JSONObject Obj = data.getJSONObject(i);

                                        String id = Obj.getString("id");
                                        String name = Obj.getString("name");
                                        String countryid=Obj.getString("state_id");
                                        Log.d("StateGET", id +name);

                                        City city = new City(id, name,countryid);

                                        cityList.add(i, city);

                                    } catch (JSONException e) {
                                        Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                    }
                                }

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mCityAdapter = new CitySpinAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, cityList);
                    mCity.setAdapter(mCityAdapter);

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
        }

    }
}
