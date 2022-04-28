package com.example.uscdoordrink;

import static android.R.layout.simple_list_item_1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag2_orderlistMerch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag2_orderlistMerch extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag2_orderlistMerch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment frag2_orderlistMerch.
     */
    // TODO: Rename and change types and number of parameters
    public static frag2_orderlistMerch newInstance() {
        frag2_orderlistMerch fragment = new frag2_orderlistMerch();
        fragment.addGroupDataToListView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        //select time period to group items
        timePeriodSelection = (String)parent.getItemAtPosition(position);
        System.out.println("onItemSelected timeperiodSelection value: " + timePeriodSelection);
        //clear order list
        orders_list.clear();
        //then call method to seperate
        addGroupDataToListView();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void loadView() {

        DatabaseReference merchRef = FirebaseDatabase.getInstance().getReference("MerchantOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        merchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String orderlist = snapshot.

                preliminary_orders_list = generateOrderList(snapshot);
                //preliminary_orders_list.clear();

                //addGroupDataToListView();
                //sendRecommendation();
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

                    orderString += "Customer name: ";
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

    private void addGroupDataToListView(){
        //have list need to create another list
        ArrayList<Integer> groupIndicator = new ArrayList<>();

        int currentDay = 0;
        int currentMonth = 0;
        int currentYear = 0;
        int prevDay = 0;
        int prevMonth = 0;
        int prevYear = 0;
        //String prevDate = preliminary_orders_list.get(0).substring(22, 32);
        if(preliminary_orders_list.size() == 0){
            return;
            //want to be able to display a text into the list view saying please place an order!
        }
        System.out.println("before prevDate preliminary_orders_list: "+ preliminary_orders_list);
        String prevDate = getDateFromOrder(preliminary_orders_list.get(0));
        System.out.println("preliminary_orders_list: "+ preliminary_orders_list);

        String currDate;
        groupNumber = 1;

        //increment groupNumber when they're different

        groupIndicator.add(groupNumber);
        //2022/03/24
        prevYear = Integer.parseInt(prevDate.substring(0,4));
        prevMonth = Integer.parseInt(prevDate.substring(5,7));
        prevDay = Integer.parseInt(prevDate.substring(8));

        System.out.println("Year : " + prevYear);
        ArrayList<Integer> currentYearList = new ArrayList<>();
        //currentYearList.add(currentYear);
        ArrayList<Integer> currentMonthList = new ArrayList<>();
        //currentMonthList.add(currentMonth);
        ArrayList<Integer> currentDayList = new ArrayList<>();
        //currentYearList.add(currentYear);


        for (int i = 1; i < preliminary_orders_list.size(); i++) {
            currDate = getDateFromOrder(preliminary_orders_list.get(i));
            currentYear = Integer.parseInt(currDate.substring(0,4));
            currentMonth = Integer.parseInt(currDate.substring(5,7));
            currentDay = Integer.parseInt(currDate.substring(8));

            //to ensure that the headings match up with the lists that populate each area
            if(i==1){
                currentYearList.add(currentYear);
                currentMonthList.add(currentMonth);
                currentDayList.add(currentDay);
            }

            //creating arraylists to be able to properly update list headers!
            currentYearList.add(currentYear);
            currentMonthList.add(currentMonth);
            currentDayList.add(currentDay);


            System.out.println("timePeriodSelection before Switch: "+ timePeriodSelection);
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

        //remove first element in groupIndicator list so that it matches size of the current date lists
        //groupIndicator.remove(0);
        for (int i = 0; i < groupIndicator.size(); i++) {
            System.out.println("groupIndicator.size(): "+ groupIndicator.size());
            System.out.println("currentDayList.size(): "+ currentDayList.size());
            System.out.println("currentDayList in the for loop: "+ currentDayList);
            System.out.println("currentMonthList in the for loop: "+ currentMonthList);
            System.out.println("currentYearList in the for loop: "+ currentYearList);


            if (groupIndicator.get(i) != groupNum){

                //making headers
                switch (timePeriodSelection){
                    case "Day":
                        orders_list.add("Day: " + currentYearList.get(i) + "/" + currentMonthList.get(i) + "/" + currentDayList.get(i));
                        groupNum = groupIndicator.get(i);
                        break;

                    case "Month":
                        orders_list.add("Month: " + currentYearList.get(i) + "/" + currentMonthList.get(i));
                        groupNum = groupIndicator.get(i);
                        break;

                    case "Year":
                        orders_list.add("Year: " + currentYearList.get(i));
                        groupNum = groupIndicator.get(i);
                        break;

                }
            }
            //after if statement, add the elements
            orders_list.add(preliminary_orders_list.get(i));

        }
        listview_adapter.notifyDataSetChanged();
        //after list appears send notification
        sendRecommendation();

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
        Context context = getActivity();
        String text = "You sold " + recommendation + " the most so sell something else.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,text,duration );
        toast.show();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag2_orderlist_merch, container, false);
        history_view = (ListView) v.findViewById(R.id.order_chart);
        orders_list = new ArrayList<>();
        preliminary_orders_list = new ArrayList<>();

        listview_adapter = new ArrayAdapter<String>(v.getContext(),
                simple_list_item_1,
                orders_list);

        history_view.setAdapter(listview_adapter);


//        //get the spinner from the xml.
        dropdown = (Spinner) v.findViewById(R.id.spinner1);
//
//        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.date_choice_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(this);
        loadView();

        return v;
        // return inflater.inflate(R.layout.fragment_frag2_orderlist, container, false);
    }
}