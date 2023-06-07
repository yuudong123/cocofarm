package com.cocofarm.andapp.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductBinding;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    ItemProductBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
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
        ItemProductBinding binding;


        public ViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}


