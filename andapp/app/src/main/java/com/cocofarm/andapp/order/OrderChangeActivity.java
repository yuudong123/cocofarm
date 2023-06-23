package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderChangeBinding;

public class OrderChangeActivity extends AppCompatActivity {

    ActivityOrderChangeBinding binding;
    int selectNum;
    String DetailReason;
    boolean radioCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityOrderChangeBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());


        RadioGroup radioGroup = binding.radioOrderChange;

        radioGroup.getCheckedRadioButtonId();


        binding.btnOrderChangeSubmit.setOnClickListener(v->{
            int reason = 0;
            String textReason="";
            if (binding.radioOrderChange.getCheckedRadioButtonId() == R.id.radio_btn_just){
                reason = 1;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_notdelivery) {
                reason = 2;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_lackproduct) {
                reason = 3;
            }else if(binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_onother){
                reason = 4;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_differ) {
                reason = 5;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.radio_btn_broken) {
                reason = 6;
            } else if (binding.radioOrderChange.getCheckedRadioButtonId()==R.id.et_change_detailed_reason) {
                reason = 7;
                textReason = binding.etChangeDetailedReason.getText().toString();
            }else{
                Toast.makeText(this, "교환사유를 체크하거나 상세내용을 적어주세요.", Toast.LENGTH_SHORT).show();
            }
            selectNum = reason;
            DetailReason = textReason;
            changeReason();

        });
    }
    private void changeReason(){
        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        CommonConn conn = new CommonConn(this,".and");
        conn.addParam("orderProductVO", orderProductVO);
        conn.addParam("reasoncode",selectNum+"");
        conn.addParam("textreason", DetailReason);
        conn.onExcute((isResult, data) -> {
        });
    }

}
