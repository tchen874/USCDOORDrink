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
        Query query = FirebaseDatabase.getInstance().getReference().child("Merchants");


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
        adapter.startListening();
    }

    //stop adapter when we stop
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
