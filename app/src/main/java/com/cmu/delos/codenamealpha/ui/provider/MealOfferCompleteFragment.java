package com.cmu.delos.codenamealpha.ui.provider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.model.Meal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
//        dish_ingredients = (TextView) rootView.findViewById(R.id.dishIngrediants);
//        dish_count = (TextView) rootView.findViewById(R.id.dishCount);
//        dish_desc  = (TextView) rootView.findViewById(R.id.dishDesc);
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
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        dish_image.setImageBitmap(myBitmap);
    }
}
