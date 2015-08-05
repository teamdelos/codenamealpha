/**
 * Login Activity for handling login & signup fragments
 */
package com.cmu.delos.codenamealpha.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.ProfileTracker;
import com.facebook.Profile;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.facebook.AccessTokenTracker;
import com.facebook.AccessToken;

import com.cmu.delos.codenamealpha.ui.consumer.SearchActivity;
import android.content.Intent;

import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.cmu.delos.codenamealpha.R;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AbstractAlphaActivity {

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    ProfileTracker profileTracker;
    protected LoginButton loginButton;
    protected LoginManager LoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

        callbackManager = CallbackManager.Factory.create();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Add App code here
                        Intent intentToGoSearch = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intentToGoSearch);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Intent intentToGoSearch = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentToGoSearch);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Intent intentToSearch = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentToSearch);
                    }
                });

        setContentView(R.layout.activity_login);
        //check the changes with the saved instance state and do the rest
        if(savedInstanceState==null){
            handleFragment();
        }
    }

    private void handleFragment(){
            Fragment fragment = new LoginFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.login_container, fragment);
            fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

}
