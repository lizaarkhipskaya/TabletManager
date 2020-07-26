package com.product.tabletmanager.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.product.tabletmanager.R;

import java.util.List;

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.ViewHolder> {

    private List<Drug> drugList;

    public DrugListAdapter(List<Drug> drugs) {
        drugList = drugs;
    }

    private OnClickListener onRemoveClickListener;
    private OnClickListener onItemClickListener;

    public void setNewData(List<Drug> drugs) {
        drugList = drugs;
        notifyDataSetChanged();
    }

    public void setOnRemoveClickListener(OnClickListener onRemoveClickListener) {
        this.onRemoveClickListener = onRemoveClickListener;
    }

    public void setOnItemClickListener(OnClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(drugList.get(position), v -> onRemoveClickListener.onClick(v, drugList.get(position)));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(v, drugList.get(position)));
    }

    @Override
    public int getItemCount() {
        if (drugList != null)
            return drugList.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView form;
        TextView name;
        ImageView remove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            form = itemView.findViewById(R.id.form);
            name = itemView.findViewById(R.id.name);
            remove = itemView.findViewById(R.id.btn_remove);
        }

        void bindView(Drug drug, View.OnClickListener listener) {
            form.setImageResource(getImageResIdByForm(drug.getForm()));
            name.setText(drug.getName());
            remove.setOnClickListener(listener);
        }

        @DrawableRes
        private int getImageResIdByForm(Drug.FORM form){
            switch (form){
                case PILL:
                    return R.drawable.ic_pill;
                case CAPSULE:
                    return R.drawable.ic_capsule;
                case LIQUID:
                    return R.drawable.ic_liquid;
                default:
                    return R.drawable.ic_pill;
            }
        }
    }

    public interface OnClickListener {
        void onClick(View view, Drug drug);
    }
}
