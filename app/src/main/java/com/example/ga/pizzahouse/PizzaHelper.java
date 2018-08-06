package com.example.ga.pizzahouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ga.pizzahouse.PizzaContract.PizzaEntry;

public class PizzaHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "orders.db";
    private static final int DATABASE_VERSION = 1;

    public PizzaHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ORDER_TABLE = "CREATE TABLE " + PizzaEntry.TABLE_NAME + " (" +
                PizzaEntry._ID + " INTEGER PRIMARY KEY, "+
                PizzaEntry.COLUMN_ORDER_SUMMARY + " TEXT NOT NULL, " +
                PizzaEntry.COLUMN_ORDER_DATE + " TEXT NOT NULL, " +
                PizzaEntry.COLUMN_ORDER_TIME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
