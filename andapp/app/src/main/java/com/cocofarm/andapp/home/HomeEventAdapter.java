package com.cocofarm.andapp.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardReadActivity;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.ItemVpEventBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.List;

public class HomeEventAdapter extends RecyclerView.Adapter<HomeEventAdapter.ViewHolder> {

    ItemVpEventBinding binding;
    List<BoardVO> list;
    Context context;

    public HomeEventAdapter(List<BoardVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemVpEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        BoardVO boardVO = list.get(i);
        holder.binding.ivMain.setClipToOutline(true);
        ImageUtil.load(holder.binding.ivMain, boardVO.getMainimage());
        holder.binding.ivMain.setOnClickListener(v -> {
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
        ItemVpEventBinding binding;

        public ViewHolder(@NonNull ItemVpEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
