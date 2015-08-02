package com.cmu.delos.codenamealpha.ui.consumer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.User;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;
import com.cmu.delos.codenamealpha.ui.provider.MealOfferCompleteFragment;

public class MealDetails extends AbstractAlphaActivity {

    private Button buy_btn;

    private static final String[] MEAL_COLUMNS = {
            AlphaContract.MealEntry.TABLE_NAME+"."+ AlphaContract.MealEntry._ID,
            AlphaContract.MealEntry.COLUMN_KITCHEN_ID,
            AlphaContract.MealEntry.COLUMN_DISH_NAME,
            AlphaContract.MealEntry.COLUMN_MEAL_PRICE,
            AlphaContract.MealEntry.COLUMN_MEAL_COUNT,
            AlphaContract.MealEntry.COLUMN_DISH_INGREDIENTS,
            AlphaContract.MealEntry.COLUMN_SHORT_DESC,
            AlphaContract.MealEntry.COLUMN_DISH_IMAGE,
            AlphaContract.MealEntry.COLUMN_IS_LISTED,
            AlphaContract.UserEntry.COLUMN_F_NAME,
            AlphaContract.UserEntry.COLUMN_L_NAME,
            AlphaContract.UserEntry.TABLE_NAME+"."+AlphaContract.UserEntry._ID

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        buy_btn = (Button) findViewById(R.id.buy_btn);
        Intent intent = getIntent();
        Log.v("Read", intent.getStringExtra("dishName"));
        Cursor mealCursor = getContentResolver().query(AlphaContract.MealEntry.buildMealUriWithKidDName(intent.getIntExtra("kitchenId", 0), intent.getStringExtra("dishName")),MEAL_COLUMNS,null, null,null);
        Log.v("Count", mealCursor.getCount()+"");
        if (mealCursor.getCount() == 1) {
            try {
                for (mealCursor.moveToFirst(); !mealCursor.isAfterLast(); mealCursor.moveToNext()) {
                    createMeal(mealCursor.getInt(0), mealCursor.getInt(1), mealCursor.getString(2),
                            mealCursor.getString(6), mealCursor.getString(5), mealCursor.getString(7),
                            mealCursor.getInt(4), mealCursor.getDouble(3));
                    setIsProvider(false);
                    User provider = new User();
                    provider.setUserId(mealCursor.getInt(11));
                    provider.setFirstName(mealCursor.getString(9));
                    provider.setLastName(mealCursor.getString(10));

                    setProviderDetails(provider);
                    Log.v("User ID", mealCursor.getString(10));
                    break;
                }
                setUpFragment();
            }finally {
                mealCursor.close();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Should not be here!",
                    Toast.LENGTH_LONG).show();
        }
        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpFragment(){
        Fragment fragment = new MealOfferCompleteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.offer_meal_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
