package com.cocofarm.andapp.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
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
        BoardVO boardVO = list.get(i);
        holder.binding.title.setText(boardVO.getTitle());
        holder.binding.regdate.setText(Md.format(boardVO.getRegdate()));
        if (boardVO.getRegdate().getTime() != boardVO.getUpddate().getTime()) {
            holder.binding.tvUpdated.setVisibility(VISIBLE);
        } else {
            holder.binding.tvUpdated.setVisibility(GONE);
        }
        holder.binding.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardReadActivity.class);
            intent.putExtra("BoardVO", boardVO);
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
