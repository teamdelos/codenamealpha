package com.cmu.delos.codenamealpha.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Address;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {

    private TextView profile_full_name;
    private EditText profile_address_1;
    private EditText profile_address_2;
    private EditText profile_state;
    private EditText profile_city;
    private EditText profile_zip_code;
    private EditText editText;
    private CircleImageView profile_image_button;
    private Button saveProfileBtn;

    private String mCurrentPhotoPath =null;
    private static int RESULT_LOAD_IMG = 1;

    private String name;
    private String address1;
    private String address2;
    private String state;
    private String city;
    private String zipCode;
    private String about;
    private Address address;

    private static final String[] PROFILE_COLUMNS = {
            AlphaContract.AddressEntry.TABLE_NAME+"."+ AlphaContract.AddressEntry._ID,
            AlphaContract.AddressEntry.COLUMN_ZIPCODE,
            AlphaContract.AddressEntry.COLUMN_STREET_1,
            AlphaContract.AddressEntry.COLUMN_STREET_12,
            AlphaContract.AddressEntry.COLUMN_CITY,
            AlphaContract.AddressEntry.COLUMN_STATE,
            AlphaContract.UserEntry.COLUMN_F_NAME,
            AlphaContract.UserEntry.COLUMN_L_NAME,
            AlphaContract.UserEntry.COLUMN_ABOUT,
            AlphaContract.UserEntry.COLUMN_IMAGE
    };

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_full_name = (TextView)rootView.findViewById(R.id.profile_full_name);
        profile_address_1 = (EditText)rootView.findViewById(R.id.profile_address_1);
        profile_address_2 = (EditText)rootView.findViewById(R.id.profile_address_2);
        profile_state = (EditText)rootView.findViewById(R.id.profile_state);
        profile_city = (EditText)rootView.findViewById(R.id.profile_city);
        profile_zip_code = (EditText)rootView.findViewById(R.id.profile_zip_code);
        editText = (EditText)rootView.findViewById(R.id.editText);
        profile_image_button = (CircleImageView) rootView.findViewById(R.id.profile_image_button);
        saveProfileBtn = (Button)rootView.findViewById(R.id.saveProfileBtn);
        Cursor userAddressCur = null;
        try {
            userAddressCur = getActivity().getContentResolver().query(AlphaContract.AddressEntry.buildAddrressUriWithid(((ProfileActivity) getActivity()).getUser().getUserId()), PROFILE_COLUMNS, null, null, null);

            if (userAddressCur.getCount() > 0) {
                for (userAddressCur.moveToFirst(); !userAddressCur.isAfterLast(); userAddressCur.moveToNext()) {
                    address = new Address();
                    if (userAddressCur.getString(0) != null) {
                        address.setAddressId(userAddressCur.getInt(0));

                    }
                    if (userAddressCur.getString(1) != null) {
                        address.setZipCode(userAddressCur.getString(1));
                        profile_zip_code.setText(userAddressCur.getString(1));
                    }
                    if (userAddressCur.getString(8)!= null) {
                        address.setAbout(userAddressCur.getString(8));
                        editText.setText(userAddressCur.getString(8));
                    }
                    if (userAddressCur.getString(6)!= null && userAddressCur.getString(7)!= null) {
                        address.setName(userAddressCur.getString(6)+ " "+userAddressCur.getString(7));
                        profile_full_name.setText(userAddressCur.getString(6)+ " "+userAddressCur.getString(7));
                    }
                    if (userAddressCur.getString(4)!= null) {
                        address.setCity(userAddressCur.getString(4));
                        profile_city.setText(userAddressCur.getString(4));
                    }
                    if (userAddressCur.getString(5)!= null) {
                        address.setState(userAddressCur.getString(5));
                        profile_state.setText(userAddressCur.getString(5));
                    }
                    if (userAddressCur.getString(2)!= null) {
                        address.setStreetAddress1(userAddressCur.getString(2));
                        profile_address_1.setText(userAddressCur.getString(2));
                    }
                    if (userAddressCur.getString(3)!= null) {
                        address.setStreetAddress2(userAddressCur.getString(3));
                        profile_address_2.setText(userAddressCur.getString(3));
                    }
                    if(userAddressCur.getString(9)!=null){
                        ((ProfileActivity) getActivity()).getUser().setImage(userAddressCur.getString(9));
                        mCurrentPhotoPath = userAddressCur.getString(9);
                        setPic();
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
        uploadImagerHandler();
        handleSave();

        return rootView;
    }

    private void uploadImagerHandler(){
        profile_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mCurrentPhotoPath = cursor.getString(columnIndex);
                ((ProfileActivity) getActivity()).getUser().setImage(mCurrentPhotoPath);
                setPic();
                cursor.close();

            }
            else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong!!!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void setPic() {
        File imgFile = new File(mCurrentPhotoPath);
        // Part 1: Decode image
        Bitmap unscaledBitmap = decodeResource(imgFile, 500, 400, ScalingUtilities.ScalingLogic.FIT);

        // Part 2: Scale image
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 500,
                400, ScalingUtilities.ScalingLogic.FIT);
        unscaledBitmap.recycle();

        // Publish results
        profile_image_button.setImageBitmap(scaledBitmap);
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
                if (!address1.isEmpty() && !address2.isEmpty()
                        && !city.isEmpty() && !state.isEmpty() && !zipCode.isEmpty()) {
                    ContentValues userAddressDetails = new ContentValues();
                    userAddressDetails.put(AlphaContract.AddressEntry.COLUMN_CITY, city);
                    userAddressDetails.put(AlphaContract.AddressEntry.COLUMN_STATE, state);
                    userAddressDetails.put(AlphaContract.AddressEntry.COLUMN_STREET_1, address1);
                    userAddressDetails.put(AlphaContract.AddressEntry.COLUMN_STREET_12, address2);
                    userAddressDetails.put(AlphaContract.AddressEntry.COLUMN_ZIPCODE, zipCode);
                    int ret = getActivity().getContentResolver().update(AlphaContract.AddressEntry.buildAddrressUriWithid(address.getAddressId()), userAddressDetails, null, null);

                    if(!about.isEmpty() || mCurrentPhotoPath==null){
                        ContentValues userProfileDetails = new ContentValues();
                        userProfileDetails.put(AlphaContract.UserEntry.COLUMN_ABOUT,about);
                        userProfileDetails.put(AlphaContract.UserEntry.COLUMN_IMAGE,mCurrentPhotoPath);
                        ((ProfileActivity) getActivity()).getUser().setImage(mCurrentPhotoPath);
                        int userRowsUpdated = getActivity().getContentResolver().update(AlphaContract.UserEntry.buildUserUriWithEmail(((ProfileActivity) getActivity()).getUser().getEmail()), userProfileDetails, null, null);
                    }

                    Toast.makeText(getActivity().getApplicationContext(), "Profile has been updated!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill all Address fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
