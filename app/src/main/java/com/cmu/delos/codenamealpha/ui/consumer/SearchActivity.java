package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.ui.ProfileActivity;
import com.cmu.delos.codenamealpha.ui.SettingsActivity;

public class SearchActivity extends AbstractAlphaActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupToolbar();
        setupNavigationView();

        search=(SearchView) findViewById(R.id.searchView);
        search.setQueryHint("What type of food?");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                Log.v("Here query",query);
                Cursor userMealSearch = getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithName(query),null,null,null,null);
                Log.v("Here after search",userMealSearch.getCount()+"");
                Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();

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

    }

    private void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
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
                    case R.id.navigation_item_2:
                        Intent goToSettings = new Intent(SearchActivity.this, SettingsActivity.class);
                        startActivity(goToSettings);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }

    private void setupToolbar(){
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_drawer_items, menu);
//        return true;
//    }
}
