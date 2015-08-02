package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Meal;
import com.cmu.delos.codenamealpha.model.User;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;
import java.util.Date;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;


/**
 * A placeholder fragment containing a simple view.
 */
public class OrderCompleteFragment extends Fragment {

    private TextView price_complete;
    private TextView textView4;
    private TextView textView5;
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
        textView5 = (TextView) rootView.findViewById(R.id.textView5);
        textView4 = (TextView) rootView.findViewById(R.id.textView4);
        price_complete.setText("$" + meal.getMealPrice());
        textView4.setText(meal.getDishName());
        textView5.setText("Provider: " + (((AbstractAlphaActivity) getActivity()).getProviderDetails().getFirstName() +" "
                + ((AbstractAlphaActivity) getActivity()).getProviderDetails().getLastName()));
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
        ContentValues transactionValues = new ContentValues();
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_KITCHEN_ID, meal.getKitchenId());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_MEAL_ID, meal.getMealId());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_MEAL_NAME, meal.getDishName());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_MEAL_PRICE, meal.getMealPrice());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_TRAN_TIME, new Date().getTime());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_USER_ID_C, user.getUserId());
        transactionValues.put(AlphaContract.TransactionEntry.COLUMN_USER_ID_P, ((AbstractAlphaActivity) getActivity()).
                getProviderDetails().getUserId());
        Uri insertedKitchenUri = getActivity().getContentResolver().insert(AlphaContract.TransactionEntry.CONTENT_URI, transactionValues);
        Log.v("Transact ID", ContentUris.parseId(insertedKitchenUri) + "");
        return rootView;
    }
}
