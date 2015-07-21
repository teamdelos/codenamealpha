package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cmu.delos.codenamealpha.ui.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MealOrderDetailActivityFragment extends Fragment {

    private ImageButton imageBtn1;

    public MealOrderDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_order_detail, container, false);
        imageBtn1 = (ImageButton)view.findViewById(R.id.imageButton);
        imageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "meal Clicked");
                Intent intentToViewMeal = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intentToViewMeal);
            }
        });
        return view;
    }
}
