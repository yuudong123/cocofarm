package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cocofarm.andapp.databinding.ActivityAmAddressBinding;

public class AmAddressActivity extends AppCompatActivity {

    ActivityAmAddressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtAddress.setOnClickListener(v->{
            int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
            if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                Intent intent = new Intent(AmAddressActivity.this, AddressApiActivity.class);
                overridePendingTransition(0, 0);
            }else {
                Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}