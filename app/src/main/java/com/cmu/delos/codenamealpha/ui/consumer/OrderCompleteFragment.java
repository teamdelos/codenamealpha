package com.cmu.delos.codenamealpha.ui.consumer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.model.Meal;
import com.cmu.delos.codenamealpha.model.User;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;


/**
 * A placeholder fragment containing a simple view.
 */
public class OrderCompleteFragment extends Fragment {

    private TextView price_complete;
    private TextView textView4;
    private User user;
    private Meal meal;
    private ImageView imageView_complete;
    public OrderCompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_complete, container, false);
        user = ((AbstractAlphaActivity)getActivity()).getUser();
        Log.v("USER ", user.getFirstName());
        meal = ((AbstractAlphaActivity)getActivity()).getMeal();
        Log.v("MEAL ", meal.getKitchenId()+"");
        price_complete = (TextView) rootView.findViewById(R.id.price_complete);
        imageView_complete = (ImageView)rootView.findViewById(R.id.imageView_complete);
        textView4 = (TextView) rootView.findViewById(R.id.textView4);
        price_complete.setText("$" + meal.getMealPrice());
        textView4.setText(meal.getDishName());
        if(meal.getDishImage()!=null){
            File imgFile = new File(meal.getDishImage());
            // Part 1: Decode image
            Bitmap unscaledBitmap = decodeResource(imgFile, 500, 400, ScalingUtilities.ScalingLogic.FIT);
            // Part 2: Scale image
            Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 500,
                    400, ScalingUtilities.ScalingLogic.FIT);
            unscaledBitmap.recycle();
            // Publish results
            imageView_complete.setImageBitmap(scaledBitmap);
        }
        return rootView;
    }
}
