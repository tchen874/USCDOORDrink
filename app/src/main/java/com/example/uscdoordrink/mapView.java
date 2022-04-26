package com.example.uscdoordrink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Locale;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.os.Handler;


//imports for directions
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;





//code from google maps tutorial https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial

public class mapView extends AppCompatActivity
        implements OnMapReadyCallback, java.io.Serializable{

    DrawerLayout drawerLayout;
    private static final String TAG = mapView.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;

    // The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-34.0224, 118.2851);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;
    private Store s;

    // Keys for storing activity state.
    // [START maps_current_place_state_keys]
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    // [END maps_current_place_state_keys]


    // array list for all merchants
    private ArrayList<LatLng> locationArrayList;
    //array list for store names
    private ArrayList<String> nameArrayList;

    private ArrayList<String> MerchantUidList;
    private ArrayList<Drink> cart;
    // For click marker
    boolean doubleBackToExitPressedOnce = false;

    //polyline for directions
    private Polyline mPolyline;

    boolean isDriving = true;


    // create map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Retrieve location and camera position from saved instance state.
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }

        // view map
        setContentView(R.layout.activity_mapview);

        //buttons
        Button drivingButton = findViewById(R.id.drivingbutton);
        Button bikingButton = findViewById(R.id.bikingbutton);


        //if click driving button
        drivingButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 System.out.println("Driving button clicked");
                 //change boolean
                 isDriving = true;
                 //change current mode text
                TextView tv = (TextView)findViewById(R.id.textCurrentMode);
                tv.setText("Driving");

             }
        });

        //if click driving button
        bikingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Biking button clicked");
                //change boolean
                isDriving = false;
                //change current mode text
                TextView tv = (TextView)findViewById(R.id.textCurrentMode);
                tv.setText("Biking");
            }
        });

        //initialize it
        Places.initialize(getApplicationContext(), "AIzaSyCWDouECJGV1idsJfVU7lMf4Nj22_nUzIo");
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        // Build the map
        // get SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawerLayout = findViewById(R.id.user_drawer_layout);
        //initialize arraylist
        locationArrayList = new ArrayList<>();
        nameArrayList = new ArrayList<>();
        MerchantUidList = new ArrayList<>();
        cart = new ArrayList<Drink>();

        // EXAMPLE ADDING TO ARRAYLIST - TODO!!
        //Query query = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Menu");
        Query query = FirebaseDatabase.getInstance().getReference().child("Merchants");


        //get addresses from database and place down markers
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants");
        ValueEventListener postlistener= new ValueEventListener() {

            //add markers to list so later we can populate map with markers
            //ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get stuff fromm firebase
                Iterable<DataSnapshot> iterChild = snapshot.getChildren();
                for (DataSnapshot s : snapshot.getChildren()) {
                    //bug fixing - added when null, so check it's not null first!
                    String address =  s.child("address").getValue(String.class);

                    //if address and location aren't null, add marker to list
                    if(address != null){
                        LatLng loc = getLocationFromAddress(getApplicationContext(), s.child("address").getValue(String.class));
                        if (loc != null){
                            locationArrayList.add(loc);
                            //System.out.println("address here!! " + s.child("address").getValue(String.class));
                            nameArrayList.add(s.child("name").getValue(String.class));
                            //System.out.println("name here!! " + s.child("name").getValue(String.class));
                            //System.out.println("Prink uid" + s.getKey());
                            MerchantUidList.add(s.getKey());
                            //ystem.out.println("MerchantUIDLIST: " + s.getKey());
                        }

                    }

                }
                //called here since ondatachange is called after onmapready initially is!!!!!!!
                onMapReady(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        //});
        };
        ref.addValueEventListener(postlistener);


    }



    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    //implement onmapready interface
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        getLocationPermission();

        //add markers
        for (int i = 0; i < locationArrayList.size(); i++) {
            // below line is use to add marker to each location of our array list.
            Marker marker = this.map.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(nameArrayList.get(i)));
            //set marker tag to track click count
            Integer clickCount = 0;
            marker.setTag(clickCount);

        }
        //when we click marker
        //if click once, show biking and driving route from store to home and time needed for delivery.
        //if click twice, bring to menu view
        this.map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                for (int i = 0; i < locationArrayList.size(); i++) {
                    //if we found the marker lol
                    if(marker.getTitle().equals(nameArrayList.get(i))){
                        Integer clickCount = (Integer) marker.getTag();
                        System.out.println("clickCount: " + clickCount);
                        if (clickCount != null) {
                            //if click twice
                            if (clickCount == 1) {
                                System.out.println("marker clicked twice! clickCount: " + clickCount);
                                //reset click count
                                clickCount = 0;
                                marker.setTag(clickCount);
                                //go to store activity
                                String currentMerchantUid = MerchantUidList.get(i);
                                String merchantName = marker.getTitle();
                                User_store userStore = new User_store(s);
                                Intent intent = new Intent(mapView.this, userStore.getClass());
                                intent.putExtra("UID_STRING", currentMerchantUid);
                                intent.putExtra("STORE_NAME", merchantName);
                                System.out.println("uid_string: " + currentMerchantUid + " store_name: " + merchantName);
                                startActivity(intent);

                            }
                            //else if click once
                            else if (clickCount == 0) {
                                //increment click count
                                clickCount = clickCount + 1;
                                marker.setTag(clickCount);
                                System.out.println("marker clicked once!");
                                //turn lastKnownLocation and marker location into LatLng
                                LatLng origin = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                LatLng destination = marker.getPosition();
                                // drawRoute
                                try {
                                    drawRoute(origin, destination);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }

                }
                return false;
            }
        });



        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        //moves to current location of device on map!
        getDeviceLocation();

        //turn off location layer to remove exceeded sample count in frametime
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);

            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }


        //click listener so it goes to store activity view when we click!
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(@NonNull Marker marker) {
//                //when marker is clicked, we go to activity
//                System.out.println("clicked marker!");
//                String title = marker.getTitle();
//                System.out.println("marker title: " + title);
//                Intent intent = new Intent(getApplicationContext(), DrinklistActivity.class);
//                return false;
//            }
//        });

    }

    public Store getStore()
    {
        return s;
    }


    /**
     * Saves the state of the map when the activity is paused.
     */
    // [START maps_current_place_on_save_instance_state]
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }
    // [END maps_current_place_on_save_instance_state]

    /**
     * Sets up the options menu.
     * @param menu The options menu.
     * @return Boolean.
     */
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.activity_mapview, menu);
//        getMenuInflater().inflate(R.layout.activity_mapview, menu);
        return true;
    }



    //get current location and move camera
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
            //get current location
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){ //if not null, move camera
                            lastKnownLocation = location;
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()),
                                            DEFAULT_ZOOM));

                        }
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                        }
//                        else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            map.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            map.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    // [END maps_current_place_get_device_location]

    /**
     * Prompts the user for permission to use the device location.
     */
    // [START maps_current_place_location_permission]
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    // [END maps_current_place_location_permission]

    /**
     * Handles the result of the request for location permissions.
     */
    // [START maps_current_place_on_request_permissions_result]
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        locationPermissionGranted = false;
//        if (requestCode
//                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//        updateLocationUI();
//    }
    // [END maps_current_place_on_request_permissions_result]

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    // [START maps_current_place_update_location_ui]
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);

            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    // [END maps_current_place_update_location_ui]


    //turn string address into location
    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    // For navigation purpose
    public void UserClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout)
    {
        // open the drawer layout
        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void UserClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        // If the drawer is open, then close it
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void UserClickViewMap(View view)
    {
        recreate();
//        redirectActivity(this, mapView.class);
    }
    //    public void UserClickViewStore(View view)
//    {
//        redirectActivity(this, class);
//    }
//
    public void UserClickDeliveryProgress(View view)
    {
        redirectActivity(this, UserDeliveryProgress.class);
    }

    public void UserClickMainOrderHistory(View view)
    {
        redirectActivity(this, mainTabLayout.class);
    }

    public void UserClickAboutUs(View view)
    {
        redirectActivity(this, UserAboutUsActivity.class);
    }

    public void UserClickProfile(View view)
    {
        redirectActivity(this, UserProfileActivity.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }
    public static void logout(Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                FirebaseAuth.getInstance().signOut();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
    public static void redirectActivity(Activity activity, Class aclass)
    {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    //classes to draw line of directions using polyline
    //draw route between origin and destination
    private void drawRoute(LatLng mOrigin, LatLng mDestination) throws IOException{

        System.out.println("at drawRoute");
        //make new thread bc was getting networkonmainthreadexception
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // Getting URL to the Google Directions API
                    String myURL = getDirectionsUrl(mOrigin, mDestination);
                    System.out.println("url: " + myURL);
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(myURL)
                            .method("GET", null)
                            .build();
//          Response response = client.newCall(request).execute();
                    try (Response response = client.newCall(request).execute()) {
                        System.out.println("drawRoute response:" + response.body().string());
                        DownloadTask downloadTask = new DownloadTask();
                        // Start downloading json data from Google Directions API
                        downloadTask.execute(myURL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


//
    }

    // get url to get directions from origin to destination to look up
    private String getDirectionsUrl(LatLng origin, LatLng dest){
        System.out.println("at getDirecitonsUrl");

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + "AIzaSyCWDouECJGV1idsJfVU7lMf4Nj22_nUzIo";


        // Building the parameters to the web service, default is driving
        String parameters = str_origin + "&" + str_dest + "&" + key;
        //if not driving, mode is biking so change parameters
        if(!isDriving){
            parameters = str_origin + "&" + str_dest + "&mode=bicycling" + "&" + key;
        }

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }



    // download json data from url
    private String downloadUrl(String strUrl) throws IOException {
        System.out.println("at downloadUrl");
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            System.out.println("at downloadTask doInBackground");
            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            System.out.println("at downloadTask onPostExecute");
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            System.out.println("at parserTask doInBackground");
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;


            try{
                jObject = new JSONObject(jsonData[0]);
                System.out.println("jObject: ");
                System.out.println(jObject);
                System.out.println("at parsertask about to go to directionsjsonparser");
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parseLatLng(jObject);
                System.out.println("route data:");
                System.out.println(routes);
                String distance = parser.parseDistance(jObject);
                String time = parser.parseDuration(jObject);
                System.out.println("distance: " + distance + " time: " + time);
                //change time and distance views
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv1 = (TextView)findViewById(R.id.textTravelTime);
                        tv1.setText(time);
                        TextView tv2 = (TextView)findViewById(R.id.textDistance);
                        tv2.setText(distance + "les");
                    }
                });

            }catch(Exception e){
                //if jObject is invalid object... the data you're getting is invalid
                //if jsonData[0] is null... the data you're getting is invalid
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            System.out.println("at parserTask onPostExecute");
            System.out.println("result at onPostExecute");
            System.out.println(result);
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);


                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = map.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }




}

