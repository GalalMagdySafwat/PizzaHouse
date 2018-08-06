package com.example.ga.pizzahouse;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.ga.pizzahouse.PizzaContract.PizzaEntry;

public class PizzaProvider extends ContentProvider {
    private static final int Orders = 100;
    private static final int Orders_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PizzaContract.CONTENT_AUTHORITY, PizzaContract.PATH_ORDER, Orders);
        sUriMatcher.addURI(PizzaContract.CONTENT_AUTHORITY, PizzaContract.PATH_ORDER + "/#", Orders_ID);
    }


    public static final String LOG_TAG = PizzaProvider.class.getSimpleName();
    private PizzaHelper mHelper;
    @Override
    public boolean onCreate() {
        mHelper = new PizzaHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case Orders:
                cursor = database.query(PizzaContract.PizzaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case Orders_ID:
                selection = PizzaContract.PizzaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PizzaContract.PizzaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("can't query unkonwn URI" + uri);
        }
        return cursor;
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Orders:
                return insertOrder(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertOrder(Uri uri, ContentValues values) {

        String orderSummary = values.getAsString(PizzaEntry.COLUMN_ORDER_SUMMARY);
        if (orderSummary == null) {
            throw new IllegalArgumentException("Pet requires a order");
        }
        String orderDate = values.getAsString(PizzaEntry.COLUMN_ORDER_DATE);
        if (orderDate == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        String orderTime = values.getAsString(PizzaEntry.COLUMN_ORDER_TIME);
        if (orderTime == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }


        SQLiteDatabase database = mHelper.getWritableDatabase();
        long id = database.insert(PizzaEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Orders:
                return updateOrder(uri, contentValues, selection, selectionArgs);
            case Orders_ID:

                selection = PizzaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateOrder(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateOrder(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(PizzaEntry.COLUMN_ORDER_SUMMARY)) {
            String orderSummary = values.getAsString(PizzaEntry.COLUMN_ORDER_SUMMARY);
            if (orderSummary == null) {
                throw new IllegalArgumentException(" requires a order");
            }
            String orderDate = values.getAsString(PizzaEntry.COLUMN_ORDER_DATE);
            if (orderDate == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
            String orderTime = values.getAsString(PizzaEntry.COLUMN_ORDER_TIME);
            if (orderTime == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        if (values.size() == 0) {
            return 0;
        }


        SQLiteDatabase database = mHelper.getWritableDatabase();

        return database.update(PizzaEntry.TABLE_NAME, values, selection, selectionArgs);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Orders:
                // Delete all rows that match the selection and selection args
                return database.delete(PizzaEntry.TABLE_NAME, selection, selectionArgs);
            case Orders_ID:
                // Delete a single row given by the ID in the URI
                selection = PizzaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(PizzaEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Orders:
                return PizzaEntry.CONTENT_LIST_TYPE;
            case Orders_ID:
                return PizzaEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
