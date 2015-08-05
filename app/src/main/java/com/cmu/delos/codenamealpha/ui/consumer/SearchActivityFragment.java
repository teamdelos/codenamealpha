/**
 * Search activity Fragment which has the search bar for searching the meals
 */
package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;

//import com.cmu.delos.codenamealpha.ui.MapsActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {


    private SearchView search;
    private static final String SEARCHRESULTFRAGMENT_TAG = "SRTAG";

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        search = (SearchView) rootView.findViewById(R.id.searchView);
        search.setQueryHint("What type of food?");
        search.setSubmitButtonEnabled(true);

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                Log.v("Here query", query);
                Cursor userMealSearch = getActivity().getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithName(query), null, null, null, null);
                Log.v("Here after search", userMealSearch.getCount() + "");

                Bundle args = new Bundle();
                args.putString(SearchResultFragment.DETAIL_QUERY, query);

                SearchResultFragment fragment = (SearchResultFragment) getActivity().getFragmentManager().findFragmentByTag(SEARCHRESULTFRAGMENT_TAG);
                if ( null != fragment) {
                    fragment.onQueryChanged(query);
                }


                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.search_result_container, fragment, SEARCHRESULTFRAGMENT_TAG)
                        .commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                //	Toast.makeText(getBaseContext(), newText,
                //          Toast.LENGTH_SHORT.show();
                return false;
            }
        });

        return rootView;
    }
}
