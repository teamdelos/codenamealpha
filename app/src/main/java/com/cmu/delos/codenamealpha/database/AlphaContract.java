package com.cmu.delos.codenamealpha.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlphaContract {

    public static final String CONTENT_AUTHORITY = "com.cmu.delos.codenamealpha";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_KITCHEN = "kitchen";
    public static final String PATH_MEAL = "meal";
    public static final String PATH_ADDRESS = "address";
    public static final String PATH_TRANSACTION = "purchase";


    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_F_NAME = "first_name";
        public static final String COLUMN_L_NAME = "last_name";
        public static final String COLUMN_IS_PROVIDER = "is_provider";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PWD = "password";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_MOBILE_NUMBER = "mobile_number";


        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getEmailFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getAddressFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUserUriWithEmail(String email){
            return CONTENT_URI.buildUpon()
                    .appendPath(email).build();
        }

    }

    public static final class KitchenEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_KITCHEN).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KITCHEN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KITCHEN;

        public static final String TABLE_NAME = "kitchen";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_SHORT_DESC = "short_desc";
        public static final String COLUMN_KITCHEN_IMAGE = "kitchen_image";

        public static final String COLUMN_FOOD_ALBUM = "food_album";

        public static Uri buildKitchenUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildKitchenUriWithEmail(String email){
            return CONTENT_URI.buildUpon()
                    .appendPath(email).build();
        }
    }

    public static final class MealEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEAL).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEAL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEAL;

        public static final String TABLE_NAME = "meal";

        public static final String COLUMN_KITCHEN_ID = "kitchen_id";
        public static final String COLUMN_DISH_NAME = "dish_name";
        public static final String COLUMN_SHORT_DESC = "meal_desc";
        public static final String COLUMN_DISH_IMAGE = "dish_image";
        public static final String COLUMN_DISH_INGREDIENTS = "dish_ingredients";
        public static final String COLUMN_MEAL_COUNT = "meal_count";
        public static final String COLUMN_MEAL_PRICE = "meal_price";
        public static final String COLUMN_IS_LISTED = "is_listed";

        public static Uri buildMealUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMealUriWithKidDName(int kitchenId, String dishName) {
            return CONTENT_URI.buildUpon().appendPath(dishName)
                    .appendPath(Integer.toString(kitchenId)).build();
        }

        public static Uri buildMealUriWithName(String dishName) {
            return CONTENT_URI.buildUpon().appendPath(dishName).build();
        }

        public static Uri buildMealUriWithKid(int kitchenId) {
            return CONTENT_URI.buildUpon()
            .appendQueryParameter(COLUMN_KITCHEN_ID, Integer.toString(kitchenId))
                    .build();
        }



        public static boolean getQueryParameterKeyFromUri(Uri uri, String Key){
            return uri.getBooleanQueryParameter(Key,false);
        }

        public static String getKidFromUri(Uri uri, boolean onlyKid) {
            if(onlyKid) return uri.getQueryParameter(COLUMN_KITCHEN_ID);
            else return uri.getPathSegments().get(2);
        }
        public static String getDishNameFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class AddressEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ADDRESS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ADDRESS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ADDRESS;

        public static final String TABLE_NAME = "address";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_STREET_1 = "street_add_1";
        public static final String COLUMN_STREET_12 = "street_add_2";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ZIPCODE = "zipcode";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PIC = "picture";
        public static final String COLUMN_PROFILE_ABOUT = "profile_about";

        public static Uri buildAddressUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAddrressUriWithid(int userId) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_USER_ID, Integer.toString(userId))
                    .build();
        }

        public static String getuseridFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_USER_ID);
        }
    }

    public static final class TransactionEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACTION).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTION;


        public static Uri buildTransactionUriWithCustId(int custId) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_USER_ID_C, Integer.toString(custId))
                    .build();
        }
        public static final String TABLE_NAME = "purchase";

        public static final String COLUMN_USER_ID_C = "user_id_c";
        public static final String COLUMN_USER_ID_P = "user_id_p";
        public static final String COLUMN_MEAL_ID = "meal_id";
        public static final String COLUMN_TRAN_TIME = "time";
        public static final String COLUMN_MEAL_NAME = "meal_name";
        public static final String COLUMN_KITCHEN_ID = "kitchen_id";
        public static final String COLUMN_MEAL_PRICE = "meal_price";

        public static Uri buildTransactionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getTransactionFromCustId(Uri uri) {
            return uri.getQueryParameter(COLUMN_USER_ID_C);
        }


    }

}
