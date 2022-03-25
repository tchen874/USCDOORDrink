package com.example.uscdoordrink;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class DrinkListAdapter extends ArrayAdapter<Menu> {
    // constructor for our list view adapter.
    public DrinkListAdapter(@NonNull Context context, ArrayList<Menu> menuArrayList) {
        super(context, 0, menuArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.single_drink_view, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Menu menu = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.listViewText);
        ImageView courseIV = listitemView.findViewById(R.id.listViewPic);

        // set data to text view!
        ArrayList<Drink> list = menu.getDrinkList();
        for (Drink d : list){
            nameTV.setText(d.getDrinkName());
        }
        //nameTV.setText(menu.getName());

        // use picasso to load image to image view?
        //Picasso.get().load(menu.getImgUrl()).into(courseIV);

        // add item click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message
                ArrayList<Drink> list = menu.getDrinkList();
                Toast.makeText(getContext(), "Item clicked is : " + list.get(0), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}

}

