package com.cmu.delos.codenamealpha.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.ui.consumer.SearchActivity;
import com.cmu.delos.codenamealpha.ui.provider.KitchenActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Button signUpBtn;
    private Button btnSignIn;
    private ImageButton fbBtn;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUpBtn = (Button)view.findViewById(R.id.signUpBtn);
        btnSignIn = (Button)view.findViewById(R.id.btnSignIn);
        fbBtn = (ImageButton) view.findViewById(R.id.fb_login_button);
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

                Log.v("on click","signin Clicked");
                Intent intentToLogin = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentToLogin);
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
