package com.cmu.delos.codenamealpha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cmu.delos.codenamealpha.database.AlphaContract.UserEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.KitchenEntry;
import com.cmu.delos.codenamealpha.database.AlphaContract.AddressEntry;

public class AlphaDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

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
                " UNIQUE (" + UserEntry.COLUMN_EMAIL+ "));";


        final String SQL_CREATE_KITHCHEN_TABLE = "CREATE TABLE " + KitchenEntry.TABLE_NAME + " (" +
                KitchenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KitchenEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                KitchenEntry.COLUMN_SHORT_DESC + " TEXT, " +
                KitchenEntry.COLUMN_KITCHEN_IMAGE + " INTEGER," +
                KitchenEntry.COLUMN_FOOD_ALBUM + " REAL, " +
                " FOREIGN KEY (" + KitchenEntry.COLUMN_USER_ID+ ") REFERENCES " +
                UserEntry.TABLE_NAME + " (" + UserEntry._ID + "));";

        final String SQL_CREATE_ADRESS_TABLE = "CREATE TABLE " + AddressEntry.TABLE_NAME + " (" +
                AddressEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddressEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                AddressEntry.COLUMN_ZIPCODE + " TEXT, " +
                AddressEntry.COLUMN_PROFILE_ABOUT + " TEXT," +
                AddressEntry.COLUMN_USER_NAME + " TEXT," +
                AddressEntry.COLUMN_CITY + " TEXT, " +
                AddressEntry.COLUMN_STATE + " TEXT, " +
                AddressEntry.COLUMN_STREET_1 + " TEXT, " +
                AddressEntry.COLUMN_STREET_12 + " TEXT, " +
                AddressEntry.COLUMN_PIC + " TEXT, " +
                " FOREIGN KEY (" + AddressEntry.COLUMN_USER_ID+ ") REFERENCES " +
                UserEntry.TABLE_NAME + " (" + UserEntry._ID + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_KITHCHEN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ADRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KitchenEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AddressEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
