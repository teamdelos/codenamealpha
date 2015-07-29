package com.cmu.delos.codenamealpha.ui;


import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Address;


public class ProfileActivity extends AbstractAlphaActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText profile_full_name;
    private EditText profile_address_1;
    private EditText profile_address_2;
    private EditText profile_state;
    private EditText profile_city;
    private EditText profile_zip_code;
    private EditText editText;
    private Button saveProfileBtn;

    private String name;
    private String address1;
    private String address2;
    private String state;
    private String city;
    private String zipCode;
    private String about;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolbar();
        setupNavigationView();
        profile_full_name = (EditText)findViewById(R.id.profile_full_name);
        profile_address_1 = (EditText)findViewById(R.id.profile_address_1);
        profile_address_2 = (EditText)findViewById(R.id.profile_address_2);
        profile_state = (EditText)findViewById(R.id.profile_state);
        profile_city = (EditText)findViewById(R.id.profile_city);
        profile_zip_code = (EditText)findViewById(R.id.profile_zip_code);
        editText = (EditText)findViewById(R.id.editText);
        saveProfileBtn = (Button)findViewById(R.id.saveProfileBtn);
        Cursor userAddressCur = null;

        try {
            userAddressCur = getContentResolver().query(AlphaContract.AddressEntry.buildAddressUri(getUser().getUserId()), null, null, null, null);
            if (userAddressCur.getCount() > 0) {
                for (userAddressCur.moveToFirst(); !userAddressCur.isAfterLast(); userAddressCur.moveToNext()) {
                    address = new Address();
                    Log.v("Inside prof activity", getUser().getUserId()+"");
                    if (userAddressCur.getString(0) != null) {
                        address.setAddressId(userAddressCur.getInt(0));

                    }
                    if (userAddressCur.getString(2) != null) {
                        address.setZipCode(userAddressCur.getString(2));
                        profile_zip_code.setText(userAddressCur.getString(2));
                    }
                    if (userAddressCur.getString(3)!= null) {
                        address.setAbout(userAddressCur.getString(3));
                        editText.setText(userAddressCur.getString(3));
                    }
                    if (userAddressCur.getString(4)!= null) {
                        address.setName(userAddressCur.getString(4));
                        profile_full_name.setText(userAddressCur.getString(4));
                    }
                    if (userAddressCur.getString(5)!= null) {
                        address.setCity(userAddressCur.getString(5));
                        profile_city.setText(userAddressCur.getString(5));
                    }
                    if (userAddressCur.getString(6)!= null) {
                        address.setState(userAddressCur.getString(6));
                        profile_state.setText(userAddressCur.getString(6));
                    }
                    if (userAddressCur.getString(7)!= null) {
                        address.setStreetAddress1(userAddressCur.getString(7));
                        profile_address_1.setText(userAddressCur.getString(7));
                    }
                    if (userAddressCur.getString(8)!= null) {
                        address.setStreetAddress2(userAddressCur.getString(8));
                        profile_address_2.setText(userAddressCur.getString(8));
                    }
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (userAddressCur != null) {
                userAddressCur.close();
            }
        }
        handleSave();

    }

    private void handleSave() {
        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = profile_full_name.getText().toString();
                address1 = profile_address_1.getText().toString();
                address2 = profile_address_2.getText().toString();
                city = profile_city.getText().toString().trim();
                state = profile_state.getText().toString().trim();
                zipCode = profile_zip_code.getText().toString().trim();
                about = editText.getText().toString();
                if (!name.isEmpty() && !address1.isEmpty() && !address2.isEmpty()
                        && !city.isEmpty() && !state.isEmpty() && !zipCode.isEmpty()
                        && !about.isEmpty()) {
                    ContentValues userProfileDetails = new ContentValues();
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_USER_ID, address.getAddressId());
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_USER_NAME, name);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_USER_ID, getUser().getUserId());
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_CITY, city);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_STATE, state);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_STREET_1, address1);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_STREET_12, address2);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_PROFILE_ABOUT, about);
                    userProfileDetails.put(AlphaContract.AddressEntry.COLUMN_ZIPCODE, zipCode);
                    int ret = getContentResolver().update(AlphaContract.AddressEntry.buildAddrressUriWithid(address.getAddressId()), userProfileDetails, null, null);
                    Toast.makeText(getApplicationContext(), "Profile has been updated!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all * fields",
                            Toast.LENGTH_LONG).show();
                }
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
//                        Intent goToProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
//                        startActivity(goToProfile);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.navigation_item_2:
                        Intent goToSettings = new Intent(ProfileActivity.this, SettingsActivity.class);
                        startActivity(goToSettings);
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
