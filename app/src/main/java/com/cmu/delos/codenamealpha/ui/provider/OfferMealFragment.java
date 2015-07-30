package com.cmu.delos.codenamealpha.ui.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Kitchen;
import com.cmu.delos.codenamealpha.ui.LoginActivity;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;


/**
 * A placeholder fragment containing a simple view.
 */
public class OfferMealFragment extends Fragment {
    private Button listMealButton;
    private EditText dish_name;
    private EditText dish_price;
    private EditText dish_quantity;
    private EditText dish_ingredients;
    private EditText dish_desc;
    private ImageButton addDishImageBtn;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    private int mealId;
    Bitmap photo;
    private Uri imageUri;

    public OfferMealFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_meal, container, false);
        dish_name = (EditText) rootView.findViewById(R.id.dish_name);
        dish_price = (EditText) rootView.findViewById(R.id.dish_price);
        dish_quantity = (EditText) rootView.findViewById(R.id.dish_quantity);
        dish_ingredients = (EditText) rootView.findViewById(R.id.dish_ingrediants);
        dish_desc = (EditText) rootView.findViewById(R.id.dish_desc);
        addDishImageBtn = (ImageButton) rootView.findViewById(R.id.addDishImage);
        listMealButton = (Button) rootView.findViewById(R.id.list_meal_button);
        uploadImagerHandler();
        listMealHandler();
        return rootView;
    }
    private void uploadImagerHandler(){
        addDishImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check camera
                if (getActivity().getPackageManager().hasSystemFeature(
                        PackageManager.FEATURE_CAMERA)) {
                    // Open default camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Toast.makeText(getActivity(), "There was a problem saving the image", Toast.LENGTH_LONG).show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        imageUri = Uri.fromFile(photoFile);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                } else {
                    Toast.makeText(getActivity(), "Camera not supported", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = imageUri;
            getActivity().getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getActivity().getContentResolver();
            try {
                photo = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, selectedImage);
                setPic();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Alpha_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",       /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
        addDishImageBtn.setImageBitmap(scaledBitmap);
    }

    private void listMealHandler(){
        listMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName = dish_name.getText().toString().trim();
                String dishPrice = dish_price.getText().toString().trim();
                String dishQuantity = dish_quantity.getText().toString().trim();
                String dishIngredients = dish_ingredients.getText().toString().trim();
                int dishIsListed = 1;
                String dishDesc = dish_desc.getText().toString().trim();

                Kitchen k = ((OfferMealActivity) getActivity()).getKitchen();
                if (!dishName.isEmpty() && !dishPrice.isEmpty()
                        && !dishQuantity.isEmpty()&& !dishIngredients.isEmpty()) {
                    Cursor mealCursor = getActivity().getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithKidDName(k.getKitchenId(), dishName),null,null,null,null);
                    if(mealCursor.getCount() == 0){
                        ContentValues mealDetails = new ContentValues();
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_KITCHEN_ID, k.getKitchenId());
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_DISH_NAME, dishName);
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_MEAL_PRICE, Double.valueOf(dishPrice));
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_MEAL_COUNT, Integer.valueOf(dishQuantity));
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_DISH_INGREDIENTS, dishIngredients);
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_SHORT_DESC, dishDesc);
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_IS_LISTED,dishIsListed);
                        mealDetails.put(AlphaContract.MealEntry.COLUMN_DISH_IMAGE,mCurrentPhotoPath);

                        Uri insertedMealUri = getActivity().getContentResolver().insert(AlphaContract.MealEntry.CONTENT_URI, mealDetails);
                        mealId = (int) ContentUris.parseId(insertedMealUri);
                        Toast.makeText(getActivity(), "Dish Added!",
                                Toast.LENGTH_LONG).show();

                        ((OfferMealActivity) getActivity())
                                .createMeal(
                                        mealId,
                                        k.getKitchenId(),
                                        dishName,
                                        dishDesc,
                                        dishIngredients,
                                        mCurrentPhotoPath,
                                        Integer.valueOf(dishQuantity),
                                        Double.valueOf(dishPrice)
                                );

                        setUpFragment();
                    } else {
                        Toast.makeText(getActivity(), "Dish already exists!",
                                Toast.LENGTH_LONG).show();
                    }
                    mealCursor.close();
                }
                else{
                    Toast.makeText(getActivity(), "Please fill all * fields!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpFragment(){
        Fragment fragment = new MealOfferCompleteFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.offer_meal_container, fragment);
        fragmentTransaction.commit();
    }
}
