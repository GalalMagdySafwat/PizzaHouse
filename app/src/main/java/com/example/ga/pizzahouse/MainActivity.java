package com.example.ga.pizzahouse;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private RecyclerView recyclerView;
    private ArrayList<Pizza> modelArrayList;
    private RecyclerViewAdapter adapter;
    private Toolbar mToolbar;
    private Button btnCheckOut;
    private FloatingActionButton floatingActionButton;
    private String[] pizzaList;
    private double[] priceList = new double[]{150, 140, 99.99, 130, 120};
    private String[] dList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        floatingActionButton = findViewById(R.id.call_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        final TextView emptyState = findViewById(R.id.empty_State);
        btnCheckOut = findViewById(R.id.next);
        emptyState.setVisibility(View.GONE);

        if (isNetworkOnline()) {
            refresh();

        } else {
            emptyState.setVisibility(View.VISIBLE);
            btnCheckOut.setVisibility(View.GONE);
            emptyState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkOnline()) {
                        emptyState.setVisibility(View.GONE);
                        btnCheckOut.setVisibility(View.VISIBLE);
                        refresh();
                    }
                }
            });
        }
    }
    private ArrayList<Pizza> getModel(boolean isSelect) {
        ArrayList<Pizza> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Pizza model = new Pizza();
            model.setSelected(isSelect);
            model.setPizzaName(pizzaList[i]);
            model.setPizzaPrice(priceList[i]);
            model.setPizzaDescription(dList[i]);
            list.add(model);
        }
        return list;
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

    public void refresh() {
        pizzaList = getResources().getStringArray(R.array.pizzaList);
        dList = getResources().getStringArray(R.array.dList);
        recyclerView = findViewById(R.id.recycler);


        modelArrayList = getModel(false);
        adapter = new RecyclerViewAdapter(this, modelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

    }

    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getString(R.string.PizzaHouseNumber)));
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.PermissionDenied), Toast.LENGTH_SHORT).show();
            }
        }
    }

}


