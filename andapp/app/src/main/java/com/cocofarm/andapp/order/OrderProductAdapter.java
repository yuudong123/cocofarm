package com.cocofarm.andapp.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ItemOrderProductBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    Context context;

    ItemOrderProductBinding binding;
    ArrayList<OrderProductVO>list;

    public OrderProductAdapter(ArrayList<OrderProductVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ItemOrderProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
        }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String orderDate = CommonVal.Md.format(list.get(position).getOrderdate());
        holder.binding.tvOrderDay.setText(orderDate+" 결제");
        holder.binding.tvOrderReady.setText(list.get(position).getValue());
        ImageUtil.load(holder.binding.ivCartOrder1,list.get(position).getFilename());
        holder.binding.tvOrderproductName.setText(list.get(position).getName());
        holder.binding.tvOrderproductPrice.setText("각"+list.get(position).getPrice()+" / ");
        holder.binding.tvOrderproductAmount.setText(list.get(position).getAmount()+"개");

        OrderProductVO orderProduct = list.get(position);
        int orderStatus = orderProduct.getOrder_status_cd();

        if (orderStatus ==CodeTable.ORDER_STATUS_ONREADY) {
            //주문 시점
            holder.binding.btnsOnready.setVisibility(View.VISIBLE);
            holder.binding.btnsOndelivery.setVisibility(View.GONE);
            holder.binding.btnsArrived.setVisibility(View.GONE);
            holder.binding.btnsArrivedNOK.setVisibility(View.GONE);
            holder.binding.btnsChangeNRefund.setVisibility(View.GONE);

            holder.binding.btnCancel.setOnClickListener(v->{
                //취소 버튼 눌렀을 경우 주문목록에서 삭제.
            });
            holder.binding.btnDeliveryCheck.setOnClickListener(v->{
                //배송조회 눌렀을 경우 배송조회 페이지로 이동 배송시작 글자만 뜨게.
            });

        }else if(orderStatus == CodeTable.ORDER_STATUS_ONDELIVERY) {
            //배송중
            //취소 불가능 배송조회 버튼만 뜸.
            binding.btnsOnready.setVisibility(View.GONE);
            binding.btnsOndelivery.setVisibility(View.VISIBLE);
            binding.btnsArrived.setVisibility(View.GONE);
            binding.btnsArrivedNOK.setVisibility(View.GONE);
            binding.btnsChangeNRefund.setVisibility(View.GONE);

            binding.btnDeliveryCheck.setOnClickListener(v->{
                //배송조회 눌렀을 경우 배송조회 페이지로 이동
            });

        } else if (orderStatus ==CodeTable.ORDER_STATUS_ARRIVED) {
            //배송완료 시점.
            holder.binding.btnsOnready.setVisibility(View.GONE);
            holder.binding.btnsOndelivery.setVisibility(View.GONE);
            holder.binding.btnsArrived.setVisibility(View.VISIBLE);
            holder.binding.btnsArrivedNOK.setVisibility(View.GONE);
            holder.binding.btnsChangeNRefund.setVisibility(View.GONE);

            holder.binding.btnsChangeNRefund.setOnClickListener(v->{
                //교환반품 신청 버튼 눌렀을때
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("").setMessage("교환신청을 원하시면 교환, 반품신청을 원하시면 반품을 눌러주세요.").setCancelable(false)
                        .setPositiveButton("교환", (dialogInterface, i1) -> {
                            Intent intent = new Intent(context, OrderChangeActivity.class);
                            context.startActivity(intent);
                        })
                        .setNegativeButton("반품", (dialogInterface, i1) -> {
                            Intent intent = new Intent(context, OrderRefundActivity.class);
                            context.startActivity(intent);
                        }).create().show();
            });

            //배송완료 시점에서 구매확정 눌렀을때 리뷰쓰기만 보이게 설정.
            holder.binding.btnRealbuy.setOnClickListener(v->{
                holder.binding.btnsOnready.setVisibility(View.GONE);
                holder.binding.btnsOndelivery.setVisibility(View.GONE);
                holder.binding.btnsArrived.setVisibility(View.GONE);
                holder.binding.btnsArrivedNOK.setVisibility(View.VISIBLE);
                holder.binding.btnsChangeNRefund.setVisibility(View.GONE);
            });

            //리뷰쓰기 누르면 리뷰쓰는 페이지로 이동 후 버튼들 다 사라짐.
            holder.binding.btnReview.setOnClickListener(v->{
                binding.btnsOnready.setVisibility(View.GONE);
                binding.btnsOndelivery.setVisibility(View.GONE);
                binding.btnsArrived.setVisibility(View.GONE);
                binding.btnsArrivedNOK.setVisibility(View.GONE);
                binding.btnsChangeNRefund.setVisibility(View.GONE);
            });
        }

        else if (orderStatus == CodeTable.ORDER_STATUS_RETURN_REQ||orderStatus ==CodeTable.ORDER_STATUS_REFUND_REQ) {
            //교환반품 신청 하고 난후에 304.305상태면 교환반품 취소할수있음.
            holder.binding.btnsOnready.setVisibility(View.GONE);
            holder.binding.btnsOndelivery.setVisibility(View.GONE);
            holder.binding.btnsArrived.setVisibility(View.GONE);
            holder.binding.btnsArrivedNOK.setVisibility(View.GONE);
            holder.binding.btnsChangeNRefund.setVisibility(View.VISIBLE);

            //교환반품 신청하고 완료전에 취소 신청을 했을때 배송완료로 돌아와야함.
            holder.binding.btnChangeRefundcancel.setOnClickListener(v -> {
                holder.binding.btnsOnready.setVisibility(View.GONE);
                holder.binding.btnsOndelivery.setVisibility(View.GONE);
                holder.binding.btnsArrived.setVisibility(View.VISIBLE);
                holder.binding.btnsArrivedNOK.setVisibility(View.GONE);
                holder.binding.btnsChangeNRefund.setVisibility(View.GONE);
                //취소 하시겠습니까? 누르고 확인 누르면 배송완료로 돌아가고
                //아니오 누르면 교환반품 신청인 상태로 돌아감.
            });
        }
        else {
            if(orderStatus == CodeTable.ORDER_STATUS_RETURN_OK||orderStatus ==CodeTable.ORDER_STATUS_REFUND_OK){
                //교환, 반품신청 완료 되면 모든 버튼들 사라짐
                holder.binding.btnsOnready.setVisibility(View.GONE);
                holder.binding.btnsOndelivery.setVisibility(View.GONE);
                holder.binding.btnsArrived.setVisibility(View.GONE);
                holder.binding.btnsArrivedNOK.setVisibility(View.GONE);
                holder.binding.btnsChangeNRefund.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemOrderProductBinding binding;

        public ViewHolder(@NonNull ItemOrderProductBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}


