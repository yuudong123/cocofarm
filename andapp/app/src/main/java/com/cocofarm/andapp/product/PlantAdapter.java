package com.cocofarm.andapp.product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductBinding;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    ItemProductBinding binding;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding=ItemProductBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.layoutProductItem.setOnClickListener(view -> {
            Intent intent = new Intent(context , ProductActivity.class);
            context.startActivity(intent);
        });


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


