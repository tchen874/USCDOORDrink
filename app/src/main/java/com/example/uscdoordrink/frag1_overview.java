package com.example.uscdoordrink;

import android.annotation.SuppressLint;
import android.app.SyncNotedAppOp;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag1_overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag1_overview extends Fragment {

    public frag1_overview() {
        // Required empty public constructor
    }

    String weather_url = "";
    String api_key ="6e885470367f4e05810cfd5a93c995d0";
    TextView textView;

    private FusedLocationProviderClient fusedLocationClient;

    public static frag1_overview newInstance() {
        frag1_overview fragment = new frag1_overview();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //simple drink/weather recommendation toast here!

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //just display text welcoming the user
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag1_overview, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        textView = v.findViewById(R.id.textView);


        Log.e("lat", weather_url);
        // on clicking this button function to
        // get the coordinates will be called
        Button btVar1 = (Button) v.findViewById(R.id.btVar1);

        btVar1.setOnClickListener(v1 -> {
            System.out.println("afterSetOnClickListener " + fusedLocationClient);
            Log.e("lat", "onClick");
            // function to find the coordinates
            // of the last location
            obtainLocation();
        });
        return inflater.inflate(R.layout.fragment_frag1_overview, container, false);
    }


    @SuppressLint("MissingPermission")
    private void obtainLocation(){
        Log.e("lat", "function");
        // get the last location

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // and create the http URL
                weather_url = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&key=" + api_key;
                Log.e("lat", weather_url);
                System.out.println("in obtainLocation fusedLocClient " + fusedLocationClient);
                System.out.println("log: "+ Log.e("lat", weather_url));
                System.out.println("location=: "+ location.getLatitude());
                // this function will
                // fetch data from URL
                getTemp();
            }
        });

        }

        private void getTemp() {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = weather_url;
            Log.e("lat", url);
            System.out.println("queue within getTemp() "+ queue );
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.e("lat ", response.toString());
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray arr = obj.getJSONArray("data");
                                System.out.println("JSONARRAY: "+ arr );
                                System.out.println("JSONOBJ: "+ obj );

                                JSONObject obj2 = arr.getJSONObject(0);
                                Log.e("lat obj2", obj2.toString());
                                textView.setText(obj2.getString("temp")+ " deg Celsius in " + obj2.getString("city_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("That didn't work ");
                }
            });
            System.out.println("queue before: "+ queue );
            queue.add(stringRequest);
            System.out.println("queue after: "+ queue );

        }

    }