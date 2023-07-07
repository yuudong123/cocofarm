package com.cocofarm.andapp.report;

import static android.view.View.VISIBLE;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReportBinding;

public class ReportActivity extends AppCompatActivity {

    ActivityReportBinding binding;
    int reported_board;
    int reported_reply;
    int reported_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reported_board = getIntent().getIntExtra("reported_board", 0);
        reported_reply = getIntent().getIntExtra("reported_reply", 0);
        reported_member = getIntent().getIntExtra("reported_member", 0);

        if (reported_board != 0) {
            binding.reportBoardTitle.setText("제목 : " + getIntent().getStringExtra("title"));
            binding.reportBoardContent.setText("내용 : " + getIntent().getStringExtra("content"));
            binding.reportBoardInfo.setVisibility(VISIBLE);
        } else {
            binding.reportReplyContent.setText("댓글 내용 : " + getIntent().getStringExtra("content"));
            binding.reportReplyContent.setVisibility(VISIBLE);
        }
        binding.reportedNickname.setText("작성자 : "+getIntent().getStringExtra("reported_nickname"));

        binding.btnCancel.setOnClickListener(v -> finish());
        binding.btnConfirm.setOnClickListener(v -> sendReport());
    }

    private void sendReport() {

        int reason_cd = 0;
        int checked = binding.radioGroupReport.getCheckedRadioButtonId();
        if (checked == R.id.radioBtnReport511) reason_cd = 511;
        else if (checked == R.id.radioBtnReport512) reason_cd = 512;
        else if (checked == R.id.radioBtnReport513) reason_cd = 513;
        else if (checked == R.id.radioBtnReport514) reason_cd = 514;
        else if (checked == R.id.radioBtnReport515) reason_cd = 515;
        else if (checked == R.id.radioBtnReport516) reason_cd = 516;
        else if (checked == R.id.radioBtnReport517) reason_cd = 517;
        else if (checked == R.id.radioBtnReport518) reason_cd = 518;

        if ((reported_board == 0 && reported_reply == 0) || reported_member == 0) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (reason_cd == 0) {
            Toast.makeText(this, "신고 사유를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        CommonConn conn = new CommonConn(this, "report.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        if (reported_board != 0) {
            conn.addParam("reported_board", reported_board);
        } else {
            conn.addParam("reported_reply", reported_reply);
        }
        conn.addParam("reported_member", reported_member);
        conn.addParam("reason_cd", reason_cd);
        conn.addParam("content", binding.edtContent.getText().toString());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                Toast.makeText(this, "신고되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}

