package com.cocofarm.andapp.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemCartBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    ItemCartBinding binding;
    ArrayList<CartDTO>list;

    public CartAdapter(ArrayList<CartDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageUtil.load(holder.binding.ivCartOrder1,list.get(position).getProduct_image());
        holder.binding.tvCartOrderName.setText(list.get(position).getProduct_id());
        holder.binding.tvCartProductPrice.setText(list.get(position).getProduct_price());

        String cartProductPrice = holder.binding.tvCartProductPrice.getText().toString();
        int intProductPrice = Integer.parseInt(cartProductPrice);
        String buyAmountText = holder.binding.tvProductBuyAmount.getText().toString();
        int amount = Integer.parseInt(buyAmountText);
        int totalAmount = intProductPrice * amount;

        holder.binding.tvCartOrderPrice.setText(String.valueOf(totalAmount));

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
