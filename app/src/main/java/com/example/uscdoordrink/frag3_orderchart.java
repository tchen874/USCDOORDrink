package com.example.uscdoordrink;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag3_orderchart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag3_orderchart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PieChartView pieChartView;


    public frag3_orderchart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static frag3_orderchart newInstance() {
        frag3_orderchart fragment = new frag3_orderchart();
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
        View v = inflater.inflate(R.layout.fragment_frag3_orderchart, container, false);

        pieChartView = v.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, R.color.BLUE));
        System.out.println("This is piedata after adding blue" + pieData);
        pieData.add(new SliceValue(25, R.color.colorAccent));
        //pieData.add(new SliceValue(10, "FF6200EE"));
        pieData.add(new SliceValue(60, R.color.teal_200));

        PieChartData pieChartData = new PieChartData(pieData);

        pieChartView.setPieChartData(pieChartData);


        return v;
    }
}