package com.cocofarm.andapp.mypage;

import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.isToday;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.QnAReadActivity;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.databinding.ItemMyBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        QnaDTO qnaDTO = list.get(i);
        holder.binding.title.setText(qnaDTO.getTitle());
        if (qnaDTO.getReplycnt() == 0) {
            holder.binding.answer.setText("");
        }
        SimpleDateFormat sdf = isToday(qnaDTO.getRegdate()) ? HHmmss : Md;

        holder.binding.title.setText(qnaDTO.getTitle());
        holder.binding.regdate.setText(sdf.format(qnaDTO.getRegdate()));

        holder.binding.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, QnAReadActivity.class);
            intent.putExtra("QnaDTO", qnaDTO);
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
