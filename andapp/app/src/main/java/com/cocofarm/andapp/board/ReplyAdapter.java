package com.cocofarm.andapp.board;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemReplyBinding;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    ItemReplyBinding binding;
    ArrayList<ReplyVO> list;

    public ReplyAdapter(ArrayList<ReplyVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.tvNickname.setText(list.get(i).getNickname());
        holder.binding.tvContent.setText(list.get(i).getContent());
        holder.binding.tvRegdate.setText(CommonVal.Md.format(list.get(i).getRegdate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemReplyBinding binding;

        public ViewHolder(ItemReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
