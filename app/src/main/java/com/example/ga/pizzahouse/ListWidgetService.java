package com.example.ga.pizzahouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import static com.example.ga.pizzahouse.PizzaContract.BASE_CONTENT_URI;
import static com.example.ga.pizzahouse.PizzaContract.PATH_ORDER;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}
class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    Cursor mCursor;
    public ListRemoteViewsFactory(Context applicationContext){
        mContext =applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri ORDER_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ORDER).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                ORDER_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        String orderID = mCursor.getString(mCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry._ID));

        String order = mCursor.getString(mCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY));

        String date = mCursor.getString(mCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE));

        String time = mCursor.getString(mCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME));
        String listViewItem = orderID +" "+order +"\n"+ " Date : " + date +"\n" +" Time : " + time;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_order_list);
        views.setTextViewText(R.id.orderItem,listViewItem);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
