package com.cocofarm.andapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.databinding.ActivityMainBinding;
import com.cocofarm.andapp.home.HomeFragment;
import com.cocofarm.andapp.product.ProductFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        binding.bottomNav.setOnItemSelectedListener(menu->{
            Fragment fragment =  null;
            if(menu.getItemId()==R.id.home){
                fragment = new HomeFragment();

            } else if (menu.getItemId()==R.id.shop) {
                fragment = new ProductFragment();

            } else if(menu.getItemId()==R.id.board){
                fragment = new BoardFragment();

            } else if (menu.getItemId()==R.id.mydevice) {
                
            } else if (menu.getItemId()==R.id.mypage) {
                
            }else{
                return false;
            }
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
            return true;

        });
    }
}