package com.example.ga.pizzahouse;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import static com.example.ga.pizzahouse.PizzaContract.BASE_CONTENT_URI;
import static com.example.ga.pizzahouse.PizzaContract.PATH_ORDER;


public class OrdersWidgetService extends IntentService {
    public static final String ACTION_ORDER = "com.example.android.mygarden.action.get_order";

    public OrdersWidgetService() {
        super("OrdersWidgetService");
    }
    public static void startAction(Context context) {
        Intent intent = new Intent(context, OrdersWidgetService.class);
        intent.setAction(ACTION_ORDER);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ORDER.equals(action)) {


                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, OrdersWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.orderWidgetLV);
                OrdersWidgetProvider.updateOrderWidgetProvider(this, appWidgetManager, appWidgetIds);

            }

        }

    }
}
