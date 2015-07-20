package com.cmu.delos.codenamealpha.ui.consumer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.delos.codenamealpha.ui.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutActivityFragment extends Fragment {

    public CheckoutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }
}
