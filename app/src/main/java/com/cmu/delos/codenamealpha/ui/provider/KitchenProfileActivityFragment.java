package com.cmu.delos.codenamealpha.ui.provider;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmu.delos.codenamealpha.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class KitchenProfileActivityFragment extends Fragment {

    public KitchenProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kitchen_profile, container, false);
    }
}