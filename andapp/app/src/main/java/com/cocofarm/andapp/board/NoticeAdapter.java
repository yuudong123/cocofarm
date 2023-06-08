package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemNoticeBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    ItemNoticeBoardBinding binding;
    ArrayList<BoardVO> list;
    Context context;

    public NoticeAdapter(ArrayList<BoardVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ItemNoticeBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.rownum.setText(list.get(i).getRownum()+"");
        holder.binding.title.setText(list.get(i).getTitle());
        holder.binding.regdate.setText(Md.format(list.get(i).getRegdate()));
        holder.binding.item.setOnClickListener(v->{
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
        ItemNoticeBoardBinding binding;

        public ViewHolder(ItemNoticeBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
