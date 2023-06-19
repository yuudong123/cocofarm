package com.cocofarm.andapp.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.FragmentHomeBinding;
import com.cocofarm.andapp.databinding.ItemRecvPdBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;

import java.text.DecimalFormat;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String price = decimalFormat.format(list.get(position).getPrice());

        holder.binding.tvName.setText(list.get(position).getName());
        holder.binding.tvPrice.setText("₩ " + price);
        ImageUtil.load(holder.binding.ivPd, list.get(position).getFilename());

        holder.binding.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productVO", list.get(position));
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
