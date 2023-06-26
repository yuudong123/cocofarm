package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderRefundBinding;
import com.google.gson.Gson;

public class OrderRefundActivity extends AppCompatActivity {

    ActivityOrderRefundBinding binding;
    int selectNum;
    String DetailReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderRefundBinding.inflate(getLayoutInflater());

        RadioGroup radioGroup = binding.radioOrderRefund;

        radioGroup.getCheckedRadioButtonId();

        binding.btnOrderRefundSubmit.setOnClickListener(v->{
            int reason = 0;
            String textReason="";
            if (binding.radioOrderRefund.getCheckedRadioButtonId() == R.id.radio_btn_just){
                reason = 1;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId()==R.id.radio_btn_notdelivery) {
                reason = 2;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId()==R.id.radio_btn_lackproduct) {
                reason = 3;
            }else if(binding.radioOrderRefund.getCheckedRadioButtonId()==R.id.radio_btn_onother){
                reason = 4;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId()==R.id.radio_btn_differ) {
                reason = 5;
            } else if (binding.radioOrderRefund.getCheckedRadioButtonId()==R.id.radio_btn_broken) {
                reason = 6;
            }else{
                reason = 7;
                textReason = binding.etRefundDetailedReason.getText().toString();
                if(textReason.equals("")){
                    Toast.makeText(this, "반품사유를 체크하거나 상세내용을 적어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            textReason = binding.etRefundDetailedReason.getText().toString();
            selectNum = reason;
            DetailReason = textReason;
            changeReason();

            finish();
        });

        setContentView(binding.getRoot());

    }
    private void changeReason(){
        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        ChangeAndRefundDTO dto =new ChangeAndRefundDTO();
        dto.setMember_no(orderProductVO.getMember_no());
        dto.setOrderproduct_id(orderProductVO.getOrderproduct_id());
        dto.setOrder_status_cd(305); //반품신청코드
        dto.setReasoncode(selectNum);
        dto.setTextreason(DetailReason);
        CommonConn conn = new CommonConn(this,"changeandrefundinsert.and");
        conn.addParam("dto", new Gson().toJson( dto ));
        conn.onExcute((isResult, data) -> {
            Log.d("반품처리", "onCreate: " + isResult);
        });
    }

}