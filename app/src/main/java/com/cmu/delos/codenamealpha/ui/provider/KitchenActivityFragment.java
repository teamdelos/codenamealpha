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
public class KitchenActivityFragment extends Fragment {
    private Button addMealBtn;
    public KitchenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);

        addMealBtn = (Button)rootView.findViewById(R.id.add_meal_button);
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "got to add meal Clicked");
                Intent intentToOfferMeal = new Intent(getActivity(), OfferMealActivity.class);
                startActivity(intentToOfferMeal);
            }
        });

        return rootView;
    }
}
