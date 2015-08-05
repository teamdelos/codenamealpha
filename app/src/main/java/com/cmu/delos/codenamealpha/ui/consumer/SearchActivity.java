package com.cmu.delos.codenamealpha.ui.consumer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;

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
import com.cmu.delos.codenamealpha.ui.LoginActivity;
import com.cmu.delos.codenamealpha.ui.ProfileActivity;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;

//import com.cmu.delos.codenamealpha.ui.MapsActivity;

public class SearchActivity extends AbstractAlphaActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView navHeaderImage;
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

    }

    private void handleFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment searchFragment = new SearchActivityFragment();
        fragmentTransaction.replace(R.id.search_container, searchFragment);
        Fragment searchResultFragment = new SearchResultFragment();
        fragmentTransaction.replace(R.id.search_result_container, searchResultFragment, SEARCHRESULTFRAGMENT_TAG);

        fragmentTransaction.commit();
    }


    private void setupNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        TextView navHeaderTitle = (TextView)drawerLayout.findViewById(R.id.nav_header_title);
        TextView navHeaderEmail = (TextView)drawerLayout.findViewById(R.id.nav_header_email);
        navHeaderImage = (CircleImageView)drawerLayout.findViewById(R.id.profile_image);

        navHeaderTitle.setText(super.getUser().getFirstName() + " " + super.getUser().getLastName());
        navHeaderEmail.setText(super.getUser().getEmail());
//        if(super.getUser().getImage()!=null){
//            setPic();
//        }

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
                    case R.id.navigation_item_14:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }
    private void setPic(){
        File imgFile = new File(super.getUser().getImage());

        // Part 1: Decode image
        Bitmap unscaledBitmap = decodeResource(imgFile, 500, 400, ScalingUtilities.ScalingLogic.FIT);

        // Part 2: Scale image
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 500,
                400, ScalingUtilities.ScalingLogic.FIT);
        unscaledBitmap.recycle();

        // Publish results
        navHeaderImage.setImageBitmap(scaledBitmap);
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

    @Override
    public void onResume(){
        super.onResume();
        if(super.getUser().getImage()!=null){
            setPic();
        }
    }


}
