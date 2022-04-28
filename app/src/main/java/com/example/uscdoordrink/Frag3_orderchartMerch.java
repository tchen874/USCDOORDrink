package com.example.uscdoordrink;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3_orderchartMerch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3_orderchartMerch extends Fragment {

    TextView firstTV, firstTV1, secondTV, secondTV1, thirdTV, thirdTV1, fourthTV, fourthTV1;
    TextView first_label, second_label, third_label, fourth_label;
    PieChart pieChart;


    PieChartView pieChartView;
    private boolean hasLabels = true;
    ArrayList<String> preliminary_orders_list;


    public Frag3_orderchartMerch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag3_orderchartMerch.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag3_orderchartMerch newInstance(String param1, String param2) {
        Frag3_orderchartMerch fragment = new Frag3_orderchartMerch();
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
        View v = inflater.inflate(R.layout.fragment_frag3_orderchart_merch, container, false);
        firstTV = v.findViewById(R.id.firstTV);
        firstTV1 = v.findViewById(R.id.firstTV1);
        secondTV = v.findViewById(R.id.secondTV);
        secondTV1 = v.findViewById(R.id.secondTV1);

        thirdTV = v.findViewById(R.id.thirdTV);
        thirdTV1 = v.findViewById(R.id.thirdTV1);
        fourthTV = v.findViewById(R.id.fourthTV);
        fourthTV1 = v.findViewById(R.id.fourthTV1);

        first_label = v.findViewById(R.id.first_label);
        second_label = v.findViewById(R.id.second_label);
        third_label = v.findViewById(R.id.third_label);
        fourth_label = v.findViewById(R.id.fourth_label);

        pieChart = v.findViewById(R.id.piechart);

        //setData();
        loadView();
        return v;
    }


    private void loadView() {

        DatabaseReference merchRef = FirebaseDatabase.getInstance().getReference("MerchantOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        System.out.println("merchRef: " + FirebaseDatabase.getInstance().getReference("MerchantOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        merchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String orderlist = snapshot.


                preliminary_orders_list = generateOrderList(snapshot);
                //preliminary_orders_list.clear();
                //addGroupDataToListView();
                String recommendation = getRecommendation();
                //TODO where I split up top four popular drinks and put into four var
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getDateFromOrder(String order){
        return order.substring(22, 32);
    }

    public ArrayList<String> generateOrderList(DataSnapshot snapshot){
        String orderString = "";
        ArrayList<String> ordersList = new ArrayList<>();

        System.out.println("snapshot in genOrderList: " + snapshot);
        for(DataSnapshot i : snapshot.getChildren()) {

            System.out.println("i: "+ snapshot.getChildren());
            for (DataSnapshot s : i.getChildren()) {
                //how to convert between snapshot and orderlist??
                //array of string value pairs?!
                if (s.getKey().equals("0")) {
                    orderString += "Date order purchased: ";
                }
                if (s.getKey().equals("1")) {
                    orderString += "Time order placed: ";
                }
                if (s.getKey().equals("2")) {

                    orderString += "Cust name: ";
                }
                if (s.getKey().equals("3")) {

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

    private String getRecommendation(){
        //go through list, get rec, put into String
        int max = Integer.MIN_VALUE;
        String recommendation = "";
        //use split!!
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
        System.out.println("Map at line 208: "+ map);
        System.out.println("map.entrySet(): "+ map.entrySet());

        //create list from elements in HashMap
        List<Map.Entry<String, Integer> > list = new ArrayList<Map.Entry<String, Integer> > (map.entrySet());
        //sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >(){
            //("boba", 3), ("milk", 2), ("coffee", 1)
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()) .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        System.out.println("Sorted list" + list);
//        List<TextView> textViews = new ArrayList<TextView>();
//        textViews.add(firstTV);
//        textViews.add(secondTV);
//        textViews.add(thirdTV);
//        textViews.add(fourthTV);

        //i < 4 because I want to show the four most ordered drinks
        List<String> stringsforTV = new ArrayList<String>();
        List<Integer> occurencesforTV = new ArrayList<Integer>();

        int count = 4;
        //set all views to non visible

        if(map.size() < 4) {
            count = map.size();
        }
        for (int i = 0; i < count; i++){
            System.out.println("map size: " + map.size());
            System.out.println("int i: " + i);
            System.out.println("strings for TV: " + stringsforTV);

            stringsforTV.add(list.get(i).getKey().replaceAll("'", ""));
            occurencesforTV.add(list.get(i).getValue());
            System.out.println(list.get(i).getKey().replaceAll("'", ""));
            System.out.println(list.get(i).getValue());

        }
        System.out.println("stringsforTV "+ stringsforTV);


        //add to textview
        int count1 = 4;
        if(map.size() < 4) {
            count1 = map.size();
        }
        for (int i = 0; i < count1; i++) {
            System.out.println("stringsforTV.get(i) "+ stringsforTV.get(i));

            if(i==0){
                firstTV.setText(Integer.toString(occurencesforTV.get(i)));
                first_label.setText(stringsforTV.get(i));
                firstTV1.setText(stringsforTV.get(i));
                pieChart.addPieSlice(
                        new PieModel(
                                stringsforTV.get(i),
                                Integer.parseInt(firstTV.getText().toString()),
                                Color.parseColor("#111111")));
            }
            if(i==1){
                secondTV.setText(Integer.toString(occurencesforTV.get(i)));
                second_label.setText(stringsforTV.get(i));
                secondTV1.setText(stringsforTV.get(i));
                pieChart.addPieSlice(
                        new PieModel(
                                stringsforTV.get(i),
                                Integer.parseInt(secondTV.getText().toString()),
                                Color.parseColor("#66BB6A")));
            }
            if(i==2){
                thirdTV.setText(Integer.toString(occurencesforTV.get(i)));
                third_label.setText(stringsforTV.get(i));
                thirdTV1.setText(stringsforTV.get(i));
                pieChart.addPieSlice(
                        new PieModel(
                                stringsforTV.get(i),
                                Integer.parseInt(thirdTV.getText().toString()),
                                Color.parseColor("#EF5350")));
            }
            if(i==3){
                fourthTV.setText(Integer.toString(occurencesforTV.get(i)));
                fourth_label.setText(stringsforTV.get(i).toString());
                fourthTV1.setText(stringsforTV.get(i));
                pieChart.addPieSlice(
                        new PieModel(
                                stringsforTV.get(i),
                                Integer.parseInt(fourthTV.getText().toString()),
                                Color.parseColor("#29B6F6")));
            }

            pieChart.startAnimation();

        }

        //find the top 4 max occurences in hash map
        for (Map.Entry<String, Integer> set : map.entrySet()) {
            if (set.getValue() > max) {
                max = set.getValue();
                recommendation = set.getKey();
            }
        }
        return recommendation;

    }
}