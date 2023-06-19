package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.board.QnAWriteActivity.qnaselectedproduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemImageSelectBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductVO;

import java.util.ArrayList;

public class QnAProductAdapter extends RecyclerView.Adapter<QnAProductAdapter.ViewHolder> {
    ItemImageSelectBinding binding;

    Context context;
    ArrayList<ProductVO> list;

    public QnAProductAdapter(Context context, ArrayList<ProductVO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemImageSelectBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ProductVO productVO = list.get(i);
        holder.binding.filename.setText(productVO.getName());
        holder.binding.thumbnail.setImageBitmap(ImageUtil.load(productVO.getFilename()));
        holder.binding.itemImageSelect.setOnClickListener(v -> {
            qnaselectedproduct = productVO;
            Toast.makeText(context, productVO.getName() + " 로 변경", Toast.LENGTH_SHORT).show();
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
