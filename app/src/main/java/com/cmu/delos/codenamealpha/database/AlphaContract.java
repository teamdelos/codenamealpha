package com.cmu.delos.codenamealpha.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class AlphaContract {

    public static final String CONTENT_AUTHORITY = "com.cmu.delos.codenamealpha";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_KITCHEN = "kitchen";


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
    }

}
