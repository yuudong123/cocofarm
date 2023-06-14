package com.cocofarm.andapp.mypage;

import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.databinding.ItemMyBoardBinding;
import com.cocofarm.andapp.databinding.ItemQnaBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyBoardAdapter extends RecyclerView.Adapter<MyBoardAdapter.ViewHolder> {

    ItemMyBoardBinding binding;
    ArrayList<BoardVO> list;

    public MyBoardAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemMyBoardBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat sdf;
        if (new Date().getTime() - list.get(position).getRegdate().getTime() < 86400000) {
            sdf = HHmmss;
        } else {
            sdf = Md;
        }

        holder.binding.title.setText(list.get(position).getTitle());
        holder.binding.regdate.setText(sdf.format(list.get(position).getRegdate()));
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
