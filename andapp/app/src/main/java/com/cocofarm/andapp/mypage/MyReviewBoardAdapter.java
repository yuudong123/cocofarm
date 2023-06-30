package com.cocofarm.andapp.mypage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.ItemMyreviewboardBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class MyReviewBoardAdapter extends RecyclerView.Adapter<MyReviewBoardAdapter.ViewHolder> {
    ItemMyreviewboardBinding binding;

    public MyReviewBoardAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    ArrayList<BoardVO> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMyreviewboardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        ImageUtil.load(holder.binding.ivProductImage, list.get(position).getMainimage());
        holder.binding.tvProductName.setText(list.get(position).getTitle());
        holder.binding.tvReviewview.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyreviewboardBinding binding;

        public ViewHolder(@NonNull ItemMyreviewboardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
