package com.hari.customsearchdialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hari.customsearchdialog.dialog.CitySearchDialog;
import com.hari.customsearchdialog.model.CityItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity;
    List<CityItem> cityList = new ArrayList<>();
    String selectedCityId;
    EditText cityET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        cityET = findViewById(R.id.cityET);
        cityET.setFocusable(false);

        addItemInList();
        openDialog(null);
        cityET.setOnClickListener(v -> openDialog(v));
    }

    private void openDialog(View v) {
        CitySearchDialog c = new CitySearchDialog(activity, cityList, "Search here", false,
                dialogInterface -> {
                    Log.i(TAG, "===========cancel");
                },
                id -> {
                    for (CityItem item : cityList) {
                        if (item.getCityId().equals(id)) {
                            cityET.setText(item.getCityName());
                            selectedCityId = item.getCityId();
                        }
                    }
                });
        c.show();
    }

    private void addItemInList() {
        for (int i = 0; i < 100; i++) {
            cityList.add(new CityItem("id " + i, "City " + i));
        }
    }
}
