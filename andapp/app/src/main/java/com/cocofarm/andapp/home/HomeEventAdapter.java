package com.cocofarm.andapp.home;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardReadActivity;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.FragmentHomeBinding;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.ivMain.setClipToOutline(true);
            ImageUtil.load(holder.binding.ivMain, list.get(position % list.size()).getMainimage());
            holder.binding.ivMain.setOnClickListener(v -> {
                Intent intent = new Intent(context, BoardReadActivity.class);
                intent.putExtra("BoardVO", list.get(position));
                context.startActivity(intent);
            });
        }

    @Override
    public int getItemCount() {

        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemVpEventBinding binding;

        public ViewHolder(@NonNull ItemVpEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
