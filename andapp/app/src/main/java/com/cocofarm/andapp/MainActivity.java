package com.cocofarm.andapp;

import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.databinding.ActivityMainBinding;
import com.cocofarm.andapp.home.HomeFragment;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.mypage.CsCenterActivity;
import com.cocofarm.andapp.mypage.MypageFragment;
import com.cocofarm.andapp.mypage.NonMemberFragment;
import com.cocofarm.andapp.order.CartActivity;
import com.cocofarm.andapp.product.ProductFragment;
import com.cocofarm.andapp.util.BackPressedHandler;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
    public ActivityMainBinding binding;

    private BackPressedHandler backPressedHandler = new BackPressedHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = this;

        binding.bottomNav.setOnItemSelectedListener(menu -> {
            Fragment fragment;
            if (menu.getItemId() == R.id.home) {
                fragment = new HomeFragment();
            } else if (menu.getItemId() == R.id.shop) {
                fragment = new ProductFragment();
            } else if (menu.getItemId() == R.id.board) {
                fragment = new BoardFragment();
            } else if (menu.getItemId() == R.id.mydevice) {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return false;
            } else if (menu.getItemId() == R.id.mypage) {
                if (loginMember == null) {
                    fragment = new NonMemberFragment();
                } else {
                    fragment = new MypageFragment();
                }
            } else {
                return false;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        });

        binding.navView.setNavigationItemSelectedListener(item -> {
            int i = item.getItemId();
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END);
            }
            if (i == R.id.right_nav_home) {
                binding.bottomNav.setSelectedItemId(R.id.home);
            } else if (i == R.id.right_nav_logout) {
                loginMember = null;
                SharedPreferences.Editor editor = SplashActivity.preferences.edit();
                editor.putString("email", "");
                editor.putString("password", "");
                editor.putBoolean("checked", false);
                editor.apply();
            } else if (i == R.id.right_nav_login) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (i == R.id.right_nav_cocomall) {
                binding.bottomNav.setSelectedItemId(R.id.shop);
            } else if (i == R.id.right_nav_cart) {
                Intent intentCart = new Intent(mContext, CartActivity.class);
                startActivity(intentCart);
            } else if (i == R.id.right_nav_board) {
                binding.bottomNav.setSelectedItemId(R.id.board);
            } else if (i == R.id.right_nav_mypage) {
                binding.bottomNav.setSelectedItemId(R.id.mypage);
            } else if (i == R.id.right_nav_mydevice) {
                binding.bottomNav.setSelectedItemId(R.id.mydevice);
            } else if (i == R.id.right_nav_cs) {
                Intent intentCs = new Intent(mContext, CsCenterActivity.class);
                startActivity(intentCs);
            }
            return true;
        });
        binding.bottomNav.setSelectedItemId(R.id.home);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        binding.btnRightNavOpen.setOnClickListener(v -> {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        binding.navView.getHeaderView(0).findViewById(R.id.btn_right_navi_close).setOnClickListener(v -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (loginMember == null) {
            binding.navView.getMenu().findItem(R.id.right_nav_login).setVisible(true);
            binding.navView.getMenu().findItem(R.id.right_nav_logout).setVisible(false);
        } else {
            binding.navView.getMenu().findItem(R.id.right_nav_login).setVisible(false);
            binding.navView.getMenu().findItem(R.id.right_nav_logout).setVisible(true);
        }

        if (loginMember != null) {
            ((TextView) binding.navView.getHeaderView(0).findViewById(R.id.tv_right_nav_email))
                    .setText(loginMember.getEmail());
            ((TextView) binding.navView.getHeaderView(0).findViewById(R.id.tv_right_nav_nickname))
                    .setText(loginMember.getNickname());
            ((TextView) binding.navView.getHeaderView(0).findViewById(R.id.tv_right_nav_nim))
                    .setText("  님");
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }
        backPressedHandler.onBackPressed();
    }
}
