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

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ItemEventBoardBinding binding;
    List<BoardVO> list;
    Context context;

    public EventAdapter(List<BoardVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemEventBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.tvTitle.setText(list.get(i).getTitle());
        holder.binding.tvRegdate.setText(yyyyMMddHHmmss.format(list.get(i).getRegdate()));
        ImageUtil.load(holder.binding.ivMainimage, list.get(i).getMainimage());
        holder.binding.itemEventBoard.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardReadActivity.class);
            intent.putExtra("BoardVO", list.get(i));
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
