package com.example.ga.pizzahouse;

import android.content.ContentValues;

import android.content.Intent;
import android.net.Uri;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {
    private TextView tv;
    private double sumPrice;
    private String orderSummary;
    private Button order;
    private Button orderHistory;
    private Toolbar toolbar;
    @BindView(R.id.find)
    Button findUs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar = findViewById(R.id.checkout_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayShowTitleEnabled(false);}

        ButterKnife.bind(this);
        tv =  findViewById(R.id.tv);
        StringBuffer orderBuffer = new StringBuffer();
        for (int i = 0; i < RecyclerViewAdapter.modelArrayList.size(); i++){
            if(RecyclerViewAdapter.modelArrayList.get(i).isSelected()) {

                orderBuffer.append( " " + RecyclerViewAdapter.modelArrayList.get(i).getPizzaName()+" : "+RecyclerViewAdapter.modelArrayList.get(i).getPizzaPrice()+"\n");
                sumPrice += RecyclerViewAdapter.modelArrayList.get(i).getPizzaPrice();

            }
        }
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        SimpleDateFormat sdfD = new SimpleDateFormat("dd MMMM yyyy");
        final String date = sdfD.format(today);
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String time = sdft.format(today);
        orderSummary = orderBuffer+"\n"+"  total : "+ sumPrice ;
        String displayOrderSummary = orderSummary+ " " + date + " "+ time;
        tv.setText(displayOrderSummary);
        order = findViewById(R.id.button_id);
        if (sumPrice==0.00){
            order.setVisibility(View.GONE);
            tv.setText(getString(R.string.emptyCart));
        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sumPrice!=0.00) {
                    ContentValues orderValues = new ContentValues();
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY,
                            orderSummary);
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE,
                            date);
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME,
                            time);
                    getContentResolver().insert(
                            PizzaContract.PizzaEntry.CONTENT_URI,
                            orderValues);
                    OrdersWidgetService.startAction(CheckoutActivity.this);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
                    intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }else {

                    Toast.makeText(getApplicationContext(), getString(R.string.emptyCart), Toast.LENGTH_SHORT).show();

                }


            }
        });
        orderHistory = findViewById(R.id.orderAc);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckoutActivity.this,OrdersActivity.class);
                startActivity(intent1);
            }
        });
        findUs = findViewById(R.id.find);

        findUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent1 = new Intent(CheckoutActivity.this,MapsActivity.class);
                startActivity(intent1);

            }
        });

    }


}
