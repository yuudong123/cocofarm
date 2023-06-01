package com.cocofarm.andapp.board;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemNoticeBoardBinding;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter {

    ItemNoticeBoardBinding binding;
    ArrayList<BoardVO> list;

    public NoticeAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNoticeBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNoticeBoardBinding binding;

        public ViewHolder(ItemNoticeBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
