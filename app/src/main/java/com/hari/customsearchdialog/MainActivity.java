package com.hari.customsearchdialog;

import android.os.Bundle;
import android.util.Log;
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
    String selectedStateId;
    EditText spinner_state_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        spinner_state_name = findViewById(R.id.spinner_state_name);

        addItemInList();
        SearchDialog c = new SearchDialog(activity, spinnerList, "Search here", false,
                dialogInterface -> {
                    Log.i(TAG, "===========cancel");
                },
                position -> {
                    SpinnerItem item = spinnerList.get(position);
                    spinner_state_name.setText(item.getCityName());
                    selectedStateId = item.getCityId();
                });
        c.show();
    }

    private void addItemInList() {
        for (int i = 0; i < 100; i++) {
            spinnerList.add(new SpinnerItem("id" + i, "item" + i));
        }
    }
}
