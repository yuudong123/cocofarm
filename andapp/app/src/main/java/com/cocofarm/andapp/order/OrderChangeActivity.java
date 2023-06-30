package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderChangeBinding;
import com.google.gson.Gson;

public class OrderChangeActivity extends AppCompatActivity {

    ActivityOrderChangeBinding binding;
    int selectNum;
    String DetailReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityOrderChangeBinding.inflate(getLayoutInflater());



        RadioGroup radioGroup = binding.radioOrderChange;

        radioGroup.getCheckedRadioButtonId();


        binding.btnOrderChangeSubmit.setOnClickListener(v->{
            int reason = 0;
            String textReason="";
            if (binding.radioOrderChange.getCheckedRadioButtonId() == R.id.radio_btn_just){
                reason = CodeTable.REASON_CODE_JUST;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_notdelivery) {
                reason = CodeTable.REASON_CODE_NOTYET;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_lackproduct) {
                reason = CodeTable.REASON_CODE_LACK;
            }else if(binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_onother){
                reason = CodeTable.REASON_CODE_DIFFER;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_differ) {
                reason = CodeTable.REASON_CODE_ONOTHER;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_broken) {
                reason = CodeTable.REASON_CODE_BROKEN;
            }else{
                reason = CodeTable.REASON_CODE_DETAIL;
                textReason = binding.etChangeDetailedReason.getText().toString();
                if(textReason.equals("")){
                    Toast.makeText(this, "교환사유를 체크하거나 상세내용을 적어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            textReason = binding.etChangeDetailedReason.getText().toString();
            selectNum = reason;
            DetailReason = textReason;
            changeReason();

            finish();

        });
        binding.btnClose.setOnClickListener(v->{
            finish();
        });

        setContentView(binding.getRoot());
    }
    private void changeReason(){
        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        ChangeAndRefundDTO dto =new ChangeAndRefundDTO();
        dto.setMember_no(orderProductVO.getMember_no());
        dto.setOrderproduct_id(orderProductVO.getOrderproduct_id());
        dto.setOrder_status_cd(CodeTable.ORDER_STATUS_CHANGE_REQ); //교환신청코드
        dto.setReasoncode(selectNum);
        dto.setTextreason(DetailReason);
        CommonConn conn = new CommonConn(this,"changeandrefundinsert.and");
        conn.addParam("dto", new Gson().toJson( dto ));
        conn.onExcute((isResult, data) -> {
            Log.d("환불처리", "onCreate: " + isResult);
        });
    }

}
