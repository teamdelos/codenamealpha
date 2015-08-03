package com.cmu.delos.codenamealpha.ui.consumer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.AppLocationService;

//import com.cmu.delos.codenamealpha.ui.MapsActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {


    AppLocationService appLocationService;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        View view2 = inflater.inflate(R.layout.fragment_search_map, container, false);

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
