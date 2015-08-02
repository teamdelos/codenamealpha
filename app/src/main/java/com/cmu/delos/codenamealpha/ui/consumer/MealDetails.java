package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.ui.ProfileActivity;
import com.cmu.delos.codenamealpha.ui.SettingsActivity;
import com.cmu.delos.codenamealpha.ui.provider.MealOfferCompleteFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MealDetails extends AbstractAlphaActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button buy_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        setupToolbar();
        setupNavigationView();
        buy_btn = (Button) findViewById(R.id.buy_btn);
        Intent intent = getIntent();
        Log.v("Read", intent.getStringExtra("dishName"));
        Cursor mealCursor = getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithKidDName(intent.getIntExtra("kitchenId", 0), intent.getStringExtra("dishName")),null,null,null,null);
        Log.v("Count", mealCursor.getCount()+"");
        if (mealCursor.getCount() == 1) {
            try {
                for (mealCursor.moveToFirst(); !mealCursor.isAfterLast(); mealCursor.moveToNext()) {
                    createMeal(mealCursor.getInt(0), mealCursor.getInt(1), mealCursor.getString(2),
                            mealCursor.getString(6), mealCursor.getString(5), mealCursor.getString(7),
                            mealCursor.getInt(4), mealCursor.getDouble(3));
                    setIsProvider(false);
                    break;
                }
                setUpFragment();
            }finally {
                mealCursor.close();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Should not be here!",
                    Toast.LENGTH_LONG).show();
        }
        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpFragment(){
        Fragment fragment = new MealOfferCompleteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.offer_meal_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal_details, menu);
        return true;
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

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        TextView navHeaderTitle = (TextView)drawerLayout.findViewById(R.id.nav_header_title);
        TextView navHeaderEmail = (TextView)drawerLayout.findViewById(R.id.nav_header_email);
        CircleImageView navHeaderImage = (CircleImageView)drawerLayout.findViewById(R.id.profile_image);
        navHeaderTitle.setText(super.getUser().getFirstName() + " " + super.getUser().getLastName());
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
                        Intent goToProfile = new Intent(MealDetails.this, ProfileActivity.class);
                        startActivity(goToProfile);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.navigation_item_2:
                        Intent goToSettings = new Intent(MealDetails.this, SettingsActivity.class);
                        startActivity(goToSettings);
                        return true;
                    case R.id.navigation_item_3:
//                        Intent goToHistory = new Intent(MealDetails.this, .class);
//                        startActivity(goToHistory);
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }
}
