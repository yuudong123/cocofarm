package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.board.BoardFragment.cri;
import static com.cocofarm.andapp.board.BoardFragment.pagenum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.board.EventFragment;
import com.cocofarm.andapp.board.NoticeFragment;
import com.cocofarm.andapp.board.QnAFragment;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentProductBinding;
import com.cocofarm.andapp.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    FragmentProductBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProductBinding.inflate(inflater, container, false);

        getChildFragmentManager().beginTransaction().replace(R.id.containerProduct, new ProductPlantFragment()).commit();

        binding.activityPruductPlant.addTab(binding.activityPruductPlant.newTab().setText("식물"));
        binding.activityPruductPlant.addTab(binding.activityPruductPlant.newTab().setText("관리 기기"));
        binding.activityPruductPlant.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                Fragment fragment = null;
                if (i == 0) {
                    fragment = new ProductPlantFragment();
                } else if (i == 1) {
                    fragment = new ProductDeviceFragment();

                }
                getChildFragmentManager().beginTransaction().replace(R.id.containerProduct, fragment).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}