package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.comma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemOrderAddProductBinding;

import java.util.ArrayList;

public class OrderProductAddAdapter extends RecyclerView.Adapter<OrderProductAddAdapter.ViewHolder> {
    ItemOrderAddProductBinding binding;

    Context context;
    ArrayList<OrderProductDTO> list;

    public OrderProductAddAdapter(ArrayList<OrderProductDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderAddProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        OrderProductDTO dto = list.get(i);
        String orderDate = Md.format(dto.getOrderdate());
        holder.binding.tvOrderDate.setText(orderDate + " 결제");
        holder.binding.tvOrderPrice.setText("주문금액 " + comma(dto.getPrice()));

        OrderProductAdapter adapter = new OrderProductAdapter(dto.getOrderproduct_list());
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
