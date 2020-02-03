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
    public List<SpinnerItem> gridList;
    public Activity activity;
    private List<SpinnerItem> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public SpinAdapter(Activity activity, List<SpinnerItem> gridList) {
        this.activity = activity;
        this.gridList = gridList;
        this.gridListFiltered = gridList;
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
            SpinnerItem item = gridListFiltered.get(position);
            holder.CompanyNameTV.setText(item.getCityName());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(SpinnerItem dataObj, int index) {
        gridList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        gridList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return gridListFiltered.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        RelativeLayout mSquareView;
        TextView CompanyNameTV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            CompanyNameTV = itemView.findViewById(R.id.CompanyNameTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                gridListFiltered = gridList;
            } else {
                List<SpinnerItem> filteredList = new ArrayList<>();
                for (SpinnerItem row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) ) {
                        filteredList.add(row);
                    }
                }
                gridListFiltered = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = gridListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            gridListFiltered = (ArrayList<SpinnerItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
