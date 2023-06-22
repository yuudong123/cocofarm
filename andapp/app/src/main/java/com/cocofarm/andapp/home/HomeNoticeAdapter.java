package com.cocofarm.andapp.home;

import static com.cocofarm.andapp.common.CommonVal.Md;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardReadActivity;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.ItemNoticeBoardBinding;

import java.util.List;

public class HomeNoticeAdapter extends RecyclerView.Adapter<HomeNoticeAdapter.ViewHolder> {

    ItemNoticeBoardBinding binding;
    List<BoardVO> list;
    Context context;

    public HomeNoticeAdapter(List<BoardVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeNoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNoticeBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeNoticeAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNoticeAdapter.ViewHolder holder, int i) {
        holder.binding.title.setText(list.get(i).getTitle());
        holder.binding.regdate.setText(Md.format(list.get(i).getRegdate()));
        if (list.get(i).getRegdate().getTime() != list.get(i).getUpddate().getTime()) {
            holder.binding.tvUpdated.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvUpdated.setVisibility(View.GONE);
        }
        holder.binding.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardReadActivity.class);
            intent.putExtra("BoardVO", list.get(i));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNoticeBoardBinding binding;

        public ViewHolder(ItemNoticeBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
