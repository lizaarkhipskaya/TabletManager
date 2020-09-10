package com.product.tabletmanager.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.product.tabletmanager.R;

import java.util.List;

public class TimeInfoAdapter extends RecyclerView.Adapter<TimeInfoAdapter.ViewHolder> {
    private List<String> mData;

    public TimeInfoAdapter(List<String> timeList) {
        mData = timeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new ViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTimeTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTimeTextView = (TextView) itemView;
        }
    }
}
