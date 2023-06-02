package com.cocofarm.andapp.board;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemEventBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ItemEventBoardBinding binding;
    ArrayList<BoardVO> list;

    public EventAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemEventBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.title.setText(list.get(i).getTitle());
        holder.binding.regdate.setText(new SimpleDateFormat("M월 d일").format(list.get(i).getRegdate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemEventBoardBinding binding;

        public ViewHolder(ItemEventBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
