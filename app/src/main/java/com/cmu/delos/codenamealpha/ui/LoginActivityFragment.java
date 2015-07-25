package com.cmu.delos.codenamealpha.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.ui.consumer.SearchActivity;
import com.cmu.delos.codenamealpha.ui.provider.KitchenActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Button signUpBtn;
    private Button btnSignIn;
    private ImageButton fbBtn;
    private EditText login_username;
    private EditText login_password;

    private String email;
    private String password;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUpBtn = (Button)view.findViewById(R.id.signUpBtn);
        btnSignIn = (Button)view.findViewById(R.id.btnSignIn);
        fbBtn = (ImageButton) view.findViewById(R.id.fb_login_button);

        login_username = (EditText)view.findViewById(R.id.login_username);
        login_password = (EditText)view.findViewById(R.id.login_password);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click","signup Clicked");


                Intent intentToSignUp = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intentToSignUp);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = login_username.getText().toString().trim();
                password = login_password.getText().toString().trim();
                Log.v(email+password," Clicked");
                if (!email.isEmpty() && !password.isEmpty()) {
                    Log.v("inside after true", "signup Clicked");




                    //search

                    Cursor userCurser = getActivity().getContentResolver().query(AlphaContract.UserEntry.buildUserUriWithEmail(email),null,null,null,null);
                    Log.v("","count" + userCurser.getCount());
                    if (userCurser.getCount() > 0) {
                        Log.v("user details", userCurser.getString(5));
                        System.out.println("cursor details "+userCurser.getInt(1));
                        Intent intentToSignUp = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intentToSignUp);

                    } else {
                        Toast.makeText(getActivity(), "User doesnt exist!",
                                Toast.LENGTH_LONG).show();
                    }

                    //insert users


                } else {
                    Toast.makeText(getActivity(), "Please Enter correct fields!",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("on click","FbBtn Clicked");
                Intent intentToGoProvider = new Intent(getActivity(), KitchenActivity.class);
                startActivity(intentToGoProvider);
            }
        });
        return view;
    }
}
