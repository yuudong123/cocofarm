package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemImageSelectBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.image.ImageUtil;

import java.util.ArrayList;

public class QnAProductAdapter extends RecyclerView.Adapter<QnAProductAdapter.ViewHolder> {
    ItemImageSelectBinding binding;
    ArrayList<ImageDTO> list;
    Context context;

    public QnAProductAdapter(Context context, ArrayList<ImageDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemImageSelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.binding.filename.setText(list.get(i).getProduct_name());
        ImageUtil.load(holder.binding.thumbnail, list.get(i).getFilename());
        holder.binding.itemImageSelect.setOnClickListener(v -> {
            boardselectedImage = list.get(i).getFilename();
            Toast.makeText(context, list.get(i).getProduct_name() + " 로 변경", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemImageSelectBinding binding;

        public ViewHolder(ItemImageSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
