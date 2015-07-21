package com.cmu.delos.codenamealpha.ui.consumer;

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
public class CheckoutActivityFragment extends Fragment {
    private Button placeOrderBtn;
    public CheckoutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_check_out_meal, container, false);
        placeOrderBtn = (Button)rootView.findViewById(R.id.btnPlaceOrder);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "meal Clicked");
                Intent intentToCompleteOrder = new Intent(getActivity(), OrderCompleteActivity.class);
                startActivity(intentToCompleteOrder);
            }
        });
        return rootView;
    }
}
