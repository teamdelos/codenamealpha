package com.cmu.delos.codenamealpha.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Vignan on 7/19/2015.
 */
public class AlphaProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private AlphaDBHelper mOpenHelper;

    static final int USER = 100;
    static final int USER_WITH_EMAIL = 101;
    static final int KITCHEN = 300;
    static final int ADDRESS = 400;
    static final int ADDRESS_WITH_USER_ID = 401;


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AlphaContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, AlphaContract.PATH_USER, USER);
        matcher.addURI(authority, AlphaContract.PATH_USER + "/*", USER_WITH_EMAIL);
        matcher.addURI(authority, AlphaContract.PATH_KITCHEN, KITCHEN);
        matcher.addURI(authority, AlphaContract.PATH_ADDRESS, ADDRESS);
        matcher.addURI(authority, AlphaContract.PATH_ADDRESS + "/#", ADDRESS_WITH_USER_ID);

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
            // Student: Uncomment and fill out these two cases
            case USER:
                return AlphaContract.UserEntry.CONTENT_TYPE;
            case USER_WITH_EMAIL:
                return AlphaContract.UserEntry.CONTENT_ITEM_TYPE;
            case KITCHEN:
                return AlphaContract.KitchenEntry.CONTENT_TYPE;
            case ADDRESS:
                return AlphaContract.AddressEntry.CONTENT_TYPE;
            case ADDRESS_WITH_USER_ID:
                return AlphaContract.AddressEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private static final String sUserSelectionEmail =
            AlphaContract.UserEntry.TABLE_NAME +
                    "." + AlphaContract.UserEntry.COLUMN_EMAIL+ " = ? ";

    private static final String sAddressSelectWithUserId =
            AlphaContract.AddressEntry.TABLE_NAME +
                    "." + AlphaContract.AddressEntry.COLUMN_USER_ID+ " = ? ";


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            //Weather with location and date
            //Weather with location
            case USER_WITH_EMAIL: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.UserEntry.TABLE_NAME,
                        projection,
                        sUserSelectionEmail,
                        new String[]{AlphaContract.UserEntry.getEmailFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case ADDRESS_WITH_USER_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.AddressEntry.TABLE_NAME,
                        projection,
                        sAddressSelectWithUserId,
                        new String[]{AlphaContract.UserEntry.getAddressFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //Weather
            case USER: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.UserEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            //Location
            case KITCHEN: {
                retCursor = mOpenHelper.getReadableDatabase().query(AlphaContract.KitchenEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
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

            case ADDRESS:{
                long _id = db.insert(AlphaContract.AddressEntry.TABLE_NAME,null,values);
                if(_id > 0)
                    returnUri = AlphaContract.AddressEntry.buildAddressUri(_id);
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
            case KITCHEN:
                rowsUpdated = db.update(AlphaContract.KitchenEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ADDRESS:
                rowsUpdated = db.update(AlphaContract.AddressEntry.TABLE_NAME, values, selection, selectionArgs);
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
