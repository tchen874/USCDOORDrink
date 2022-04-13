package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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




    //old stuff
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);

        //TODO here create notification channel

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

    private void createNotificationChannel() {
        // Create the NotificationChannel

    }

    String val;

    private void display_database_data() {

        //TODO change to the current user //String id = FirebaseAuth.getInstance().getCurrentUser().getUid()
//        //DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("orders");
//        String val;
//        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders");
//        //OnCompleteListener<DataSnapshot> task;
//
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("Users").   child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                               document.
//                            }
//                        } else {
//                            //not sure what to do
//                        }
//                    }
//                });

    }

    private void loadView() {

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        usersRef.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String orderString = "";
                for(DataSnapshot i : snapshot.getChildren()) {
                    for (DataSnapshot s : i.getChildren()) {

                        if (s.getKey().equals("0")) {
                            orderString += "Date order purchased: ";
                        }
                        if (s.getKey().equals("1")) {
                            orderString += "Time order placed: ";
                        }
                        if (s.getKey().equals("2")) {

                            orderString += "Name, price, and caffiene amount: ";
                        }
                        orderString += s.getValue();
                        orderString += " \n";
                    }
                    preliminary_orders_list.add(orderString);
                    orderString = "";
                }
                addGroupDataToListView();
                listview_adapter.notifyDataSetChanged();

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
        //create representation of each order in form of day, month, and year
            //then take the first item and filter into different sections based on





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

            switch (timePeriodSelection){
                case "Day":
                    if ((currentYear != prevYear) || (currentMonth != prevMonth) || (currentDay != prevDay)){
                        groupNumber++;
                    }
                    break;

                case "Month":
                    if ((currentYear != prevYear) || (currentMonth != prevMonth)){
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

        }
        System.out.println("Group indicator after for loop: " +groupIndicator);
        //have created a different arraylist which we loop through to create another list to insert headers
        //add to order list now!
        int groupNum = 0;
        for (int i = 0; i < groupIndicator.size(); i++) {
            if (groupIndicator.get(i) != groupNum){
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


    }




}