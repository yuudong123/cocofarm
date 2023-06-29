package com.cocofarm.andapp.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ItemOrderAddProductBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class OrderProductAddAdapter extends RecyclerView.Adapter<OrderProductAddAdapter.ViewHolder>{
    ItemOrderAddProductBinding binding;

    CommonConn conn;

    Context context;
    ArrayList<OrderProductDTO> list;

    public OrderProductAddAdapter(ArrayList<OrderProductDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderAddProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String orderDate = CommonVal.Md.format(list.get(position).getOrderdate());
        holder.binding.tvOrderDate.setText(orderDate + " 결제");
        holder.binding.tvOrderPrice.setText("주문금액"+list.get(position).getPrice()+"");
        //holder.binding.tvOrderAmount.setText(list.get(position).getAmount());

        ArrayList<OrderProductVO> OrderProductList = list.get(position).getOrderproduct_list();

        OrderProductAdapter adapter = new OrderProductAdapter(OrderProductList);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.recvOrderList.setAdapter(adapter);
        binding.recvOrderList.setLayoutManager(manager);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderAddProductBinding binding;

        public ViewHolder(@NonNull ItemOrderAddProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
