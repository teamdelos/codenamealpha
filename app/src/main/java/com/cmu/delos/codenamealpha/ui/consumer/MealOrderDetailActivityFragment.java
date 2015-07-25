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

/**
 * A placeholder fragment containing a simple view.
 */
public class MealOrderDetailActivityFragment extends Fragment {

    private Button buyBtn;

    public MealOrderDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_order_detail, container, false);
        buyBtn = (Button)view.findViewById(R.id.buy_meal_button);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "meal Clicked");
                Intent intentToCheckOut = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intentToCheckOut);
            }
        });
        return view;
    }
}
