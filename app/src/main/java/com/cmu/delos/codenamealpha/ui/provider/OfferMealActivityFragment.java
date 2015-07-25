package com.cmu.delos.codenamealpha.ui.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmu.delos.codenamealpha.R;



/**
 * A placeholder fragment containing a simple view.
 */
public class OfferMealActivityFragment extends Fragment {
    Button offerMealCompleteButton;

    public OfferMealActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_meal, container, false);
        offerMealCompleteButton = (Button) rootView.findViewById(R.id.list_meal_button);
        offerMealCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("on click", "list Meal Button Clicked");
                Intent intentToCompleteOfferMeal = new Intent(getActivity(), MealOfferingCompleteActivity.class);
                startActivity(intentToCompleteOfferMeal);
            }
        });
        return rootView;
    }
}
