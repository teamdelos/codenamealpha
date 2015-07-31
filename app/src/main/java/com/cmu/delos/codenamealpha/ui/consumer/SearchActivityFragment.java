package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.AppLocationService;
//import com.cmu.delos.codenamealpha.ui.MapsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private Button btnSearch;
    private Button btnFilter;
    private Button MaptoList;
    private Button ListToMap;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    AppLocationService appLocationService;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        View view2 = inflater.inflate(R.layout.fragment_search_map, container, false);
        btnSearch = (Button)view.findViewById(R.id.btnSearch);

        

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "search Clicked");
                Intent intentToSearch = new Intent(getActivity(), MealOrderDetailActivity.class);
                startActivity(intentToSearch);
            }
        });
/**
        btnFilter = (Button)view2.findViewById(R.id.btnSearch);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "Filter Clicked");
                //Intent intentToSearch = new Intent(getActivity(), MealOrderDetailActivity.class);
                //startActivity(intentToSearch);
            }
        });
**/



        return view;
    }
}
