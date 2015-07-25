package com.cmu.delos.codenamealpha.ui.provider;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmu.delos.codenamealpha.ui.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MealOfferingCompleteActivityFragment extends Fragment {
    private Button toKitchenButton;
    public MealOfferingCompleteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_offering_complete, container, false);
        toKitchenButton = (Button) rootView.findViewById(R.id.toKitchenButton);
        toKitchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "toKitchen Clicked");
//                Intent intentToGoKitchen = new Intent(getActivity(), KitchenActivity.class);
//                startActivity(intentToGoKitchen);
                Fragment OfferMealFragment = new OfferMealActivityFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, OfferMealFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return rootView;
    }
}
