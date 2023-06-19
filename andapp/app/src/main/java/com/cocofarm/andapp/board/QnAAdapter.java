package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemQnaBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QnAAdapter extends RecyclerView.Adapter<QnAAdapter.ViewHolder> {
    ItemQnaBoardBinding binding;

    Context context;
    ArrayList<QnaDTO> list;

    public QnAAdapter(ArrayList<QnaDTO> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemQnaBoardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        QnaDTO qnaDTO = list.get(i);
        holder.binding.title.setText(list.get(i).getTitle());
        if (qnaDTO.getReplycnt() == 0) {
            holder.binding.answer.setText("");
        }
        SimpleDateFormat sdf;
        if (new Date().getTime() - qnaDTO.getRegdate().getTime() < 86400000) {
            sdf = HHmmss;
        } else {
            sdf = Md;
        }
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
        ItemQnaBoardBinding binding;

        public ViewHolder(ItemQnaBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
