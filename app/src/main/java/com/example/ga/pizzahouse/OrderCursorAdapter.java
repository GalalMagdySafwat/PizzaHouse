package com.example.ga.pizzahouse;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;


public class OrderCursorAdapter extends CursorAdapter {
    private Context context;
    private TextView orderTv;

    public OrderCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.orders_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        orderTv = view.findViewById(R.id.orderSummary);

        String order = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY));

        String date = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE));

        String time = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME));
        String orderSummary = order +"\n"+ " Date : " + date +"\n" +" Time : " + time+"\n";
        orderTv.setText(orderSummary);


    }
}