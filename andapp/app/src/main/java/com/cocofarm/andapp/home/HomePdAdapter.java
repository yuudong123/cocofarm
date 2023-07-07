package com.cocofarm.andapp.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemRecvPdBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;

import java.util.List;

public class HomePdAdapter extends RecyclerView.Adapter<HomePdAdapter.ViewHolder> {

    ItemRecvPdBinding binding;
    List<ProductVO> list;
    Context context;

    public HomePdAdapter(List<ProductVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRecvPdBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ProductVO productVO = list.get(i);
        String price = CommonVal.comma(productVO.getPrice());
        holder.binding.tvName.setText(productVO.getName());
        holder.binding.tvPrice.setText("₩ " + price);
        ImageUtil.load(holder.binding.ivPd, productVO.getFilename());

        holder.binding.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productVO", productVO);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecvPdBinding binding;

        public ViewHolder(@NonNull ItemRecvPdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
