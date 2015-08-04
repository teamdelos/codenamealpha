package com.cmu.delos.codenamealpha.ui.consumer;

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
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;


public class SearchResultFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String[] MEAL_COLUMNS = {
            AlphaContract.MealEntry.TABLE_NAME+"."+ AlphaContract.MealEntry._ID,
            AlphaContract.MealEntry.COLUMN_DISH_NAME,
            AlphaContract.MealEntry.COLUMN_DISH_IMAGE,
            AlphaContract.MealEntry.COLUMN_MEAL_PRICE,
            AlphaContract.MealEntry.COLUMN_MEAL_COUNT,
            AlphaContract.MealEntry.COLUMN_IS_LISTED,
            AlphaContract.MealEntry.COLUMN_KITCHEN_ID
    };


    private static final int DISHES_LOADER = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MealAdapter mAdapter;
    Cursor userMealSearch;
    static final String DETAIL_QUERY = "QUERY";
    private String mQuery;



    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }

    //Here you Save your data
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mQuery", mQuery);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        if(mQuery!=null){
            getLoaderManager().initLoader(DISHES_LOADER, null, this);
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Cursor userMealSearch = getActivity().getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithName(mQuery), null, null, null, null);
        if (userMealSearch.getCount() > 0) {
            Uri dishesUri = AlphaContract.MealEntry.buildMealUriWithName(mQuery);
            return new CursorLoader(
                    getActivity(),
                    dishesUri,
                    MEAL_COLUMNS,
                    null,
                    null,
                    null
            );
        } else {
            Toast.makeText(getActivity(), "Meal " + mQuery + " not found!",
                    Toast.LENGTH_SHORT).show();
            Intent intentToSignUp = new Intent(getActivity(), SearchActivity.class);
            startActivity(intentToSignUp);
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader){
        mAdapter.swapCursor(null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mQuery = arguments.getString(SearchResultFragment.DETAIL_QUERY);
        }

        if ((savedInstanceState != null) && (savedInstanceState.getString("mQuery") != null)) {
            mQuery = savedInstanceState
                        .getString("mQuery");
            }

            mAdapter = new MealAdapter(getActivity(), null);
            mRecyclerView=(RecyclerView)view.findViewById(R.id.search_dishes_recycler_view);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void onQueryChanged(String query){
        mQuery = query;
        getLoaderManager().restartLoader(DISHES_LOADER, null, this);
    }

}
