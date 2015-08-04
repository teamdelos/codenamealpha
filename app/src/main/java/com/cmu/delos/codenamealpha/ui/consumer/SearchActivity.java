package com.cmu.delos.codenamealpha.ui.consumer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.ui.AppLocationService;
import com.cmu.delos.codenamealpha.ui.ProfileActivity;
import com.google.android.gms.maps.GoogleMap;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.cmu.delos.codenamealpha.ui.MapsActivity;

public class SearchActivity extends AbstractAlphaActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
//    private SearchView search;
    private static final String SEARCHRESULTFRAGMENT_TAG = "SRTAG";

    boolean mShowMap;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private Location mylocation = mMap.getMyLocation();
    //private LatLng sydney = new LatLng(mylocation.getLatitude(),mylocation.getLongitude());

    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupToolbar();
        setupNavigationView();
        if(savedInstanceState==null){
            handleFragment();
        }

        //new MapsActivity();



//        search = (SearchView) findViewById(R.id.searchView);
//        search.setQueryHint("What type of food?");
//
//        //*** setOnQueryTextFocusChangeListener ***
//        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//
//
//            }
//        });
//
//        //*** setOnQueryTextListener ***
//        search.setOnQueryTextListener(new OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // TODO Auto-generated method stub
//                Log.v("Here query", query);
//                Cursor userMealSearch = getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithName(query), null, null, null, null);
//                Log.v("Here after search", userMealSearch.getCount() + "");
//
//
//                Intent intentToSignUp = new Intent(getApplicationContext(), SearchResults.class);
//                intentToSignUp.putExtra("query", query);
//                startActivity(intentToSignUp);
//                return false;
//            }
//
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // TODO Auto-generated method stub
//
//                //	Toast.makeText(getBaseContext(), newText,
//                //          Toast.LENGTH_SHORT.show();
//
//                return false;
//            }
//        });

    }

    private void handleFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment searchFragment = new SearchActivityFragment();
        fragmentTransaction.replace(R.id.search_container, searchFragment);
        Fragment searchResultFragment = new SearchResultFragment();

//        Bundle args = new Bundle();
//        args.putString(SearchResultFragment.DETAIL_QUERY, "");

//        searchResultFragment.setArguments(args);
        fragmentTransaction.replace(R.id.search_result_container, searchResultFragment,SEARCHRESULTFRAGMENT_TAG);

        fragmentTransaction.commit();
    }


    private void setupNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        TextView navHeaderTitle = (TextView)drawerLayout.findViewById(R.id.nav_header_title);
        TextView navHeaderEmail = (TextView)drawerLayout.findViewById(R.id.nav_header_email);
        CircleImageView navHeaderImage = (CircleImageView)drawerLayout.findViewById(R.id.profile_image);
        if (super.getUser() != null ) {
            navHeaderTitle.setText(super.getUser().getFirstName() + " " + super.getUser().getLastName());
            navHeaderEmail.setText(super.getUser().getEmail());
        }
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.navigation_item_1:
                        Intent goToProfile = new Intent(SearchActivity.this, ProfileActivity.class);
                        startActivity(goToProfile);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.navigation_item_3:
                        Intent goToHistory = new Intent(SearchActivity.this, TransactionHistoryActivity.class);
                        startActivity(goToHistory);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.bland_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.diabetic_meal:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.glutenfree_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.low_calorie:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.low_cholesterol_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.low_sodium:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.nonlactose_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.vegan_meal:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.vegeterian_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.nutsorpeanuts:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.kosher_meal:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.child_meal:
                //startActivity(new Intent(this, Help.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
//        System.exit(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }


}
