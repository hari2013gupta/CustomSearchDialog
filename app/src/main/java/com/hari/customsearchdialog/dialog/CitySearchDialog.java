package com.hari.customsearchdialog.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.hari.customsearchdialog.R;
import com.hari.customsearchdialog.adapter.CityAdapter;
import com.hari.customsearchdialog.model.CityItem;
import com.hari.customsearchdialog.util.MyDividerItemDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitySearchDialog extends Dialog {
    String TAG = CitySearchDialog.class.getCanonicalName();
    OnDialogIntOkClickListener onDialogIntOkClickListener;
    OnCancelListener onCancelListener;
    Activity activity;
    List<CityItem> cityList;
    String hintString;
    boolean can;

    public interface OnDialogIntOkClickListener {
        void onClicked(String id);
    }
    public CitySearchDialog(@NonNull Activity context, List<CityItem> cityList, String searchHint, boolean cancelable, @Nullable OnCancelListener cancelListener, OnDialogIntOkClickListener onDialogIntOkClickListener) {
        super(context, R.style.CustomDialog);
        this.onDialogIntOkClickListener = onDialogIntOkClickListener;
        this.onCancelListener = cancelListener;
        this.activity = context;
        this.cityList = cityList;
        this.hintString = searchHint;
        this.can = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("Select your city");

        setContentView(R.layout.dialog_city_search);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerDialog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(activity, DividerItemDecoration.VERTICAL, 36));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        CityAdapter cityAdapter = new CityAdapter(activity, cityList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(cityAdapter);

        cityAdapter.notifyDataSetChanged();
        cityAdapter.setOnItemClickListener((id, v) -> {
            if (onDialogIntOkClickListener != null) {
                onDialogIntOkClickListener.onClicked(id);
            }
            dismiss();
        });
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(view -> dismiss());

        EditText mSearchView = findViewById(R.id.ListSearch);
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cityAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        setOnCancelListener(dialogInterface -> {
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialogInterface);
            }
        });
        setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss();
            }
            return true;
        });
        mSearchView.setHint(hintString);
        setCancelable(can);
    }
}
