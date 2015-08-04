package com.cmu.delos.codenamealpha.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;


import com.cmu.delos.codenamealpha.database.AlphaContract;
//import com.cmu.delos.codenamealpha.ui.consumer.MyActivity;
import com.cmu.delos.codenamealpha.ui.consumer.SearchActivity;
import com.cmu.delos.codenamealpha.ui.provider.KitchenActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private Button signUpBtn;
    private Button btnSignIn;
    protected LoginButton loginButton;

    //private ImageButton fbBtn;
    private EditText login_username;
    private EditText login_password;

    private String email;
    private String password;
    private CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intentToGoSearch = new Intent(getActivity(),SearchActivity.class);
                startActivity(intentToGoSearch);
            }

            @Override
            public void onCancel() {
                Intent intentToGoLogin = new Intent(getActivity(),LoginActivity.class);
                startActivity(intentToGoLogin);
            }

            @Override
            public void onError(FacebookException exception) {
                Intent intentToGoLogin = new Intent(getActivity(),LoginActivity.class);
                startActivity(intentToGoLogin);
            }
        });

        signUpBtn = (Button)view.findViewById(R.id.signUpBtn);
        btnSignIn = (Button)view.findViewById(R.id.btnSignIn);


        login_username = (EditText)view.findViewById(R.id.login_username);
        login_password = (EditText)view.findViewById(R.id.login_password);
        handleSignIn();
        handleSignUp();


/**
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("on click", "FbBtn Clicked");
                Intent intentToGoSearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentToGoSearch);

            }

        });
 **/
        return view;
    }

    private void handleSignIn(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = login_username.getText().toString().trim();
                password = login_password.getText().toString().trim();
                String isProvider = "";
                if (!email.isEmpty() && !password.isEmpty()) {
                    //search
                    Cursor userCursor = getActivity().getContentResolver().query(AlphaContract.UserEntry.buildUserUriWithEmail(email),null,null,null,null);
                    Cursor kitchenCursor = getActivity().getContentResolver().query(AlphaContract.KitchenEntry.buildKitchenUriWithEmail(email),null,null,null,null);
                    if (userCursor.getCount() == 1) {
                        String pwd=null;
                            try {
                            for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                                pwd = userCursor.getString(4);
                                isProvider = userCursor.getString(7);
                                ((LoginActivity) getActivity())
                                        .createUser(
                                                userCursor.getInt(0),
                                                userCursor.getString(1),
                                                userCursor.getString(2),
                                                userCursor.getString(3),
                                                userCursor.getString(6),
                                                userCursor.getString(7)
                                        );
                                break;
                            }
                        }finally {
                            userCursor.close();
                        }

                        if(password.equals(pwd)){
                            if (isProvider.equals("N")) {
                                Intent intentToSignUp = new Intent(getActivity(), SearchActivity.class);
                                startActivity(intentToSignUp);
                            } else {
                                try {
                                    for (kitchenCursor.moveToFirst(); !kitchenCursor.isAfterLast(); kitchenCursor.moveToNext()) {
                                        ((LoginActivity) getActivity())
                                                .createKitchen(
                                                        kitchenCursor.getInt(0),
                                                        kitchenCursor.getInt(1),
                                                        kitchenCursor.getString(2),
                                                        kitchenCursor.getString(3)
                                                );
                                        break;
                                    }
                                }finally {
                                    kitchenCursor.close();
                                }
                                Intent intentToSignUp = new Intent(getActivity(), KitchenActivity.class);
                                startActivity(intentToSignUp);
                            }
                        }
                        else{
                            login_password.setText("");
                            Toast.makeText(getActivity(), "Wrong Password. Please enter again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        login_username.setText("");
                        login_password.setText("");
                        Toast.makeText(getActivity(), "User does not exist!",
                                Toast.LENGTH_LONG).show();
                    }
                    //insert users
                } else {
                    Toast.makeText(getActivity(), "Please fill all fields!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void handleSignUp(){
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SignUpFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.login_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                login_username.setText("");
                login_password.setText("");
            }
        });
    }
}
