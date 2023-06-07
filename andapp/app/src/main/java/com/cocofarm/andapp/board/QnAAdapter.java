package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.parseDate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemQnaBoardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QnAAdapter extends RecyclerView.Adapter<QnAAdapter.ViewHolder> {

    ItemQnaBoardBinding binding;
    ArrayList<BoardVO> list;

    public QnAAdapter(ArrayList<BoardVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemQnaBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.rownum.setText(list.get(i).getRownum() + "");
        holder.binding.title.setText(list.get(i).getTitle());
        if (list.get(i).getReplycnt() == 0) {
            holder.binding.answer.setText("");
        }
        holder.binding.regdate.setText(parseDate(list.get(i).getRegdate(),Md));
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
