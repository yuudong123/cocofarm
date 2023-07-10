package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.isToday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.databinding.ItemProductQnaBinding;

import java.text.SimpleDateFormat;
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
        QnaDTO qnaDTO = list.get(i);

        SimpleDateFormat sdf = isToday(qnaDTO.getRegdate()) ? HHmmss : Md;
        holder.binding.tvRegdate.setText(sdf.format(list.get(i).getRegdate()));

        holder.binding.tvContentQ.setText(qnaDTO.getContent());
        if (qnaDTO.getAnswer() != null) {
            holder.binding.tvTitle.setText("[답변완료] " + qnaDTO.getTitle());
            holder.binding.tvAnswerNickname.setText(qnaDTO.getAnswer().getNickname());
            holder.binding.tvContentA.setText(qnaDTO.getAnswer().getContent());
            holder.binding.layoutAnswer.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvTitle.setText(qnaDTO.getTitle());
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
