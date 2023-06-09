package com.cocofarm.andapp.product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductDeviceBinding;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    Context context;

    ItemProductDeviceBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
      binding=ItemProductDeviceBinding.inflate(LayoutInflater.from(context),parent,false);
      return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.layoutProductDeviceItem.setOnClickListener(view -> {
            Intent intent = new Intent(context , ProductActivity.class);
            context.startActivity(intent);
        });

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

