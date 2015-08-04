package com.cmu.delos.codenamealpha.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Vignan on 7/19/2015.
 */
public class AlphaProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private AlphaDBHelper mOpenHelper;

    private static final int USER = 100;
    private static final int USER_WITH_EMAIL = 101;
    private static final int KITCHEN = 200;
    private static final int KITCHEN_WITH_EMAIL = 201;
    private static final int MEAL = 300;
    private static final int MEAL_WITH_EMAIL = 301;
    private static final int MEAL_WITH_DISH_KITCHEN_ID = 302;
//    private static final int MEAL_WITH_KITCHEN_ID = 303;
    private static final int MEAL_WITH_DISH_NAME = 304;
    static final int ADDRESS = 400;
    static final int ADDRESS_WITH_USER_ID = 401;
    static final int TRANSACTION = 500;
    static final int TRANSACTION_WITH_CUST_ID = 501;

    private static final SQLiteQueryBuilder sKitchenByUserEmailQueryBuilder;
    private static final SQLiteQueryBuilder sMealByUserEmailQueryBuilder;
    private static final SQLiteQueryBuilder sgetMealwithproviderbykidandmealName;
    private static final SQLiteQueryBuilder sgetAddressWithUserDetails;

    static{
        sKitchenByUserEmailQueryBuilder = new SQLiteQueryBuilder();
        sMealByUserEmailQueryBuilder = new SQLiteQueryBuilder();
        sgetMealwithproviderbykidandmealName = new SQLiteQueryBuilder();
        sgetAddressWithUserDetails = new SQLiteQueryBuilder();

        //kitchen INNER JOIN user ON kitchen.user_id = user._id
        sKitchenByUserEmailQueryBuilder.setTables(
                AlphaContract.KitchenEntry.TABLE_NAME +
                        " INNER JOIN " +
                        AlphaContract.UserEntry.TABLE_NAME +
                        " ON " + AlphaContract.KitchenEntry.TABLE_NAME +
                        "." + AlphaContract.KitchenEntry.COLUMN_USER_ID +
                        " = " + AlphaContract.UserEntry.TABLE_NAME +
                        "." + AlphaContract.UserEntry._ID);

        //Address INNER JOIN user ON Address.user_id = user._id
        sgetAddressWithUserDetails.setTables(
                AlphaContract.AddressEntry.TABLE_NAME +
                        " INNER JOIN " +
                        AlphaContract.UserEntry.TABLE_NAME +
                        " ON " + AlphaContract.AddressEntry.TABLE_NAME +
                        "." + AlphaContract.AddressEntry.COLUMN_USER_ID+
                        " = " + AlphaContract.UserEntry.TABLE_NAME +
                        "." + AlphaContract.UserEntry._ID);

        //Meal,kitchen,user ON Meal.user_id = kitchen._id and kitchen.user_id = user._id
        sMealByUserEmailQueryBuilder.setTables(
                AlphaContract.MealEntry.TABLE_NAME +
                        " INNER JOIN " +
                        AlphaContract.KitchenEntry.TABLE_NAME +
                        " ON " + AlphaContract.MealEntry.TABLE_NAME +
                        "." + AlphaContract.MealEntry.COLUMN_KITCHEN_ID+
                        " = " + AlphaContract.KitchenEntry.TABLE_NAME +
                        "." + AlphaContract.KitchenEntry._ID +
                        " INNER JOIN " +
                        AlphaContract.UserEntry.TABLE_NAME +
                        " ON " + AlphaContract.KitchenEntry.TABLE_NAME +
                        "." + AlphaContract.KitchenEntry.COLUMN_USER_ID +
                        " = " + AlphaContract.UserEntry.TABLE_NAME +
                        "." + AlphaContract.UserEntry._ID);
        sgetMealwithproviderbykidandmealName.setTables(
                AlphaContract.MealEntry.TABLE_NAME +
                        " INNER JOIN " +
                        AlphaContract.KitchenEntry.TABLE_NAME +
                        " ON " + AlphaContract.KitchenEntry.TABLE_NAME +
                        "." + AlphaContract.KitchenEntry._ID +
                        " = " + AlphaContract.MealEntry.TABLE_NAME +
                        "." + AlphaContract.MealEntry.COLUMN_KITCHEN_ID);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AlphaContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, AlphaContract.PATH_USER, USER);
        matcher.addURI(authority, AlphaContract.PATH_USER + "/*", USER_WITH_EMAIL);
        matcher.addURI(authority, AlphaContract.PATH_KITCHEN, KITCHEN);
        matcher.addURI(authority, AlphaContract.PATH_KITCHEN + "/*", KITCHEN_WITH_EMAIL);
        matcher.addURI(authority, AlphaContract.PATH_MEAL, MEAL);
        matcher.addURI(authority, AlphaContract.PATH_MEAL + "/*", MEAL_WITH_EMAIL);
        matcher.addURI(authority, AlphaContract.PATH_MEAL + "/*/#", MEAL_WITH_DISH_KITCHEN_ID);
//        matcher.addURI(authority, AlphaContract.PATH_MEAL + "/#", MEAL_WITH_KITCHEN_ID);
        matcher.addURI(authority, AlphaContract.PATH_MEAL + "/*", MEAL_WITH_DISH_NAME);
        matcher.addURI(authority, AlphaContract.PATH_ADDRESS, ADDRESS);
        matcher.addURI(authority, AlphaContract.PATH_ADDRESS + "/#", ADDRESS_WITH_USER_ID);
        matcher.addURI(authority, AlphaContract.PATH_TRANSACTION, TRANSACTION);
        matcher.addURI(authority, AlphaContract.PATH_TRANSACTION + "/#", TRANSACTION_WITH_CUST_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new AlphaDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                return AlphaContract.UserEntry.CONTENT_TYPE;
            case USER_WITH_EMAIL:
                return AlphaContract.UserEntry.CONTENT_ITEM_TYPE;
            case KITCHEN:
                return AlphaContract.KitchenEntry.CONTENT_TYPE;
            case KITCHEN_WITH_EMAIL:
                return AlphaContract.KitchenEntry.CONTENT_ITEM_TYPE;
            case MEAL:
                return AlphaContract.MealEntry.CONTENT_TYPE;
            case MEAL_WITH_EMAIL:
                return AlphaContract.MealEntry.CONTENT_ITEM_TYPE;
            case MEAL_WITH_DISH_KITCHEN_ID:
                return AlphaContract.MealEntry.CONTENT_ITEM_TYPE;
            case MEAL_WITH_DISH_NAME:
                return AlphaContract.MealEntry.CONTENT_ITEM_TYPE;
            case ADDRESS:
                return AlphaContract.AddressEntry.CONTENT_TYPE;
            case ADDRESS_WITH_USER_ID:
                return AlphaContract.AddressEntry.CONTENT_ITEM_TYPE;
            case TRANSACTION:
                return AlphaContract.TransactionEntry.CONTENT_TYPE;
            case TRANSACTION_WITH_CUST_ID:
                return AlphaContract.TransactionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    //user.email=?
    private static final String sUserSelectionEmail =
            AlphaContract.UserEntry.TABLE_NAME +
                    "." + AlphaContract.UserEntry.COLUMN_EMAIL+ " = ? ";

    private static final String sAddressSelectWithUserId =
            AlphaContract.AddressEntry.TABLE_NAME +
                    "." + AlphaContract.AddressEntry.COLUMN_USER_ID+ " = ? ";

    private static final String sMealSelectWithKitchenId =
            AlphaContract.MealEntry.TABLE_NAME +
                    "." + AlphaContract.MealEntry.COLUMN_KITCHEN_ID+ " = ? ";

    private static final String sMealSelectWithKIdDishName=
            AlphaContract.MealEntry.TABLE_NAME +
                    "." + AlphaContract.MealEntry.COLUMN_KITCHEN_ID+" = ?  AND "+
                    AlphaContract.MealEntry.TABLE_NAME +
                    "." + AlphaContract.MealEntry.COLUMN_DISH_NAME+" = ? ";

    private static final String sMealSelectWithDishName=
            AlphaContract.MealEntry.TABLE_NAME +
                    "." + AlphaContract.MealEntry.COLUMN_DISH_NAME+" = ?  AND " + AlphaContract.MealEntry.TABLE_NAME +
                    "." + AlphaContract.MealEntry.COLUMN_IS_LISTED+" = 1 ";

    private static final String sTransactionWithCustId=
            AlphaContract.TransactionEntry.TABLE_NAME +
                    "." + AlphaContract.TransactionEntry.COLUMN_USER_ID_C+" = ? ";

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case USER_WITH_EMAIL: {
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(AlphaContract.UserEntry.TABLE_NAME,
                                projection,
                                sUserSelectionEmail,
                                new String[]{AlphaContract.UserEntry.getEmailFromUri(uri)},
                                null,
                                null,
                                sortOrder);
                break;
            }
            case ADDRESS_WITH_USER_ID: {
                retCursor = sgetAddressWithUserDetails
                        .query(mOpenHelper.getReadableDatabase(),
                                projection,
                                sAddressSelectWithUserId,
                                new String[]{String.valueOf(AlphaContract.AddressEntry.getuseridFromUri(uri))},
                                null,
                                null,
                                sortOrder);
                break;
            }
            case USER: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.UserEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case KITCHEN: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.KitchenEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case KITCHEN_WITH_EMAIL: {
                retCursor = sKitchenByUserEmailQueryBuilder
                        .query(mOpenHelper.getReadableDatabase(),
                                projection,
                                sUserSelectionEmail,
                                new String[]{AlphaContract.UserEntry.getEmailFromUri(uri)},
                                null,
                                null,
                                sortOrder);
                break;
            }
            case MEAL: {
                if(AlphaContract.MealEntry.getQueryParameterKeyFromUri(uri, AlphaContract.MealEntry.COLUMN_KITCHEN_ID)){
                    retCursor = mOpenHelper.getReadableDatabase()
                            .query(AlphaContract.MealEntry.TABLE_NAME,
                                    projection,
                                    sMealSelectWithKitchenId,
                                    new String[]{AlphaContract.MealEntry.getKidFromUri(uri, true)},
                                    null,
                                    null,
                                    sortOrder);
                }
                else{
                    retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.MealEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                }

                break;
            }
            case MEAL_WITH_EMAIL: {
                retCursor = sMealByUserEmailQueryBuilder
                        .query(mOpenHelper.getReadableDatabase(),
                                projection,
                                sUserSelectionEmail,
                                new String[]{AlphaContract.UserEntry.getEmailFromUri(uri)},
                                null,
                                null,
                                sortOrder);
                break;
            }
            case MEAL_WITH_DISH_KITCHEN_ID:{
                retCursor =  sMealByUserEmailQueryBuilder
                        .query(mOpenHelper.getReadableDatabase(),
                                projection,
                                sMealSelectWithKIdDishName,
                                new String[]{AlphaContract.MealEntry.getKidFromUri(uri, false),
                                        AlphaContract.MealEntry.getDishNameFromUri(uri)},
                                null,
                                null,
                                sortOrder);
                break;
            }

            case MEAL_WITH_DISH_NAME:{
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(AlphaContract.MealEntry.TABLE_NAME,
                                projection,
                                sMealSelectWithDishName,
                                new String[]{AlphaContract.MealEntry.getDishNameFromUri(uri)},
                                null,
                                null,
                                sortOrder);
                break;
            }

            case TRANSACTION_WITH_CUST_ID:{
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(AlphaContract.TransactionEntry.TABLE_NAME,
                                projection,
                                sTransactionWithCustId,
                                new String[]{String.valueOf(AlphaContract.TransactionEntry.getTransactionFromCustId(uri))},
                                null,
                                null,
                                sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case USER: {
                long _id = db.insert(AlphaContract.UserEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = AlphaContract.UserEntry.buildUserUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case KITCHEN:{
                long _id = db.insert(AlphaContract.KitchenEntry.TABLE_NAME,null,values);
                if(_id > 0)
                    returnUri = AlphaContract.KitchenEntry.buildKitchenUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case MEAL:{
                long _id = db.insert(AlphaContract.MealEntry.TABLE_NAME,null,values);
                if(_id > 0)
                    returnUri = AlphaContract.MealEntry.buildMealUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case ADDRESS:{
                long _id = db.insert(AlphaContract.AddressEntry.TABLE_NAME,null,values);
                if(_id > 0)
                    returnUri = AlphaContract.AddressEntry.buildAddressUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRANSACTION:{
                long _id = db.insert(AlphaContract.TransactionEntry.TABLE_NAME,null,values);
                if(_id > 0)
                    returnUri = AlphaContract.TransactionEntry.buildTransactionUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(null == selection) selection = "1";
        switch (match){
            case USER:
                rowsDeleted = db.delete(
                        AlphaContract.UserEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case KITCHEN:
                rowsDeleted = db.delete(
                        AlphaContract.KitchenEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MEAL:
                rowsDeleted = db.delete(
                        AlphaContract.MealEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MEAL_WITH_DISH_KITCHEN_ID:
                rowsDeleted = db.delete(AlphaContract.MealEntry.TABLE_NAME, sMealSelectWithKIdDishName,
                        new String[]{AlphaContract.MealEntry.getKidFromUri(uri, false),
                                AlphaContract.MealEntry.getDishNameFromUri(uri)});
                break;
            case ADDRESS:
                rowsDeleted = db.delete(
                        AlphaContract.AddressEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case USER:
                rowsUpdated = db.update(
                        AlphaContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_WITH_EMAIL:
                rowsUpdated = db.update(
                        AlphaContract.UserEntry.TABLE_NAME,
                        values,
                        sUserSelectionEmail,
                        new String[]{AlphaContract.UserEntry.getEmailFromUri(uri)}
                );
                break;
            case KITCHEN:
                rowsUpdated = db.update(AlphaContract.KitchenEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MEAL:
                rowsUpdated = db.update(AlphaContract.MealEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MEAL_WITH_DISH_KITCHEN_ID:
                rowsUpdated = db.update(AlphaContract.MealEntry.TABLE_NAME, values, sMealSelectWithKIdDishName,
                        new String[]{AlphaContract.MealEntry.getKidFromUri(uri, false),
                                AlphaContract.MealEntry.getDishNameFromUri(uri)});
                break;
            case ADDRESS:
                rowsUpdated = db.update(AlphaContract.AddressEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ADDRESS_WITH_USER_ID:
                rowsUpdated = db.update(AlphaContract.AddressEntry.TABLE_NAME, values, sAddressSelectWithUserId, new String[]{String.valueOf(AlphaContract.AddressEntry.getuseridFromUri(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

}
