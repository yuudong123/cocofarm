package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CommonVal.comma;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemOrderBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ItemOrderBinding binding;
    ArrayList<CartDTO> list;

    public OrderAdapter(ArrayList<CartDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        CartDTO cartDTO = list.get(i);
        ImageUtil.load(holder.binding.ivOrderProduct, cartDTO.getProduct_image());
        holder.binding.tvOrderName.setText(cartDTO.getProduct_name());
        holder.binding.tvOrderAmount.setText(cartDTO.getAmount() + "개");
        holder.binding.tvOrderPrice.setText("각 " + comma(cartDTO.getProduct_price()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public ViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
