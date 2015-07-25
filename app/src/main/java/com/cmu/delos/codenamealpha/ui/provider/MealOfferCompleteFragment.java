package com.cmu.delos.codenamealpha.ui.provider;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmu.delos.codenamealpha.ui.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MealOfferCompleteFragment extends Fragment {
//    private Button toKitchenButton;
    public MealOfferCompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_offering_complete, container, false);
//        toKitchenButton = (Button) rootView.findViewById(R.id.toKitchenButton);
//        toKitchenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentToGoKitchen = new Intent(getActivity(), KitchenActivity.class);
//                startActivity(intentToGoKitchen);
//            }
//        });
        return rootView;
    }
}
