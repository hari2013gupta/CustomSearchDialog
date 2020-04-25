package com.hari.customsearchdialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hari.customsearchdialog.dialog.SearchDialog;
import com.hari.customsearchdialog.dialog.SpinnerItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity;
    List<SpinnerItem> spinnerList = new ArrayList<>();
    String selectedId;
    EditText spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setFocusable(false);

        addItemInList();
        openDialog(null);
        spinner1.setOnClickListener(v -> openDialog(v));
    }

    private void openDialog(View v) {
        SearchDialog c = new SearchDialog(activity, spinnerList, "Search here", false,
                dialogInterface -> {
                    Log.i(TAG, "===========cancel");
                },
                id -> {
                    for (SpinnerItem item : spinnerList) {
                        if (item.getCityId().equals(id)) {
                            spinner1.setText(item.getCityName());
                            selectedId = item.getCityId();
                        }
                    }
                });
        c.show();
    }

    private void addItemInList() {
        for (int i = 0; i < 100; i++) {
            spinnerList.add(new SpinnerItem("id" + i, "item" + i));
        }
    }
}
