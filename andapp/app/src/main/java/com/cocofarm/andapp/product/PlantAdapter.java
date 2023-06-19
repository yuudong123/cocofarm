package com.cocofarm.andapp.product;

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

    ArrayList<ProductVO>list;

    public PlantAdapter(ArrayList<ProductVO> list, Context context) {
        this.list = list;
        this.context=context;
    }

    Context context;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding=ItemProductBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ImageUtil.load(holder.binding.imgvProduct,list.get(i).getFilename());
        holder.binding.tvProductName.setText(list.get(i).getName()+"");
        holder.binding.tvProductPrice.setText(list.get(i).getPrice()+"");

        holder.binding.layoutProductItem.setOnClickListener(view -> {
            Intent intent = new Intent(context , ProductActivity.class);
            intent.putExtra("productVO", list.get(i));
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
            this.binding=binding;
        }
    }

}


