/**
 * Transaction history activity which displays all the transactions of the consumer
 */
package com.cmu.delos.codenamealpha.ui.consumer;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.database.AlphaContract;
import com.cmu.delos.codenamealpha.model.Meal;
import com.cmu.delos.codenamealpha.model.User;
import com.cmu.delos.codenamealpha.ui.AbstractAlphaActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TransactionHistoryActivity extends AbstractAlphaActivity {

    private User user;
    private Meal meal;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> strArr;
    private ListView lv;
    private TextView  purchase_hist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        purchase_hist = (TextView) findViewById(R.id.purchase_hist);
        lv = (ListView) findViewById(R.id.listView1);
        user = getUser();
        Log.v("USER ", user.getFirstName());
        meal = getMeal();
        Cursor userCurser = null;
        userCurser = getContentResolver().query(AlphaContract.TransactionEntry.buildTransactionUriWithCustId(user.getUserId()), null, null, null, AlphaContract.TransactionEntry.COLUMN_TRAN_TIME+" DESC");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.US);
        Log.v("Count for transaction", userCurser.getCount() + "");
        if (userCurser.getCount() > 0) {
            strArr = new ArrayList<String>();
            try {

                for (userCurser.moveToFirst(); !userCurser.isAfterLast(); userCurser.moveToNext()) {
                    strArr.add("Meal Name : " + userCurser.getString(3) + System.getProperty("line.separator")+"Meal Price : " + userCurser.getDouble(4) + System.getProperty("line.separator")+"Date : " + getDate(Long.parseLong(userCurser.getString(5)), "dd/MM/yyyy hh:mm:ss"));

                }
            } finally {
                userCurser.close();
            }

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.meal_detail_list, // The name of the layout ID.
                    R.id.list_item_meal_details_textview,strArr);
            lv.setAdapter(adapter);
            purchase_hist.setText("Purchase History");

        } else {
            purchase_hist.setText("No Purchase History Yet");
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_history, menu);

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
