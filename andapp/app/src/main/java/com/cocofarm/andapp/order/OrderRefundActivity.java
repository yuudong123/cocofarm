package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CodeTable.ORDER_STATUS_REFUND_REQ;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_BROKEN;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_DETAIL;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_DIFFER;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_JUST;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_LACK;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_NOTYET;
import static com.cocofarm.andapp.common.CodeTable.REASON_CODE_ONOTHER;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderRefundBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.google.gson.Gson;

public class OrderRefundActivity extends AppCompatActivity {

    ActivityOrderRefundBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderRefundBinding.inflate(getLayoutInflater());

        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");

        ImageUtil.load(binding.imgRefundapply,orderProductVO.getFilename());
        binding.tvRefundapplyName.setText(orderProductVO.getName());
        binding.tvRefundapplyamount.setText(orderProductVO.getAmount()+"개");
        
        binding.btnOrderRefundSubmit.setOnClickListener(v -> {
            int reason;
            String textReason;
            if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_just) {
                reason = REASON_CODE_JUST;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_notdelivery) {
                reason = REASON_CODE_NOTYET;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_lackproduct) {
                reason = REASON_CODE_LACK;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_onother) {
                reason = REASON_CODE_DIFFER;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_differ) {
                reason = REASON_CODE_ONOTHER;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_broken) {
                reason = REASON_CODE_BROKEN;
            } else {
                reason = REASON_CODE_DETAIL;
                textReason = binding.etRefundDetailedReason.getText().toString();
                if (textReason.equals("")) {
                    Toast.makeText(this, "환불사유를 체크하거나 상세내용을 적어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            textReason = binding.etRefundDetailedReason.getText().toString();
            changeReason(reason, textReason);
            finish();
        });
        binding.btnClose.setOnClickListener(v -> finish());
        setContentView(binding.getRoot());
    }

    private void changeReason(int selectNum, String DetailReason) {
        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        ChangeAndRefundDTO dto = new ChangeAndRefundDTO();
        dto.setMember_no(orderProductVO.getMember_no());
        dto.setOrderproduct_id(orderProductVO.getOrderproduct_id());
        dto.setOrder_status_cd(ORDER_STATUS_REFUND_REQ); //환불신청코드
        dto.setReasoncode(selectNum);
        dto.setTextreason(DetailReason);
        CommonConn conn = new CommonConn(this, "changeandrefundinsert.and");
        conn.addParam("dto", new Gson().toJson(dto));
        conn.onExcute((isResult, data) -> Log.d("환불처리", "onCreate: " + isResult));
    }
}