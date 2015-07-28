package com.cmu.delos.codenamealpha.ui.provider;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Kitchen;


/**
 * A placeholder fragment containing a simple view.
 */
public class KitchenActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int DISHES_LOADER = 0;
    private Button addMealBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private KitchenDishesAdapter mAdapter;

    private static final String[] MEAL_COLUMNS = {
            AlphaContract.MealEntry.TABLE_NAME+"."+ AlphaContract.MealEntry._ID,
            AlphaContract.MealEntry.COLUMN_DISH_NAME,
            AlphaContract.MealEntry.COLUMN_DISH_IMAGE,
            AlphaContract.MealEntry.COLUMN_MEAL_PRICE,
            AlphaContract.MealEntry.COLUMN_MEAL_COUNT
    };

    public KitchenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);
        mAdapter = new KitchenDishesAdapter(getActivity(), null);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.dishes_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        addMealBtn = (Button) rootView.findViewById(R.id.add_meal_button);
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click", "got to add meal Clicked");
                Intent intentToOfferMeal = new Intent(getActivity(), OfferMealActivity.class);
                startActivity(intentToOfferMeal);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        getLoaderManager().initLoader(DISHES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Kitchen k = ((KitchenActivity) getActivity()).getKitchen();

        Uri dishesUri = AlphaContract.MealEntry.buildMealUriWithKid(k.getKitchenId());
        return new CursorLoader(
                getActivity(),
                dishesUri,
                MEAL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader){
        mAdapter.swapCursor(null);
    }
}
