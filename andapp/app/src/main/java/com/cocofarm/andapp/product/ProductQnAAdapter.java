package com.cocofarm.andapp.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemProductQnaBinding;

import java.util.ArrayList;

public class ProductQnAAdapter extends RecyclerView.Adapter<ProductQnAAdapter.ViewHolder> {
    ItemProductQnaBinding binding;
    ArrayList<QnaDTO> list;

    public ProductQnAAdapter(ArrayList<QnaDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductQnaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.binding.tvNickname.setText(list.get(i).getNickname());
        holder.binding.tvRegdate.setText(CommonVal.yyyyMMddHHmmss.format(list.get(i).getRegdate()));
        holder.binding.tvContentQ.setText(list.get(i).getContent());
        if (list.get(i).getAnswer() != null) {
            holder.binding.tvTitle.setText("[답변완료] " + list.get(i).getTitle());
            holder.binding.tvAnswerNickname.setText(list.get(i).getAnswer().getNickname());
            holder.binding.tvContentA.setText(list.get(i).getAnswer().getContent());
            holder.binding.layoutAnswer.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvTitle.setText(list.get(i).getTitle());
        }
        holder.binding.layoutTitle.setOnClickListener(v -> {
            if (holder.binding.layoutContent.getVisibility() == View.GONE) {
                holder.binding.layoutContent.setVisibility(View.VISIBLE);
                holder.binding.ivDropBtn.setImageResource(R.drawable.icon_drop_up);
            } else {
                holder.binding.layoutContent.setVisibility(View.GONE);
                holder.binding.ivDropBtn.setImageResource(R.drawable.icon_drop_down);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductQnaBinding binding;

        public ViewHolder(@NonNull ItemProductQnaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
