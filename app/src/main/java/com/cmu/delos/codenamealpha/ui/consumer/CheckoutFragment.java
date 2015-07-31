package com.cmu.delos.codenamealpha.ui.consumer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutFragment extends Fragment {
    private Button placeOrderBtn;
    private EditText order_full_name;
    private EditText order_type;
    private  EditText credit_card_number;
    private  EditText cc_expiry_date;
    private  EditText cvv_number;
    private  EditText profile_address_1;
    private  EditText profile_address_2;
    private  EditText profile_state;
    private  EditText profile_city;
    private  EditText profile_zip_code;
    private  EditText editText;
    public TextView total_view;
    public CheckoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_check_out_meal, container, false);
        placeOrderBtn = (Button)rootView.findViewById(R.id.btnPlaceOrder);
        total_view = (TextView)rootView.findViewById(R.id.total_view);
        order_full_name = (EditText)rootView.findViewById(R.id.order_full_name);
        order_type = (EditText)rootView.findViewById(R.id.order_type);
        credit_card_number = (EditText)rootView.findViewById(R.id.credit_card_number);
        cc_expiry_date = (EditText)rootView.findViewById(R.id.cc_expiry_date);
        cvv_number = (EditText)rootView.findViewById(R.id.cvv_number);
        profile_address_1 = (EditText)rootView.findViewById(R.id.profile_address_1);
        profile_address_2 = (EditText)rootView.findViewById(R.id.profile_address_2);
        profile_state = (EditText)rootView.findViewById(R.id.profile_state);

        profile_city = (EditText)rootView.findViewById(R.id.profile_city);

        profile_zip_code = (EditText)rootView.findViewById(R.id.profile_zip_code);

        editText = (EditText)rootView.findViewById(R.id.editText);

        order_full_name.setText(((AbstractAlphaActivity)getActivity()).getUser().getFirstName() + " "
                + ((AbstractAlphaActivity)getActivity()).getUser().getLastName());
        total_view.setText("Your Grand Total: $" + (((AbstractAlphaActivity)getActivity()).getMeal().getMealPrice()));
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OrderCompleteFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.meal_order_detail_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Cursor userAddressCur = null;

        try {
            userAddressCur = getActivity().getContentResolver().query(AlphaContract.AddressEntry.buildAddressUri(((AbstractAlphaActivity)getActivity()).getUser().getUserId()), null, null, null, null);
            if (userAddressCur.getCount() > 0) {
                for (userAddressCur.moveToFirst(); !userAddressCur.isAfterLast(); userAddressCur.moveToNext()) {

                    if (userAddressCur.getString(2) != null) {
                        profile_zip_code.setText(userAddressCur.getString(2));
                    }


                    if (userAddressCur.getString(5)!= null) {
                        profile_city.setText(userAddressCur.getString(5));
                    }
                    if (userAddressCur.getString(6)!= null) {
                        profile_state.setText(userAddressCur.getString(6));
                    }
                    if (userAddressCur.getString(7)!= null) {
                        profile_address_1.setText(userAddressCur.getString(7));
                    }
                    if (userAddressCur.getString(8)!= null) {
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
        return rootView;
    }
}
