package com.cocofarm.andapp.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductImgBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class ProductImgAdapter extends RecyclerView.Adapter<ProductImgAdapter.ViewHolder> {

    ItemProductImgBinding binding;
    ArrayList<ImageDTO> list;

    public ProductImgAdapter(ArrayList<ImageDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductImgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageUtil.load(holder.binding.ivProductA1,list.get(position).getFilename());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductImgBinding binding;

        public ViewHolder(@NonNull ItemProductImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
