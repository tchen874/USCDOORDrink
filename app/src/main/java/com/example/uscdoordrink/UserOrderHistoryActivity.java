package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class UserOrderHistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView history_view;
    Spinner dropdown;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> orders_list;
    //list that comes before we send to list view
    ArrayList<String> preliminary_orders_list;

    String timePeriodSelection = "Day";
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> listview_adapter;
    int groupNumber = 1;
    //String notificationRecommendationTitle;

    public static final String CHANNEL_NAME = "notification_channel";
    public static final String CHANNEL_DESCRIPTION = "A channel for notifications.";
    public static final String CHANNEL_ID = "0";





    //old stuff
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);
        makeNotificationMechanism();
        //notificationRecommendationTitle = "";

        drawerLayout = findViewById(R.id.user_drawer_layout);
        //System.out.println("store if=");

        history_view = (ListView) findViewById(R.id.order_chart);
        orders_list = new ArrayList<>();
        preliminary_orders_list = new ArrayList<>();

        listview_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                orders_list);

        history_view.setAdapter(listview_adapter);


        //get the spinner from the xml.
        dropdown = (Spinner) findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        //String[] items = new String[]{"day", "month", "year"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_choice_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(this);


        //testing
        //System.out.println("TESTING!!!!! LOOK AT ME");
        //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //System.out.println("Usersref : "  + usersRef.getKey());
        //Log.d("Usersref : ", usersRef.getKey());
        //Log.d("orders : ", usersRef.child("orders").child("2").get);
        //Log.d("orders : ", usersRef.child("orders").child("2").get().toString());
        //Log.d("orders: ", usersRef.toString());

        //testing@gmail.com
        //testing123
        loadView();

    }


    // For navigation purpose
    public void UserClickMenu(View view)
    {
        System.out.println("Why this is mot");
        UserNavigationActivity.openDrawer(drawerLayout);
    }
    public void UserClickLogo(View view){
        UserNavigationActivity.closeDrawer(drawerLayout);
    }
    public void UserClickProfile(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserProfileActivity.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }
    public void UserClickOrderHistory(View view)
    {
        recreate();
    }
    public void UserClickAboutUs(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickViewMap(View view)
    {
        UserNavigationActivity.redirectActivity(this, mapView.class);
    }
    public void UserClickViewStore(View view)
    {
        UserNavigationActivity.redirectActivity(this, User_store.class);
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
    public void UserClickDeliveryProgress(View view)
    {
        recreate();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        UserNavigationActivity.closeDrawer(drawerLayout);
    }

    private void makeNotificationMechanism() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    //Can I test this?
    private boolean checkVersionSDK() {
        boolean b = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
        return b;
    }

    private String getRecommendation(){
        //go through list, get rec, put into String
        int max = Integer.MIN_VALUE;
        String recommendation = "";
        HashMap<String, Integer> map = new HashMap<>();
        for (String order: preliminary_orders_list) {
            int indexOfDrinkName = order.indexOf("drinkName='");
            int startIndex = indexOfDrinkName + 10;
            int endIndex = order.indexOf(", price=");
            String recDrinkName = order.substring(startIndex, endIndex);
            if(!map.containsKey(recDrinkName)){
                map.put(recDrinkName, 1);
            } else {
                int numOccurences = map.get(recDrinkName);
                map.put(recDrinkName, ++numOccurences);
            }
        }
        //find the max occurences in has map
        for (Map.Entry<String, Integer> set : map.entrySet()) {
            if (set.getValue() > max){
                max = set.getValue();
                recommendation = set.getKey();
            }
        }
        return recommendation;

    }

    public void sendRecommendation() {
        NotificationCompat.Builder builder;
        String recommendation = getRecommendation();
        if (recommendation.equals("")){
            return;
        }
        //String strtitle = getString(R.string.notificationtitle);
        //String strtext = getString(R.string.notificationtext);
        Context context = getApplicationContext();
        String text = "You bought the drink " + recommendation + " the most so you should purchase this drink again.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,text,duration );
        toast.show();

        //TextView textView = (TextView) findViewById(R.id.android_id);
        //textView.setText("You bought the drink " + recommendation + " the most so you should purchase this drink again.");

        //Intent intent = new Intent(this, notification.class);
        // intent.putExtra("title", "Recommendation");
        //intent.putExtra("text", "You bought the drink " + recommendation + " the most so you should purchase this drink again.");

       // builder = new NotificationCompat.Builder(this, CHANNEL_ID)
       //         .setContentTitle("Recommendation")
       //         .setContentText("You bought the drink " + recommendation + " the most so you should purchase this drink again.")
       //         .setStyle(new NotificationCompat.BigTextStyle()
       //                 .bigText("You bought the drink " + recommendation + " the most so you should purchase this drink again."))
       //         .setPriority(NotificationCompat.PRIORITY_DEFAULT);

       // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
       // notificationManager.notify(0, builder.build());
    }


    private ArrayList<String> generateOrderList(DataSnapshot snapshot){
        String orderString = "";
        ArrayList<String> ordersList = new ArrayList<>();

        for(DataSnapshot i : snapshot.getChildren()) {
            for (DataSnapshot s : i.getChildren()) {

                if (s.getKey().equals("0")) {
                    orderString += "Date order purchased: ";
                }
                if (s.getKey().equals("1")) {
                    orderString += "Time order placed: ";
                }
                if (s.getKey().equals("2")) {

                    orderString += "Name, price, and caffeine amount: ";
                }
                orderString += s.getValue();
                orderString += " \n";
            }
            ordersList.add(orderString);
            orderString = "";
        }
        return ordersList;
    }

    private void loadView() {

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        usersRef.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                preliminary_orders_list = generateOrderList(snapshot);
                //addGroupDataToListView();
                //sendRecommendation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        //filters by data of user input

        //select time period to group items
        timePeriodSelection = (String)parent.getItemAtPosition(position);
        System.out.println("onItemSelected timeperiodSelection value: " + timePeriodSelection);
        //clear order list
        orders_list.clear();
        //then call method to seperate
        addGroupDataToListView();
        // dropdown.setSelection(position);
        //notification


        //Toast.makeText(MainActivity.this, "\n Class: \t " + selectedClass +
        //       "\n Div: \t" + selectedDiv, Toast.LENGTH_LONG).show();



    //testing
        //System.out.println("TESTING!!!!! LOOK AT ME");
        //System.out.println("Usersref : "  + usersRef.getKey());
        //Log.d("Usersref : ", usersRef.getKey());
        //Log.d("orders : ", usersRef.child("orders").child("2").get().toString());
        //testing@gmail.com
        //testing123
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addGroupDataToListView(){
        //have list need to create another list
        ArrayList<Integer> groupIndicator = new ArrayList<>();


        int currentDay = 0;
        int currentMonth = 0;
        int currentYear = 0;
        int prevDay = 0;
        int prevMonth = 0;
        int prevYear = 0;
        String prevDate = preliminary_orders_list.get(0).substring(22, 32);
        String currDate;
        groupNumber = 1;


        //increment groupNumber when they're different

        groupIndicator.add(groupNumber);
        //2022/03/24
        prevYear = Integer.parseInt(prevDate.substring(0,4));
        prevMonth = Integer.parseInt(prevDate.substring(5,7));
        prevDay = Integer.parseInt(prevDate.substring(8));

        System.out.println("Year : " + prevYear);
        for (int i = 1; i < preliminary_orders_list.size(); i++) {
            currDate = preliminary_orders_list.get(i).substring(22, 32);
            currentYear = Integer.parseInt(currDate.substring(0,4));
            currentMonth = Integer.parseInt(currDate.substring(5,7));
            currentDay = Integer.parseInt(currDate.substring(8));

            System.out.println("timePeriodSelection before Switch: "+ timePeriodSelection);
            switch (timePeriodSelection){
                case "Day":
                    if ((currentYear != prevYear) || (currentMonth != prevMonth) || (currentDay != prevDay)){
                        groupNumber++;
                    }
                    break;

                case "Month":
                    if ((currentYear != prevYear) || (currentMonth != prevMonth)){
                        System.out.println("currentYear in Switch: "+ currentYear);
                        System.out.println("prevYear in Switch: "+ prevYear);
                        System.out.println("currMonth in Switch: "+ currentMonth);
                        System.out.println("prevMonth in Switch: "+ prevMonth);
                        System.out.println("groupNum in Switch before incrementation: "+ groupNumber);
                        groupNumber++;
                    }
                    break;

                case "Year":
                    if (currentYear != prevYear){
                        groupNumber++;
                    }
                    break;

            }
            groupIndicator.add(groupNumber);
            prevYear = currentYear;
            prevDay = currentDay;
            prevMonth = currentMonth;
            System.out.println("groupIndicator after Switch: "+ groupIndicator);
            System.out.println("prevYear after Switch: "+ prevYear);
            System.out.println("prevDay after Switch: "+ prevDay);
            System.out.println("prevMonth after Switch: "+ prevMonth);
            System.out.println("groupNum after Switch before incrementation: "+ groupNumber);

        }
        System.out.println("Group indicator after for loop: " +groupIndicator);
        //have created a different arraylist which we loop through to create another list to insert headers
        //add to order list now!
        int groupNum = 0;
        for (int i = 0; i < groupIndicator.size(); i++) {
            System.out.println("BEFORE IF groupIndicator.get(i): "+ groupIndicator.get(i));
            System.out.println("BEFORE IF groupNum: "+ groupNum);
            if (groupIndicator.get(i) != groupNum){
                System.out.println("AFTER IF groupIndicator.get(i): "+ groupIndicator.get(i));
                System.out.println("AFTER IF groupNum: "+ groupNum);

                //making headers
                switch (timePeriodSelection){
                    case "Day":
                        orders_list.add("Day: " + currentYear + "/" + currentMonth + "/" + currentDay);
                        groupNum = groupIndicator.get(i);
                        break;

                    case "Month":
                        orders_list.add("Month: " + currentYear + "/" + currentMonth);
                        groupNum = groupIndicator.get(i);
                        break;

                    case "Year":
                        orders_list.add("Year: " + currentYear);
                        groupNum = groupIndicator.get(i);
                        break;

                }
            }
            //after if statement, add the elements
            orders_list.add(preliminary_orders_list.get(i));

        }
        System.out.println("timePeriodSelection after second for loop: " + timePeriodSelection);
        listview_adapter.notifyDataSetChanged();
        //after list appears send notification
        sendRecommendation();

    }
}