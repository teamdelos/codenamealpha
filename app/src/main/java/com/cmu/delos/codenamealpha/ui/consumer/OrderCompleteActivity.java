/**
 * A Order Compltete activity which inflates checkout and order complete fragments
 */
package com.cmu.delos.codenamealpha.ui.consumer;

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

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.util.SendMailTask;

public class OrderCompleteActivity extends AbstractAlphaActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private static final String ORDERCOMPLETEFRAGMENT_TAG = "OCTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        setupToolbar();
        if(savedInstanceState==null){
            handleFragment();
        }
    }


    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);
    }

    private void handleFragment(){
        Fragment fragment = new CheckoutFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.meal_order_detail_container, fragment);
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

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
//            super.onBackPressed();
//        }
//        if (getSupportFragmentManager().getBackStackEntryCount() == 2){
//            this.finish();
//        }
//        else {
//            getSupportFragmentManager().popBackStack();
//        }
//
//    }

    @Override
    public void onBackPressed() {
        final OrderCompleteFragment fragment = (OrderCompleteFragment) getSupportFragmentManager().findFragmentByTag(ORDERCOMPLETEFRAGMENT_TAG);

        if (fragment!= null && fragment.allowBackPressed()) {
            super.onBackPressed();
        }
    }

    public void sendEmail(String userEmail, String passWord, String sendEmail, String subject, String body) {
        new SendMailTask(OrderCompleteActivity.this).execute( userEmail,  passWord,  sendEmail,  subject,  body);
    }
}
