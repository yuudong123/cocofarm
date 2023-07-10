package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CommonVal.comma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemProductBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    ItemProductBinding binding;
    ArrayList<ProductVO> list;

    public PlantAdapter(ArrayList<ProductVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ProductVO productVO = list.get(i);
        ImageUtil.load(holder.binding.imgvProduct, productVO.getFilename());
        holder.binding.tvProductName.setText(productVO.getName() + "");
        holder.binding.tvProductPrice.setText(comma(productVO.getPrice()));

        holder.binding.layoutProductItem.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productVO", productVO);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


