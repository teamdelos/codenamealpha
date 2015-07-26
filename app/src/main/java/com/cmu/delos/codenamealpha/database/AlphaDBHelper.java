package com.cmu.delos.codenamealpha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cmu.delos.codenamealpha.database.AlphaContract.UserEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.KitchenEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.MealEntry;

public class AlphaDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

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
                UserEntry.COLUMN_GENDER + " TEXT, "+
                UserEntry.COLUMN_MOBILE_NUMBER + " TEXT, "+
                " UNIQUE (" + UserEntry.COLUMN_EMAIL+ "));";


        final String SQL_CREATE_KITHCHEN_TABLE = "CREATE TABLE " + KitchenEntry.TABLE_NAME + " (" +
                KitchenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KitchenEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                KitchenEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                KitchenEntry.COLUMN_KITCHEN_IMAGE + " INTEGER NOT NULL," +
                KitchenEntry.COLUMN_FOOD_ALBUM + " TEXT NOT NULL, " +
                " UNIQUE (" + KitchenEntry.COLUMN_USER_ID+ ")"+
                " FOREIGN KEY (" + KitchenEntry.COLUMN_USER_ID+ ") REFERENCES " +
                UserEntry.TABLE_NAME + " (" + UserEntry._ID + "));";

        final String SQL_CREATE_MEAL_TABLE = "CREATE TABLE " + MealEntry.TABLE_NAME + " (" +
                MealEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MealEntry.COLUMN_KITCHEN_ID+ " INTEGER NOT NULL, " +
                MealEntry.COLUMN_DISH_NAME+ " TEXT NOT NULL, " +
                MealEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL," +
                MealEntry.COLUMN_DISH_IMAGE + " TEXT NOT NULL, " +
                MealEntry.COLUMN_DISH_VIDEO + " TEXT NOT NULL, " +
                MealEntry.COLUMN_MEAL_COUNT + " INTEGER NOT NULL, " +
                MealEntry.COLUMN_MEAL_PRICE + " REAL NOT NULL, " +
                " UNIQUE (" + MealEntry.COLUMN_KITCHEN_ID+ ","+MealEntry.COLUMN_DISH_NAME +")"+
                " FOREIGN KEY (" + MealEntry.COLUMN_KITCHEN_ID+ ") REFERENCES " +
                KitchenEntry.TABLE_NAME + " (" + KitchenEntry._ID + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_KITHCHEN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MEAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KitchenEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MealEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
