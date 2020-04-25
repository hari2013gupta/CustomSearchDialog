package com.hari.customsearchdialog.dialog;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hari.customsearchdialog.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SpinAdapter extends RecyclerView.Adapter<SpinAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = SpinAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<SpinnerItem> spinnerList;
    public Activity activity;
    static private List<SpinnerItem> spinListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public SpinAdapter(Activity activity, List<SpinnerItem> spinnerList) {
        this.activity = activity;
        this.spinnerList = spinnerList;
        this.spinListFiltered = spinnerList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataObjectHolder dataObjectHolder = null;
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spinner_item, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            SpinnerItem item = spinListFiltered.get(position);
            holder.nameTV.setText(item.getCityName());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(SpinnerItem dataObj, int index) {
        spinnerList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        spinnerList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return spinListFiltered.size();
    }

    public interface MyClickListener {
        void onItemClick(String id, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(spinListFiltered.get(getAdapterPosition()).getCityId() , v);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                spinListFiltered = spinnerList;
            } else {
                List<SpinnerItem> filteredList = new ArrayList<>();
                for (SpinnerItem row : spinnerList) {

                    // here we are looking for name  match
                    if (row.getCityName().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                    }
                }
                spinListFiltered = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = spinListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            spinListFiltered = (ArrayList<SpinnerItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
