package com.cocofarm.andapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.board.NoticeFragment;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityMainBinding;
import com.cocofarm.andapp.home.HomeFragment;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.mydevice.MyDeviceFragment;
import com.cocofarm.andapp.mypage.MypageFragment;
import com.cocofarm.andapp.mypage.NonMemberFragment;
import com.cocofarm.andapp.product.ProductFragment;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = this;


        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();


        binding.bottomNav.setOnItemSelectedListener(menu->{
            Fragment fragment =  null;
            if(menu.getItemId()==R.id.home){
                fragment = new HomeFragment();

            } else if (menu.getItemId()==R.id.shop) {
                fragment = new ProductFragment();

            } else if(menu.getItemId()==R.id.board){
                fragment = new BoardFragment();

            } else if (menu.getItemId()==R.id.mydevice) {
                fragment = new MyDeviceFragment();

            } else if (menu.getItemId()==R.id.mypage) {
                if(CommonVal.loginMember == null) {
                    fragment = new NonMemberFragment();
                } else {
                    fragment = new MypageFragment();
                }
            }else{
                return false;
            }
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
            return true;

        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}

