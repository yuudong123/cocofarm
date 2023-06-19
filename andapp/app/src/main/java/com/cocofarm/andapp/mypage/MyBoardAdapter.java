package com.cocofarm.andapp.mypage;

import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.board.QnAReadActivity;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.databinding.ItemMyBoardBinding;
import com.cocofarm.andapp.databinding.ItemQnaBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyBoardAdapter extends RecyclerView.Adapter<MyBoardAdapter.ViewHolder> {

    ItemMyBoardBinding binding;
    ArrayList<QnaDTO> list;
    Context context;

    public MyBoardAdapter(ArrayList<QnaDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemMyBoardBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QnaDTO qnaDTO = list.get(position);
        holder.binding.title.setText(list.get(position).getTitle());
        if (list.get(position).getReplycnt() == 0) {
            holder.binding.answer.setText("");
        }
        SimpleDateFormat sdf;
        if (new Date().getTime() - list.get(position).getRegdate().getTime() < 86400000) {
            sdf = HHmmss;
        } else {
            sdf = Md;
        }

        holder.binding.title.setText(list.get(position).getTitle());
        holder.binding.regdate.setText(sdf.format(list.get(position).getRegdate()));

        holder.binding.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, QnAReadActivity.class);
            intent.putExtra("QnaDTO", list.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyBoardBinding binding;

        public ViewHolder(ItemMyBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
