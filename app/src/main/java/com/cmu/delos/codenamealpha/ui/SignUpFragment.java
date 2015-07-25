package com.cmu.delos.codenamealpha.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpFragment extends Fragment {

    private Button signUpPageBtn;
    private EditText signup_first_name;
    private EditText signup_last_name;
    private EditText signup_email;
    private EditText login_password;
    private EditText login_confirm_password;

    private String fName;
    private String lName;
    private String email;
    private String passwd;
    private String confirmPasswd;



    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUpPageBtn = (Button)view.findViewById(R.id.signUpPageBtn);
        signup_first_name = (EditText)view.findViewById(R.id.signup_first_name);
        signup_last_name = (EditText)view.findViewById(R.id.signup_last_name);
        signup_email = (EditText)view.findViewById(R.id.signup_email);
        login_password = (EditText)view.findViewById(R.id.login_password);
        login_confirm_password = (EditText)view.findViewById(R.id.login_confirm_password);

        signUpPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fName = signup_first_name.getText().toString().trim();
                lName = signup_last_name.getText().toString().trim();
                email = signup_email.getText().toString().trim();
                passwd = login_password.getText().toString().trim();
                confirmPasswd = login_confirm_password.getText().toString().trim();

                if (!fName.isEmpty() && !lName.isEmpty() && !email.isEmpty() && !passwd.isEmpty()
                        && !confirmPasswd.isEmpty() && passwd.equals(confirmPasswd)) {
                    //search

                    Cursor userCurser = getActivity().getContentResolver().query(AlphaContract.UserEntry.buildUserUriWithEmail(email),null,null,null,null);
                    if (userCurser.getCount() == 0) {
                        ContentValues userDetails = new ContentValues();
                        userDetails.put(AlphaContract.UserEntry.COLUMN_F_NAME, fName);
                        userDetails.put(AlphaContract.UserEntry.COLUMN_L_NAME, lName);
                        userDetails.put(AlphaContract.UserEntry.COLUMN_EMAIL, email);
                        userDetails.put(AlphaContract.UserEntry.COLUMN_PWD, passwd);

                        Uri insertedUri = getActivity().getContentResolver().insert(AlphaContract.UserEntry.CONTENT_URI, userDetails);
                        Intent intentToSignUp = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intentToSignUp);
                        userCurser.close();
                        Toast.makeText(getActivity(), "Account Created!",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "Email already exists!",
                            Toast.LENGTH_LONG).show();
                    }

                    //insert users
                } else {
                    Toast.makeText(getActivity(), "Please fill all * fields",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
        return view;
    }
}
