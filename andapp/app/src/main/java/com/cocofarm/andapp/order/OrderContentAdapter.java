package com.cocofarm.andapp.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemOrderContentBinding;

public class OrderContentAdapter extends RecyclerView.Adapter<OrderContentAdapter.ViewHolder> {

    ItemOrderContentBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ItemOrderContentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
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

        ItemOrderContentBinding binding;

        public ViewHolder(@NonNull ItemOrderContentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}


