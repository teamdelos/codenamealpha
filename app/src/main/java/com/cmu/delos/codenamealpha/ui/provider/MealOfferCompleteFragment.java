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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.model.Meal;

import java.io.File;


/**
 * A placeholder fragment containing a simple view.
 */
public class MealOfferCompleteFragment extends Fragment {
    private TextView dish_name;
    private TextView dish_ingredients;
    private TextView dish_count;
    private TextView dish_price;
    private TextView dish_desc;
    private ImageView dish_image;
    private Meal m;
    public MealOfferCompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_offering_complete, container, false);
        dish_name = (TextView) rootView.findViewById(R.id.dishName);
        dish_ingredients = (TextView) rootView.findViewById(R.id.dishIngrediants);
        dish_count = (TextView) rootView.findViewById(R.id.dishCount);
        dish_desc  = (TextView) rootView.findViewById(R.id.dishDesc);
        dish_price= (TextView) rootView.findViewById(R.id.dish_price_text);
        dish_image = (ImageView) rootView.findViewById(R.id.meal_offered_view);

        m = ((OfferMealActivity) getActivity()).getMeal();
        if(m.getDishImage()!=null) setPic();

        dish_name.setText(m.getDishName());
        dish_ingredients.setText("Ingredients:\n\t"+m.getDishIngredients());
        dish_count.setText("Number of Meals Available: "+ m.getMealCount());
        dish_price.setText("$"+(int) m.getMealPrice());
        dish_desc.setText("Dish Description:\n"+m.getShortDesc());

        return rootView;
    }

    private void setPic(){
        File imgFile = new File(m.getDishImage());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        dish_image.setImageBitmap(myBitmap);
    }
}
