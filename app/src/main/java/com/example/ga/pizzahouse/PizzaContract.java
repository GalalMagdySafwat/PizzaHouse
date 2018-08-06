package com.example.ga.pizzahouse;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class PizzaContract {
    private PizzaContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.example.ga.pizzahouse";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_ORDER = "order";
    public static final class PizzaEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORDER);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORDER;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORDER;
        public static final String TABLE_NAME = "orders";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_ORDER_SUMMARY = "orderSummary";
        public static final String COLUMN_ORDER_DATE= "orderDate";
        public static final String COLUMN_ORDER_TIME= "orderTime";
    }
}
