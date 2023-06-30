package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemEventBoardBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ItemEventBoardBinding binding;

    Context context;
    ArrayList<BoardVO> list;

    public EventAdapter(ArrayList<BoardVO> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemEventBoardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        BoardVO boardVO = list.get(i);
        holder.binding.tvTitle.setText(boardVO.getTitle());
        holder.binding.tvRegdate.setText(yyyyMMddHHmmss.format(boardVO.getRegdate()));
        ImageUtil.load(holder.binding.ivMainimage, boardVO.getMainimage());
        holder.binding.itemEventBoard.setOnClickListener(v -> {
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
        ItemEventBoardBinding binding;

        public ViewHolder(ItemEventBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
