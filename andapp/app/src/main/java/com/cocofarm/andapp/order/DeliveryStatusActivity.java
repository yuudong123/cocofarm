package com.cocofarm.andapp.order;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.databinding.ActivityDeliveryStatusBinding;
import com.cocofarm.andapp.image.ImageUtil;

public class DeliveryStatusActivity extends AppCompatActivity {

    ActivityDeliveryStatusBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        ImageUtil.load(binding.ivProductImage, orderProductVO.getFilename());
        binding.tvProductName.setText(orderProductVO.getName());

        binding.tvStatus.setText(orderProductVO.getValue());
        setButtons(orderProductVO.getOrder_status_cd(), binding.tvStatusMent);

        binding.btnConfirm.setOnClickListener(v -> finish());
    }

    private void setButtons(int order_status_cd, TextView ment) {

        if (order_status_cd == CodeTable.ORDER_STATUS_ONREADY) {
            binding.tvDeliveryOnready.setTextColor(Color.parseColor("#FFA500"));
            ment.setText("배송준비중입니다.");
        } else if (order_status_cd == CodeTable.ORDER_STATUS_ONDELIVERY) {
            binding.tvDeliveryOn.setTextColor(Color.parseColor("#FFFF00"));
            ment.setText("배송중입니다.");
        } else if (order_status_cd == CodeTable.ORDER_STATUS_ARRIVED || order_status_cd == CodeTable.ORDER_STATUS_SUCCESS
                || order_status_cd == CodeTable.ORDER_STATUS_CHANGE_REQ || order_status_cd == CodeTable.ORDER_STATUS_CHANGE_REQ) {
            binding.tvDeliveryFinish.setTextColor(Color.parseColor("#00FA9A"));
            ment.setText("배송완료");
        } else {
        }
    }
}