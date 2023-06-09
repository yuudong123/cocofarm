package com.cocofarm.andapp.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductDeviceBinding;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    ItemProductDeviceBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding=ItemProductDeviceBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
      return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemProductDeviceBinding binding;

    public ViewHolder(@NonNull  ItemProductDeviceBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
}

