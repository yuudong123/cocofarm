package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.ItemProductReviewBinding;

import java.util.ArrayList;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ViewHolder> {

    ItemProductReviewBinding binding;
    ArrayList<BoardVO> list;

    public ProductReviewAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        BoardVO b = list.get(i);
        holder.binding.tvNickname.setText(b.getNickname());
        holder.binding.tvRegdate.setText(yyyyMMddHHmmss.format(b.getRegdate()));
        holder.binding.tvContentR.setText(b.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductReviewBinding binding;

        public ViewHolder(@NonNull ItemProductReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
