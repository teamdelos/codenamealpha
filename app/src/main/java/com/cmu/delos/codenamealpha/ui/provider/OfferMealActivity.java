package com.cmu.delos.codenamealpha.ui.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.ui.ProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferMealActivity extends AbstractAlphaActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_meal);
        setupToolbar();
        setupNavigationView();
        if(savedInstanceState==null){
            handleFragment();
        }
    }

    private void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        TextView navHeaderTitle = (TextView)drawerLayout.findViewById(R.id.nav_header_title);
        TextView navHeaderEmail = (TextView)drawerLayout.findViewById(R.id.nav_header_email);
        CircleImageView navHeaderImage = (CircleImageView)drawerLayout.findViewById(R.id.profile_image);
        navHeaderTitle.setText(super.getUser().getFirstName()+" "+super.getUser().getLastName());
        navHeaderEmail.setText(super.getUser().getEmail());
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
                        Intent goToProfile = new Intent(OfferMealActivity.this, ProfileActivity.class);
                        startActivity(goToProfile);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.navigation_item_2:
                        Intent goToKitchenProfile = new Intent(OfferMealActivity.this, KitchenProfileActivity.class);
                        startActivity(goToKitchenProfile);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
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

    private void handleFragment(){
        Fragment fragment = new OfferMealFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.offer_meal_container, fragment);
        fragmentTransaction.commit();
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
}
