package com.cmu.delos.codenamealpha.ui.provider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.model.Meal;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;


/**
 * A placeholder fragment containing a simple view.
 */
public class MealOfferCompleteFragment extends Fragment {
    private TextView dish_name;
    private TextView dish_price;
    private ImageView dish_image;
    private Meal m;

    private ArrayAdapter<String> mMealDetailListAdapter;

    public MealOfferCompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_offering_complete, container, false);
        dish_name = (TextView) rootView.findViewById(R.id.dishName);
        dish_price= (TextView) rootView.findViewById(R.id.dish_price_text);
        dish_image = (ImageView) rootView.findViewById(R.id.meal_offered_view);

        m = ((OfferMealActivity) getActivity()).getMeal();
        if(m.getDishImage()!=null) setPic();

        dish_name.setText(m.getDishName());
        dish_price.setText("$"+(int) m.getMealPrice());

        String[] data = {
                "Dish Description:\n"+m.getShortDesc(),
                "Number of Meals Available: "+ m.getMealCount(),
                "Ingredients:\n"+m.getDishIngredients()
        };

        List<String> mealDetails = new ArrayList<String>(Arrays.asList(data));
        mMealDetailListAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.meal_detail_list, // The name of the layout ID.
                        R.id.list_item_meal_details_textview, // The ID of the textview to populate.
                        mealDetails);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_meal_details);
        listView.setAdapter(mMealDetailListAdapter);

        return rootView;
    }

    private void setPic(){
        File imgFile = new File(m.getDishImage());

        // Part 1: Decode image
        Bitmap unscaledBitmap = decodeResource(imgFile, 500, 400, ScalingUtilities.ScalingLogic.FIT);

        // Part 2: Scale image
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 500,
                400, ScalingUtilities.ScalingLogic.FIT);
        unscaledBitmap.recycle();

        // Publish results
        dish_image.setImageBitmap(scaledBitmap);
    }
}
