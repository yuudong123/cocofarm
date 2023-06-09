package com.cocofarm.andapp.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemMpAddressBinding;

public class AboutMeAdapter extends RecyclerView.Adapter<AboutMeAdapter.ViewHolder> {

    ItemMpAddressBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemMpAddressBinding.inflate(inflater, parent, false);

        ViewHolder vh = new ViewHolder(binding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return list.size;
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMpAddressBinding binding;

        public ViewHolder(ItemMpAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
