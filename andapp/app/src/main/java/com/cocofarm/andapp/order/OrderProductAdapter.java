package com.cocofarm.andapp.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ItemOrderProductBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductActivity;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    Context context;
    ItemOrderProductBinding binding;
    ArrayList<OrderProductVO>list;


    public OrderProductAdapter(ArrayList<OrderProductVO> list , Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ItemOrderProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
        }


    @SuppressLint("ResourceAsColor")
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
            status("취소", "배송조회", holder);

            holder.binding.btn1.setOnClickListener(v -> {

                //취소 버튼 눌렀을 경우 주문목록에서 삭제가 아니라 업데이트해서 결제 취소 뜨게 하기.
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("").setMessage("취소신청을 원하시면 취소신청 버튼을 눌러주시고(함께 주문하신것들이 취소가 됩니다.) 취소를 원하시지 않으시면 아니오를 눌러주세요.").setCancelable(false)
                        .setPositiveButton("취소신청", (dialogInterface, i1) -> {

                            orderProduct.setOrder_status_cd(CodeTable.ORDER_STATUS_CANCEL);
                            orderProduct.setValue("결제취소"); //코드 테이블 정리되야 함 일단 value는 하드코딩.
                            status(null, null, holder);
                            CommonConn conn = new CommonConn(context, "orderproductupdate.and");
                            conn.addParam("vo", new Gson().toJson(orderProduct));
                            conn.onExcute((isResult, data) -> {
                                if (isResult) {
                                    holder.binding.btn1.setVisibility(View.GONE);
                                    holder.binding.btn2.setVisibility(View.GONE);
                                    status(null, null, holder);
                                    notifyDataSetChanged();

                                } else {
                                    Toast.makeText(context, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }

                            });
                        })
                        .setNegativeButton("아니오", (dialogInterface, i1) -> {

                        }).create().show();

            });
        }else if(orderStatus ==CodeTable.ORDER_STATUS_CANCEL) {
            //버튼 컬러 넣을려고 했음.
            //holder.binding.btn1.setBackgroundColor(R.drawable.color_bottom_nav_icon);
            holder.binding.btn1.setClickable(false);
            status("결제취소 되었습니다.", null, holder);




        }else if(orderStatus ==CodeTable.ORDER_STATUS_ONREADY) {
                holder.binding.btn2.setOnClickListener(v -> {
                    //배송조회 눌렀을 경우 배송조회 페이지로 이동 배송시작 글자만 뜨게.
                });
            }



        else if(orderStatus == CodeTable.ORDER_STATUS_ONDELIVERY) {
            //배송중
            //취소 불가능 배송조회 버튼만 뜸.
            status(null , "배송조회",holder);

            holder.binding.btn2.setOnClickListener(v->{
                //배송조회 눌렀을 경우 배송조회 페이지로 이동
            });

        } else if (orderStatus ==CodeTable.ORDER_STATUS_ARRIVED) {
            //배송완료 시점.
            status("교환·반품신청" , "구매확정",holder);
            holder.binding.btn1.setOnClickListener(v->{
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
                        })
                        .setNeutralButton("아니오", (dialogInterface, i1) -> {
                        }).create().show();
            });
            holder.binding.btn2.setOnClickListener(v->{
                status("리뷰쓰기" , null,holder);
                holder.binding.btn1.setOnClickListener(v1->{
                    //리뷰쓰기 페이지로 이동

                });
                    });
        }

        else if (orderStatus == CodeTable.ORDER_STATUS_RETURN_REQ||orderStatus ==CodeTable.ORDER_STATUS_REFUND_REQ) {
            //교환반품 신청 하고 난후에 304.305상태면 교환반품 취소할수있음.
            status("취소" , null,holder);
            //취소 누르고 나서는 다시 교환반품 신청, 배송조회 버튼이 떠아함
            //뜨고나서 처리 생각

            holder.binding.btn1.setOnClickListener(v->{
                //교환반품 신청 취소 후 다시 신청 할수 있는지는..?
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("").setMessage("교환반품을 취소하시겠습니까? 취소를 원하시면 취소를 눌러주시고 원래 화면으로 돌아가시려면 아니오를 눌러주세요.").setCancelable(false)
                        .setPositiveButton("취소", ((dialogInterface, i1) -> {
                            status("교환, 반품 신청" , "배송조회",holder);
                            holder.binding.btn2.setVisibility(View.VISIBLE);
                        }))
                        .setNegativeButton("아니오", (dialogInterface, i1) -> {
                            status("취소" , null,holder);
                        }).create().show();
                status("교환, 반품 신청" , "배송조회",holder);

            });

        }
        else {
            if(orderStatus == CodeTable.ORDER_STATUS_RETURN_OK||orderStatus ==CodeTable.ORDER_STATUS_REFUND_OK){
                status(null , null,holder);
            }
        }

    }

    private void status(String buttonText1, String buttonText2 ,ViewHolder holder){
        if(buttonText1==null){
            holder.binding.btn1.setVisibility(View.GONE);
        }else{
            holder.binding.btn1.setText(buttonText1);
        }
        if(buttonText2==null){
            holder.binding.btn2.setVisibility(View.GONE);
        }else{
            holder.binding.btn2.setText(buttonText2);
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


