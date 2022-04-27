package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class merchantOrderHistoryActivity extends AppCompatActivity {

    private merchantOrderHistoryActivity.SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    DrawerLayout drawerLayout;
    TextView merchantName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orderchart);
        drawerLayout = findViewById(R.id.user_drawer_layout);

        sectionsPagerAdapter = new merchantOrderHistoryActivity.SectionsPagerAdapter(getSupportFragmentManager());

        //tabs.setupWithViewPager(viewPager);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);



        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        merchantName = findViewById(R.id.merchantusername);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("name")) {
                        merchantName.setText(s.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static merchantOrderHistoryActivity.PlaceholderFragment newInstance(int sectionNumber) {
            merchantOrderHistoryActivity.PlaceholderFragment fragment = new merchantOrderHistoryActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_orderchart, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            //this is where I now display different fragments depending on tab
            //frag1_overview.newInstance("Welcome user", "Please swipe right to view history in list and chart form");
            frag2_orderlist.newInstance();
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new frag1_overviewMerch();
                    break;
                case 1:
                    fragment = new frag2_orderlistMerch();
                    break;
                case 2:
                    fragment = new Frag3_orderchartMerch();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Order List";
                case 2:
                    return "Order Chart";
            }
            return null;
        }
    }

    private ArrayList<String> generateOrderList(DataSnapshot snapshot) {
        String orderString = "";
        ArrayList<String> ordersList = new ArrayList<>();

        for(DataSnapshot i : snapshot.getChildren()){
            for(DataSnapshot s : i.getChildren()){
                if(s.getKey().equals(0)) {
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
                if(s.getKey().equals("2")){
                    String userIDNumber = s.getValue().toString();
                    System.out.println("User's name from firebase: " + FirebaseDatabase.getInstance().getReference("Users").child(userIDNumber).child("name"));
                    orderString += FirebaseDatabase.getInstance().getReference("Users").child(userIDNumber).child("name");;

                } else {
                    orderString += s.getValue();
                }
                orderString += " \n";

            }
        }




        return ordersList;
    }

    public void ClickMenu(View view)
    {
        Merchant_map_view.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        Merchant_map_view.closeDrawer(drawerLayout);
    }

    public void ClickOrderHistory(View view)
    {
        recreate();
    }

    public void ClickProfile(View view)
    {
        Merchant_map_view.redirectActivity(this, ProfileActivity.class);

    }

    public void ClickEditMenu(View view)
    {
        Merchant_map_view.redirectActivity(this, MerchentEditMenu.class);
    }
    public void ClickViewMap(View view)
    {
        Merchant_map_view.redirectActivity(this, Merchant_map_view.class);

    }

    public void ClickLogout(View view)
    {
        Merchant_map_view.logout(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Merchant_map_view.closeDrawer(drawerLayout);
    }

}