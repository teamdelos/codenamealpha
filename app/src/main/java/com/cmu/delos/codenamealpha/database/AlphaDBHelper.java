package com.cmu.delos.codenamealpha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cmu.delos.codenamealpha.database.AlphaContract.AddressEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.KitchenEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.MealEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.TransactionEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.UserEntry;
/**
 * DB helper class for creating and maintaining database and tables
 * Created by Vignan on 7/19/2015.
 */
public class AlphaDBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 10;

    static final String DATABASE_NAME = "codenamealpha.db";

    public AlphaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE "+ UserEntry.TABLE_NAME+" (" +
                UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                UserEntry.COLUMN_F_NAME + " TEXT NOT NULL, "+
                UserEntry.COLUMN_L_NAME + " TEXT NOT NULL, "+
                UserEntry.COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "+
                UserEntry.COLUMN_PWD + " TEXT NOT NULL, "+
                UserEntry.COLUMN_DOB + " TEXT, "+
                UserEntry.COLUMN_IMAGE + " TEXT, "+
                UserEntry.COLUMN_IS_PROVIDER + " TEXT, "+
                UserEntry.COLUMN_GENDER + " TEXT, "+
                UserEntry.COLUMN_MOBILE_NUMBER + " TEXT, "+
                UserEntry.COLUMN_ABOUT + " TEXT, "+
                " UNIQUE (" + UserEntry.COLUMN_EMAIL+ "));";


        final String SQL_CREATE_KITHCHEN_TABLE = "CREATE TABLE " + KitchenEntry.TABLE_NAME + " (" +
                KitchenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KitchenEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                KitchenEntry.COLUMN_SHORT_DESC + " TEXT, " +
                KitchenEntry.COLUMN_KITCHEN_IMAGE + " INTEGER," +
                KitchenEntry.COLUMN_FOOD_ALBUM + " TEXT, " +
                " UNIQUE (" + KitchenEntry.COLUMN_USER_ID+ ")"+
                " FOREIGN KEY (" + KitchenEntry.COLUMN_USER_ID+ ") REFERENCES " +
                UserEntry.TABLE_NAME + " (" + UserEntry._ID + "));";

        final String SQL_CREATE_MEAL_TABLE = "CREATE TABLE " + MealEntry.TABLE_NAME + " (" +
                MealEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MealEntry.COLUMN_KITCHEN_ID+ " INTEGER NOT NULL, " +
                MealEntry.COLUMN_DISH_NAME+ " TEXT NOT NULL, " +
                MealEntry.COLUMN_MEAL_PRICE + " REAL NOT NULL, " +
                MealEntry.COLUMN_MEAL_COUNT + " INTEGER NOT NULL, " +
                MealEntry.COLUMN_DISH_INGREDIENTS + " TEXT NOT NULL, "+
                MealEntry.COLUMN_SHORT_DESC + " TEXT," +
                MealEntry.COLUMN_DISH_IMAGE + " TEXT, " +
                MealEntry.COLUMN_IS_LISTED + " INTEGER NOT NULL, "+
                " UNIQUE (" + MealEntry.COLUMN_KITCHEN_ID+ ","+MealEntry.COLUMN_DISH_NAME +")"+
                " FOREIGN KEY (" + MealEntry.COLUMN_KITCHEN_ID+ ") REFERENCES " +
                KitchenEntry.TABLE_NAME + " (" + KitchenEntry._ID + "));";

        final String SQL_CREATE_ADRESS_TABLE = "CREATE TABLE " + AddressEntry.TABLE_NAME + " (" +
                AddressEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddressEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                AddressEntry.COLUMN_ZIPCODE + " TEXT, " +
                AddressEntry.COLUMN_CITY + " TEXT, " +
                AddressEntry.COLUMN_STATE + " TEXT, " +
                AddressEntry.COLUMN_STREET_1 + " TEXT, " +
                AddressEntry.COLUMN_STREET_12 + " TEXT, " +
                AddressEntry.COLUMN_PIC + " TEXT, " +
                " FOREIGN KEY (" + AddressEntry.COLUMN_USER_ID+ ") REFERENCES " +
                UserEntry.TABLE_NAME + " (" + UserEntry._ID + "));";

        final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TransactionEntry.TABLE_NAME + " (" +
                TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TransactionEntry.COLUMN_KITCHEN_ID + " INTEGER NOT NULL, " +
                TransactionEntry.COLUMN_MEAL_ID + " INTEGER NOT NULL, " +
                TransactionEntry.COLUMN_MEAL_NAME + " TEXT NOT NULL, " +
                TransactionEntry.COLUMN_MEAL_PRICE + " REAL NOT NULL, "+
                TransactionEntry.COLUMN_TRAN_TIME + " TEXT NOT NULL, " +
                TransactionEntry.COLUMN_USER_ID_C + " INTEGER NOT NULL, " +
                TransactionEntry.COLUMN_USER_ID_P + " INTEGER NOT NULL" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_KITHCHEN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MEAL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ADRESS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KitchenEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AddressEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
