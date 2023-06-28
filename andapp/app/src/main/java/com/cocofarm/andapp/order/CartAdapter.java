package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CommonVal.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityCartBinding;
import com.cocofarm.andapp.databinding.ItemCartBinding;
import com.cocofarm.andapp.image.ImageUtil;

import java.text.DecimalFormat;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ItemCartBinding binding;
    DecimalFormat decimalFormat;

    CartActivity activity;

    public CartAdapter(CartActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ImageUtil.load(holder.binding.ivCartOrder1,cart.get(i).getProduct_image());
        holder.binding.tvCartOrderName.setText(cart.get(i).getProduct_name());
        holder.binding.tvCartProductPrice.setText(cart.get(i).getProduct_price() + "");
        holder.binding.tvProductBuyAmount.setText(cart.get(i).getAmount() + "");
        holder.binding.checkCartSelect.setChecked(cart.get(i).isChecked());
        String cartProductPrice = holder.binding.tvCartProductPrice.getText().toString();
        int intProductPrice = Integer.parseInt(cartProductPrice);
        String buyAmountText = holder.binding.tvProductBuyAmount.getText().toString();
        int amount = Integer.parseInt(buyAmountText);
        decimalFormat = new DecimalFormat("###,###");
        int totalAmount = intProductPrice * amount;
        String totalAmountPrice = decimalFormat.format(totalAmount);

        holder.binding.tvCartOrderPrice.setText("￦ " + totalAmountPrice + "원");
//
//        holder.binding.checkCartSelect.setOnClickListener(v ->{
//            cart.get(i).setChecked(holder.binding.checkCartSelect.isChecked());
//                    if(CartActivity.allSelect.isChecked()){
//                        CartActivity.allSelect.setChecked(false);
//                    }
//        });

        holder.binding.checkCartSelect.setOnCheckedChangeListener((compoundButton, isChecked) ->{
                CommonVal.cart.get(i).setChecked(isChecked);
                activity.isAllCheckChange();
            });

        holder.binding.tvOrderCancel.setOnClickListener(v->{
            int cartId = cart.get(i).getCart_id();
            CommonConn conn = new CommonConn(v.getContext(),"deletecartone.and");
            conn.addParam("cart_id", cartId+"");
            conn.addParam("member_no", CommonVal.loginMember.getMember_no());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    cart.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
