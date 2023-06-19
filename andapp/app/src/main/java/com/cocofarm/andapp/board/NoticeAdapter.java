package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.Md;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemNoticeBoardBinding;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    ItemNoticeBoardBinding binding;

    Context context;
    ArrayList<BoardVO> list;

    public NoticeAdapter(ArrayList<BoardVO> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNoticeBoardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        BoardVO boardVO = list.get(i);
        holder.binding.title.setText(boardVO.getTitle());
        holder.binding.regdate.setText(Md.format(boardVO.getRegdate()));
        holder.binding.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardReadActivity.class);
            intent.putExtra("BoardVO", boardVO);
            context.startActivity(intent);
        });
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
