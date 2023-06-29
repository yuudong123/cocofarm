package com.cocofarm.andapp.order;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ItemOrderProductBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ReviewWriteActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    Context context;
    ItemOrderProductBinding binding;
    ArrayList<OrderProductVO> list;

    CommonConn conn;


    public OrderProductAdapter(ArrayList<OrderProductVO> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderProductVO orderProduct = list.get(position);
        int orderStatus = orderProduct.getOrder_status_cd();

       // String orderDate = CommonVal.Md.format(list.get(position).getOrderdate());
       // holder.binding.tvOrderDay.setText(orderDate + " 결제");
        holder.binding.tvOrderReady.setText(list.get(position).getValue());
        ImageUtil.load(holder.binding.ivCartOrder1, list.get(position).getFilename());
        holder.binding.tvOrderproductName.setText(list.get(position).getName());
        holder.binding.tvOrderproductPrice.setText("각 " + CommonVal.comma(list.get(position).getPrice()) + " / ");
        holder.binding.tvOrderproductAmount.setText(list.get(position).getAmount() + "개");


        switch (orderStatus) {
            case CodeTable.ORDER_STATUS_ONREADY:
                //주문하고 바로 배송 시작
                status("취소", "배송조회", holder);
                //보여줄 버튼은 두개 취소, 배송조회

                holder.binding.btn2.setOnClickListener(v -> {
                    deliveryActivity(orderProduct);
                });
                holder.binding.btn1.setOnClickListener(v -> {
                    //취소 버튼 눌렀을 경우 주문목록에서 삭제가 아니라 업데이트해서 결제 취소 뜨게 하기.
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("").setMessage("취소신청을 원하시면 취소신청 버튼을 눌러주시고(함께 주문하신것들이 취소가 됩니다.) 취소를 원하시지 않으시면 아니오를 눌러주세요.").setCancelable(false)
                            .setPositiveButton("취소신청", (dialogInterface, i1) -> {
                                orderProduct.setOrder_status_cd(CodeTable.ORDER_STATUS_CANCEL);
                                orderProduct.setValue("결제취소"); //코드 테이블 정리되야 함 일단 value는 직접
                                CommonConn conn = new CommonConn(context, "orderproductupdate.and");
                                conn.addParam("vo", new Gson().toJson(orderProduct));
                                conn.onExcute((isResult, data) -> {
                                    if (isResult) {
                                        //결제취소신청이 완료되면
                                        holder.binding.btn1.setClickable(false);
                                        status("결제취소 되었습니다.", "", holder);
                                        holder.binding.btn2.setVisibility(View.GONE);

                                    } else {
                                        Toast.makeText(context, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            })
                            .setNegativeButton("아니오", (dialogInterface, i1) -> {

                            }).create().show();
                });
                break;

            case CodeTable.ORDER_STATUS_CANCEL:
                //결제취소 시
                //버튼 눌리지 않고 버튼에 컬러를 넣을려고함(컬러 아직 ㄴㄴ)
                holder.binding.btn1.setClickable(false);
                status("결제취소 되었습니다.", "", holder);
                holder.binding.btn2.setVisibility(View.GONE);
                break;

            case CodeTable.ORDER_STATUS_ONDELIVERY:
                status("", "배송조회", holder);
                holder.binding.btn2.setOnClickListener(v -> {
                    deliveryActivity(orderProduct);

                });
            case CodeTable.ORDER_STATUS_ARRIVED:
                status("교환·반품신청", "구매확정", holder);
                holder.binding.btn1.setOnClickListener(v1 -> {
                    //교환반품 신청 버튼 눌렀을때
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("").setMessage("교환신청을 원하시면 교환, 반품신청을 원하시면 반품을 눌러주세요.").setCancelable(false)
                            .setPositiveButton("교환", (dialogInterface, i1) -> {
                                Intent intent = new Intent(context, OrderChangeActivity.class);
                                intent.putExtra("orderProductVO", list.get(position));
                                context.startActivity(intent);
                            })
                            .setNegativeButton("반품", (dialogInterface, i1) -> {
                                Intent intent = new Intent(context, OrderRefundActivity.class);
                                intent.putExtra("orderProductVO", list.get(position));
                                context.startActivity(intent);
                            })
                            .setNeutralButton("아니오", (dialogInterface, i1) -> {
                                status("교환·반품신청", "구매확정", holder);
                            }).create().show();
                });
                holder.binding.btn2.setOnClickListener(v -> {
                    orderProduct.setOrder_status_cd(CodeTable.ORDER_STATUS_SUCCESS);
                    //상태코드 310으로 세팅
                    orderProduct.setValue("구매확정");
                    conn = new CommonConn(context, "orderproductupdate.and");
                    conn.addParam("vo", new Gson().toJson(orderProduct));
                    conn.onExcute((isResult, data) -> {
                        if (isResult) {
                            //구매확정이 완료되면
                            status("리뷰쓰기", "배송조회", holder);
                        }
                    });
                });
                break;
            case CodeTable.ORDER_STATUS_SUCCESS:
                status("리뷰쓰기", "배송조회", holder);
                int reviewTo = list.get(position).getReview_board_no();
                if (reviewTo > 0) {
                    holder.binding.btn1.setText("리뷰읽기");
                    //리뷰페이지로 이동.
                    holder.binding.btn1.setOnClickListener(v1 -> {
                        Intent intent = new Intent(context, ReviewViewActivity.class);
                        intent.putExtra("orderProductVO", list.get(position));
                        context.startActivity(intent);
                    });
                } else {
                    holder.binding.btn1.setOnClickListener(v1 -> {
                        //리뷰쓰기 페이지로 이동 -> 리뷰쓰거나 시간 지나면 리뷰쓰기완료로 만들거나..흠..
                        Intent intent = new Intent(context, ReviewWriteActivity.class);
                        intent.putExtra("orderProductVO", list.get(position));
                        context.startActivity(intent);
                    });
                }


                holder.binding.btn2.setOnClickListener(v2 -> {
                    deliveryActivity(orderProduct);
                });
                break;

            //교환반품 취소시를 어떻게 할수있게 할지.. 고민.
            case CodeTable.ORDER_STATUS_RETURN_REQ:
            case CodeTable.ORDER_STATUS_REFUND_REQ:
                //교환반품 신청 하고 난후에 304.305상태면 교환반품 취소할수있음.
                status("교환,반품신청중", "배송조회", holder);
                //취소 누르고 나서는 다시 교환반품 신청, 배송조회 버튼 등장
//                holder.binding.btn1.setOnClickListener(v -> {
//                    //교환반품 신청 취소 후 다시 신청 할수 있는지는 생각중.
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("").setMessage("교환반품을 취소하시겠습니까? 취소를 원하시면 취소를 눌러주시고 원래 화면으로 돌아가시려면 아니오를 눌러주세요.").setCancelable(false)
//                            .setPositiveButton("취소", ((dialogInterface, i1) -> {
//                                status("교환, 반품 신청", "배송조회", holder);
//                            }))
//                            .setNegativeButton("아니오", (dialogInterface, i1) -> {
//                                status("취소", "배송조회", holder);
//                            }).create().show();
//                    status("교환, 반품 신청", "배송조회", holder);
//                });
                holder.binding.btn2.setOnClickListener(v -> {
                    deliveryActivity(orderProduct);
                });
                break;
            case CodeTable.ORDER_STATUS_RETURN_OK:
            case CodeTable.ORDER_STATUS_REFUND_OK:
                orderProduct.setOrder_status_cd(CodeTable.ORDER_STATUS_SUCCESS);
                //상태코드 310으로 세팅
                orderProduct.setValue("교환.반품 완료");
                CommonConn conn = new CommonConn(context, "orderproductupdate.and");
                conn.addParam("vo", new Gson().toJson(orderProduct));
                conn.onExcute((isResult, data) -> {
                    Log.d("교환, 반품완료", "onBindViewHolder: " + isResult);
                    status("", "", holder);
                });
                holder.binding.btn1.setVisibility(View.GONE);
                holder.binding.btn2.setVisibility(View.GONE);
                break;
        }
//        conn = new CommonConn(context, "orderproductlowerlist.and");
//        conn.addParam("order_id", value);
//        conn.onExcute((isResult, data) -> {
//            ArrayList<OrderProductVO> list = new Gson().fromJson(data,
//                    new TypeToken<ArrayList<OrderProductVO>>() {
//                    }.getType());
//            if (isResult){
//                Log.d("order_id 데이터", "onBindViewHolder: " + orderProduct.getOrder_id());
//        OrderProductAddAdapter childAdapter = new OrderProductAddAdapter(list, value);
//        LinearLayoutManager childManager = new LinearLayoutManager(context);
//        binding.recvOrderAddlist.setAdapter(childAdapter);
//        binding.recvOrderAddlist.setLayoutManager(childManager);
//
//        binding.getRoot();
//            }
//        });
    }


    private void status(String buttonText1, String buttonText2, ViewHolder holder) {
        if (buttonText1 == null) {
            holder.binding.btn1.setVisibility(View.GONE);
        } else {
            holder.binding.btn1.setText(buttonText1);
        }
        if (buttonText2 == null) {
            holder.binding.btn2.setVisibility(View.GONE);
        } else {
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
            this.binding = binding;
        }
    }

    private void deliveryActivity(OrderProductVO orderProductVO) {
        Intent intent = new Intent(context, DeliveryStatusActivity.class);
        intent.putExtra("orderProductVO", orderProductVO);
        context.startActivity(intent);
    }
}


