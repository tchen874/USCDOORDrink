package com.example.uscdoordrink;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag1_overviewMerch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag1_overviewMerch extends Fragment {

    String weather_url = "";
    String api_key = "6e885470367f4e05810cfd5a93c995d0";
    TextView textView;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location lastKnownLocation;
    private final LatLng defaultLocation = new LatLng(34.0224, 118.2851);

    private FusedLocationProviderClient fusedLocationClient;


    public frag1_overviewMerch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment frag1_overviewMerch.
     */
    // TODO: Rename and change types and number of parameters
    public static frag1_overviewMerch newInstance() {
        frag1_overviewMerch fragment = new frag1_overviewMerch();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag1_overview_merch, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        textView = v.findViewById(R.id.textView);
        Log.e("lat", weather_url);
        // on clicking this button function to
        // get the coordinates will be called
        Button btVar1 = (Button) v.findViewById(R.id.btVar1);
        Log.e("print anything", "testing");

        btVar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("afterSetOnClickListener " + fusedLocationClient);
                Log.e("lat", "onClick");
                obtainLocation();
                // of the last location
                // and create the http URL
                //weather_url = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location.getL + "&lon=" + defaultLocation.longitude + "&key=" + api_key;
                //getTemp();
            }
        });

        return v;
    }
    @SuppressLint("MissingPermission")
    private void obtainLocation() {
        Log.e("lat", "function");
        // get the last location
        getDeviceLocation();
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // and create the http URL
                        weather_url = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&key=" + api_key;
                        Log.e("lat", weather_url);
                        System.out.println("in obtainLocation fusedLocClient " + fusedLocationClient);
                        System.out.println("log: " + Log.e("lat", weather_url));
                        System.out.println("location=: " + location.getLatitude());
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
        System.out.println("queue within getTemp() " + queue);
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
                            System.out.println("JSONARRAY: " + arr);
                            System.out.println("JSONOBJ: " + obj);

                            JSONObject obj2 = arr.getJSONObject(0);
                            Log.e("lat obj2", obj2.toString());
                            double fahrenheit = Double.parseDouble(obj2.getString("temp"));
                            fahrenheit = (fahrenheit *(1.8) + 32);
                            String weather = " Since it is " + fahrenheit + " degrees fahrenheit in " + obj2.getString("city_name") + " where you're located,";
                            //calculate what to recommend user to drink depending on weather
                            int lowTemp = 46;
                            int roomTemp = 72;
                            int highTemp = 89;
                            if (fahrenheit <= lowTemp) {
                                weather += " we recommend you sell hot beverages today since it is cold outside! Warm up your customers ";
                            }
                            if ((fahrenheit > lowTemp) &&  (fahrenheit < roomTemp)) {
                                weather += " we recommend you sell warm beverages today since it is slightly below room temperature outside! ";
                            }
                            if ((fahrenheit >= roomTemp) &&  (fahrenheit < highTemp)) {
                                weather += " we recommend you sell cold beverages today since it is above room temperature outside! ";
                            }
                            if ((fahrenheit >= highTemp) ) {
                                weather += " we recommend you sell cold beverages IMMEDIATELY today since it is very hot outside! ";
                            }
                            textView.setText(weather);
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
        System.out.println("queue before: " + queue);
        queue.add(stringRequest);
        System.out.println("queue after: " + queue);

    }
    private void getLocationPermission () {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                //get current location
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) { //if not null, move camera
                            lastKnownLocation = location;
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

}