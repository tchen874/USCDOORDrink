package com.example.uscdoordrink;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import com.firebase.ui.database.FirebaseListAdapter;

public class DrinklistActivity extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinklist);

        //find list view
        lv = (ListView) findViewById(R.id.listViewDrinks);
        // FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current id
        //query reference to values in database
        Query query = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseListOptions<Drink> options = new FirebaseListOptions.Builder<Drink>()
                .setLayout(R.layout.single_drink_view)
                .setQuery(query, Drink.class).build();

        adapter = new FirebaseListAdapter(options){
            @Override
            //populate the view with values
            protected void populateView(View v, Object model, int position){
                //get textview and values we want
                TextView name = v.findViewById(R.id.name);
                TextView price = v.findViewById(R.id.price);
                TextView caffeine = v.findViewById(R.id.caffeine);

                //cast model and create instance of drink class
                Drink drink = (Drink) model;
                name.setText("Drink name: " + drink.getName().toString());
                price.setText("Price: " + Double.toString(drink.getPrice()));
                caffeine.setText("Caffeine: " + Double.toString(drink.getCaffeine()));

            }

        };
        //set adapter
        lv.setAdapter(adapter);
    }


    // start adapter when we start
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("here");
        adapter.startListening();
    }

    //stop adapter when we stop
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
//
//    private ListView mListView;
//    private FirebaseListAdapter<Menu> adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drinklist);
//
//        Query query = FirebaseDatabase.getInstance().getReference().child("Menu");
//        // find list view
//        mListView = (ListView) findViewById(R.id.listViewDrinks);
//
//        DatabaseReference menuReference = FirebaseDatabase.getInstance().getReference().child("Menu");
//
//        FirebaseListOptions<Menu> menuOptions = new FirebaseListOptions.Builder<Menu>()
//                .setLayout(R.layout.single_drink_view)
//                .setQuery(menuReference, Menu.class)
//                .build();
//
//        adapter = new FirebaseListAdapter<Menu>(menuOptions) {
//
//            //populate the view
//            @Override
//            protected void populateView(View v, Menu model, int position) {
//                System.out.println("here!");
//                Menu menu = (Menu) model;
//
//                TextView name = v.findViewById(R.id.singledrinkview);
//                name.setText("here");
//
////                TextView name = v.findViewById(R.id.name);
////                TextView description = v.findViewById(R.id.description);
////
////                name.setText(trick.getName().toString());
////                description.setText(trick.getDescription().toString());
//
//
//            }
//        };
//        mListView.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop(){
//        super.onStop();
//        adapter.stopListening();
//    }
//}
//
//
////
////        //database reference
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("menu");
////
////        FirebaseListAdapter mAdapter = new FirebaseListAdapter<Menu>(this, Menu.class, R.layout.single_drink_view, ref){
////            @Override
////            protected void populateView(View view, Menu menu, int position){
////                //populate view
////                ArrayList<Drink> drinklist = menu.getDrinkList();
////
////            }
////        };
////        // Now set the adapter with a given layout
////        mListView.setAdapter(new FirebaseListAdapter<Menu>(this, Menu.class,
////                android.R.layout.one_line_list_item, reference) {
////
////            // Populate view as needed
////            @Override
////            protected void populateView(View view, Menu menu, int position) {
////                ArrayList<Drink> drinkList = menu.getDrinkList();
////                for (Drink d : drinkList) {
////                    ((TextView) view.findViewById(android.R.id.text1)).setText(d.getDrinkName());
////                }
////
////
////            }
////        });
////        mListView.setAdapter(mAdapter);
//
//
//
////
////    protected void onCreate(Bundle savedInstanceState) {
////
//
////
////
////    }
//
////    // creating a variable for our list view,
////    // arraylist and firebase Firestore.
////    ListView menuLV;
////    ArrayList<Menu> menuArrayList;
////    FirebaseFirestore db;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_drinklist);
////
////        // below line is use to initialize our variables
////        menuLV = findViewById(R.id.listViewDrinks);
////        menuArrayList = new ArrayList<Menu>();
////
////        // initializing our variable for firebase firestore and getting its instance.
////        db = FirebaseFirestore.getInstance();
////
////        // here we are calling a method
////        // to load data in our list view.
////        loadDatainListview();
////    }
////
////    private void loadDatainListview() {
////        // get data from Firebase firestore using collection
////        db.collection("Merchants").get()
////                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////                    @Override
////                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                        // after getting the data, check if the received query snapshot is empty or not.
////                        if (!queryDocumentSnapshots.isEmpty()) {
////                            // if the snapshot is not empty we are hiding
////                            // our progress bar and adding our data in a list.
////                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
////                            for (DocumentSnapshot d : list) {
////                                // after getting this list we are passing
////                                // that list to our object class.
////                                Menu menu = d.toObject(Menu.class);
////
////                                // after getting data from Firebase we are
////                                // storing that data in our array list
////                                menuArrayList.add(menu);
////                            }
////                            // after that we are passing our array list to our adapter class.
////                            DrinkListAdapter adapter = new DrinkListAdapter(DrinklistActivity.this, menuArrayList);
////
////                            // after passing this array list to our adapter
////                            // class we are setting our adapter to our list view.
////                            menuLV.setAdapter(adapter);
////                        } else {
////                            // if the snapshot is empty we are displaying a toast message.
////                            Toast.makeText(DrinklistActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                // we are displaying a toast message
////                // when we get any error from Firebase.
////                Toast.makeText(DrinklistActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
