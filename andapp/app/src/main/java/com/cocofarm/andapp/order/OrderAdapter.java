package com.cocofarm.andapp.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemOrderBinding;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    ItemOrderBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public ViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
