package com.cocofarm.andapp.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ItemCartBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    ItemCartBinding binding;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageUtil.load(holder.binding.ivCartOrder1, CommonVal.cart.get(position).getProduct_image());
        holder.binding.tvCartOrderName.setText(CommonVal.cart.get(position).getProduct_name());
        holder.binding.tvCartProductPrice.setText(CommonVal.cart.get(position).getProduct_price()+"");
        holder.binding.tvProductBuyAmount.setText(CommonVal.cart.get(position).getAmount()+"");
        holder.binding.checkCartSelect.setChecked(CommonVal.cart.get(position).isChecked());
        String cartProductPrice = holder.binding.tvCartProductPrice.getText().toString();
        int intProductPrice = Integer.parseInt(cartProductPrice);
        String buyAmountText = holder.binding.tvProductBuyAmount.getText().toString();
        int amount = Integer.parseInt(buyAmountText);
        int totalAmount = intProductPrice * amount;

        holder.binding.tvCartOrderPrice.setText("￦"+totalAmount+"원");


        holder.binding.checkCartSelect.setOnClickListener(v->{
            CommonVal.cart.get(position).setChecked(holder.binding.checkCartSelect.isChecked());
        });

        holder.binding.tvOrderCancel.setOnClickListener(v->{
            int cartId = CommonVal.cart.get(position).getCart_id();
            CommonConn conn = new CommonConn(v.getContext(),"deletecartone.and");
            conn.addParam("cart_id", cartId+"");
            conn.addParam("member_no", CommonVal.loginMember.getMember_no());
            conn.onExcute((isResult, data) -> {
                if(isResult){
                    CommonVal.cart.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    @Override
    public int getItemCount() {
        return CommonVal.cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
